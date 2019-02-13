package fr.athanase.deezer.dao

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.RealmResults
import rx.Observable
import java.util.concurrent.TimeUnit

class DeezerDao {
    companion object {
        inline fun <reified Class: RealmObject> updateSingleItem(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }, crossinline update: (data: Class) -> Class): Observable<Class> {
            return createObservable {
                updateItemTransaction(query, update)
            }
        }

        inline fun <reified Class: List<RealmObject>> saveMultipleItems(data: Class): Observable<Class> {
            return createObservable {
                saveMultipleItemsTransaction(data)
            }
        }

        inline fun <reified Class: RealmObject> saveSingleItem(data: Class): Observable<Class> {
            return createObservable {
                saveSingleItemTransaction(data)
            }
        }

        inline fun <reified Class: RealmObject> getMultipleItems(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<List<Class>> {
            return createObservable {
                getMultipleItemsTransaction(query)
            }
        }

        inline fun <reified Class: RealmObject> getSingleItem(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<Class> {
            return createObservable {
                getSingleItemTransaction(query)
            }
        }

        // TODO check with RxJava2 if refacto is needed
        inline fun <reified Class> createObservable(crossinline transactionResult: () -> Class?): Observable<Class> {
            val observable : Observable<Class> = Observable.create { subscriber ->
                subscriber.onNext(transactionResult())
                subscriber.onCompleted()
            }
            return observable//.retryWhen(RetryWithDelay(3, 500))
        }



        inline fun <reified Class: RealmObject> updateItemTransaction(query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }, update: (data: Class) -> Class): Class? {
            return startTransaction {
                var data: Class? = it.query().findFirst()
                if (data != null) {
                    data = it.copyFromRealm(update(data))
                }
                return data
            }
        }

        inline fun <reified Class: List<RealmObject>> saveMultipleItemsTransaction(data: Class): Class {
            return startTransaction {
                it.copyToRealmOrUpdate(data)
                return data
            }
        }

        inline fun <reified Class: RealmObject> saveSingleItemTransaction(data: Class): Class? {
            return saveMultipleItemsTransaction(listOf(data))
                    .flatMap {
                        it.
                    }
        }

        inline fun <reified Class: RealmObject> getMultipleItemsTransaction(query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): List<Class>? {
            return openInstance<List<Class>> {
                val data: RealmResults<Class>? = it.query().findAll()

                if (data != null) {
                    return it.copyFromRealm(data)
                }
                return null
            }
        }

        inline fun <reified Class: RealmObject> getSingleItemTransaction(query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Class? {
            return openInstance<Class> {
                val data: Class? = it.query().findFirst()

                if (data != null) {
                    return it.copyFromRealm(data)
                }
                return null
            }
        }

        inline fun <ReturnType: RealmObject> startTransaction(func: (realm: Realm) -> ReturnType?): ReturnType? {
            return openInstance {
                //val result: ReturnType?
                it.beginTransaction()
                val result = func(it)
                it.commitTransaction()
                return@openInstance result
            }
        }

        /**
         * Default openInstance
         */
        inline fun <ReturnType> openInstance(func: (realm: Realm) -> ReturnType?): ReturnType? {
            val realmInstance = waitForInstance()
            val result = func(realmInstance)
            realmInstance.close()
            return result
            /*var realmInstance = waitForInstance()
            var result: ReturnType? = null
            try {
                //realmInstance = waitForInstance()
                result = func(realmInstance)
            }
            catch (e: Throwable) {
                Log.e("ERRRRRRRRRRROR", e.message)
            }
            finally {
                realmInstance.close()
            }

            return result*/
        }

        // TODO translate sleep to Rx
        fun waitForInstance(): Realm {
            var instance = Realm.getDefaultInstance()
            /*if (instance.isInTransaction) {
                instance.refresh()
            }*/
            while (instance.isInTransaction) {
                try {
                    //delay(300)
                    Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            if (instance.isClosed) {
                instance = Realm.getDefaultInstance()
            }

            return instance
        }

        inline fun <reified Class: RealmObject> getSingleItemTransaction2(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }): Observable<Class> {
            return openInstance2 {
                val data: Class? = it.query().findFirst()

                if (data != null) {
                    return@openInstance2 it.copyFromRealm(data)
                }
                return@openInstance2 null
            }
        }

        inline fun <reified Class: RealmObject> updateItemTransaction2(crossinline query: Realm.() -> RealmQuery<Class> = { where(Class::class.java) }, crossinline update: (data: Class?) -> Class?): Observable<Class?> {
            return startTransaction2 {
                var data: Class? = it.query().findFirst()
                if (data != null) {
                    data = update(data)
                    data = it.copyFromRealm(data ?: throw IllegalArgumentException())
                }
                return@startTransaction2 data
            }
        }

        inline fun <reified Class: RealmObject> saveSingleItemTransaction2(data: Class): Observable<Class?> {
            return startTransaction2 {
                it.copyToRealmOrUpdate(data)
                return@startTransaction2 data
            }
        }

        inline fun <ReturnType: RealmObject> startTransaction2(crossinline func: (realm: Realm) -> ReturnType?): Observable<ReturnType?> {
            return openInstance2 {
                var result : ReturnType? = null
                it.executeTransaction {
                    result = func(it)
                }
                return@openInstance2 result
            }
        }

        inline fun <ReturnType> openInstance2(crossinline func: (realm: Realm) -> ReturnType?): Observable<ReturnType> {
            return waitForInstance2().flatMap {
                val results = func(it)
                it.close()
                return@flatMap Observable.just(results)
            }
        }

        fun waitForInstance2(): Observable<Realm> {
            var retries = 0
            val observable = createObservable {
                return@createObservable Realm.getDefaultInstance()
            }
            return observable.flatMap {
                if (it.isInTransaction
                    && retries < 3) {
                    return@flatMap Observable.timer(300, TimeUnit.MILLISECONDS)
                            .flatMap {
                                retries += 1
                                return@flatMap waitForInstance2()
                            }
                }
                else if (it.isClosed) {
                    return@flatMap Observable.just(Realm.getDefaultInstance())
                }
                return@flatMap Observable.just(it)
            }
        }
    }
}