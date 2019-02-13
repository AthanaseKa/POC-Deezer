package fr.athanase.deezer.dao

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmQuery
import rx.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun RealmConfiguration.flushDatabase(): Observable<Boolean> {
    return startTransaction {
        it.deleteAll()
        Timber.d("Database has been flushed")
        return@startTransaction true
    }
}

inline fun <reified Class : RealmObject> RealmConfiguration.flushEntityFromDatabase(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<Boolean> {
    return startTransaction {
        it.query().findAll()?.deleteAllFromRealm()
        return@startTransaction true
    }
}

inline fun <reified Class : RealmObject> RealmConfiguration.fetchSingleItem(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<Class?> {
    return fetchItems(query)
            .map {
                return@map if (it.isEmpty()) {
                    null
                } else {
                    it[0]
                }
            }
}

inline fun <reified Class : RealmObject> RealmConfiguration.saveSingleItemTransaction(data: Class): Observable<Class?> {
    return saveMultipleItemTransaction(listOf(data))
            .map {
                return@map if (it.isEmpty()) {
                    null
                } else {
                    it[0]
                }
            }
}
inline fun <reified Class : RealmObject> RealmConfiguration.fetchItems(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<List<Class>> {
    return openInstanceForFetch() {
        val data: List<Class> = it.query().findAll()

        return@openInstanceForFetch it.copyFromRealm(data)
    }
}

inline fun <reified Class : RealmObject> RealmConfiguration.updateItemTransaction(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }, crossinline update: (data: Class?) -> Class?): Observable<Class?> {
    return startTransaction { realm ->
        val data: Class? = realm.query().findFirst()

        return@startTransaction data?.let {
            val result = update(it)
            return@let realm.copyFromRealm(result ?: throw IllegalArgumentException())
        }
    }
}

inline fun <reified Class : List<RealmObject>> RealmConfiguration.saveMultipleItemTransaction(data: Class): Observable<Class> {
    return startTransaction() {
        it.copyToRealmOrUpdate(data)
        return@startTransaction data
    }
}

inline fun <ReturnType> RealmConfiguration.startTransaction(crossinline func: (realm: Realm) -> ReturnType): Observable<ReturnType> {
    return openInstanceForTransaction() { it ->
        var result: ReturnType? = null
        it.executeTransaction {
            result = func(it)
        }

        return@openInstanceForTransaction result ?: throw NullPointerException()
    }
}

inline fun <reified ReturnType> RealmConfiguration.openInstanceForFetch(crossinline func: (realm: Realm) -> ReturnType?): Observable<ReturnType> {
    return waitForInstance().flatMap {
        val results = func(it)
        it.close()

        return@flatMap results?.let { result ->
            Observable.just(result)
        } ?: Observable.error(Throwable(ReturnType::class.toString()))
    }
}

inline fun <ReturnType> RealmConfiguration.openInstanceForTransaction(crossinline func: (realm: Realm) -> ReturnType): Observable<ReturnType> {
    return waitForInstance().flatMap {
        val results = func(it)
        it.close()
        return@flatMap Observable.just(results)
    }
}

fun RealmConfiguration.waitForInstance(retries: Int = 0): Observable<Realm> {
    val observable = createObservable {
        return@createObservable Realm.getInstance(this)
    }
    return observable.flatMap {
        if (it.isInTransaction
                && retries < 3) {
            return@flatMap Observable.timer(300, TimeUnit.MILLISECONDS)
                    .flatMap { waitForInstance(retries + 1) }
        } else if (it.isClosed) {
            return@flatMap Observable.just(Realm.getInstance(this))
        }
        return@flatMap Observable.just(it)
    }
}

inline fun <reified Class> createObservable(crossinline transactionResult: () -> Class?): Observable<Class> {
    return Observable.create { subscriber ->
        subscriber.onNext(transactionResult())
        subscriber.onCompleted()
    }
}