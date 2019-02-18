#Retrofit rules
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepattributes Signature
-keepattributes Exceptions

#Facebook Rules
-keep class com.facebook.** { *; }

#Glide with OKHttp
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule

#OKHTTP
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.**
-dontwarn okio.**

#Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

#Fabric
##Crashlytics
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep public class * extends java.lang.Exception

#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Moshi
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**
-keepclassmembers class ** {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
}

#Retrolambda
-dontwarn java.lang.invoke.*

#RX Android
-dontwarn rx.internal.util.unsafe.**
-keepclassmembers class rx.internal.util.unsafe.** {
    long producerIndex;
    long consumerIndex;
}

#Google Services
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

#Entities
-keep class fr.snapp.igraal.entity.** { *; }
-keep class fr.snapp.igraal.network.request.** { *; }

-keep class com.batch.android.** { *; }
-dontwarn com.batch.android.**

-keep class com.appsflyer.** { *; }
-dontwarn com.appsflyer.**
