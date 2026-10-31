-keepattributes Signature,InnerClasses,EnclosingMethod
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,AnnotationDefault

-keep,allowobfuscation class com.google.gson.reflect.TypeToken
-keep,allowobfuscation class * extends com.google.gson.reflect.TypeToken

-keep,allowobfuscation class com.devpaul.**.data.datasource.dto.**
-keepclassmembers class com.devpaul.**.data.datasource.dto.** {
    <fields>;
    <init>(...);
}

-keep,allowobfuscation class com.devpaul.core_domain.entity.error.ApiErrorResponse
-keepclassmembers class com.devpaul.core_domain.entity.error.ApiErrorResponse {
    <fields>;
    <init>(...);
}

-keep,allowobfuscation class com.devpaul.core_data.serialization.Wrapper
-keepclassmembers class com.devpaul.core_data.serialization.Wrapper {
    <fields>;
    <init>(...);
}

-keep,allowobfuscation class com.devpaul.profile.domain.entity.ProfileUserEntity
-keepclassmembers class com.devpaul.profile.domain.entity.ProfileUserEntity {
    <fields>;
    <init>(...);
}

-keep,allowobfuscation class com.devpaul.news.domain.entity.CountryItemEntity
-keepclassmembers class com.devpaul.news.domain.entity.CountryItemEntity {
    <fields>;
    <init>(...);
}

-keep,allowobfuscation interface com.devpaul.**.data.datasource.remote.** {
    @retrofit2.http.* <methods>;
}
-keep,allowshrinking,allowobfuscation class kotlin.coroutines.Continuation
-keep,allowshrinking,allowobfuscation class retrofit2.Response

-keep @interface org.simpleframework.xml.** {
    *;
}
-keep,allowobfuscation class org.simpleframework.xml.core.** implements org.simpleframework.xml.core.Label {
    public <init>(...);
}
-keep,allowobfuscation class org.simpleframework.xml.core.** implements org.simpleframework.xml.core.Extractor {
    public <init>(...);
}
-keep,allowobfuscation class org.simpleframework.xml.core.** implements org.simpleframework.xml.core.Parameter {
    public <init>(...);
}

-dontwarn javax.xml.stream.Location
-dontwarn javax.xml.stream.XMLEventReader
-dontwarn javax.xml.stream.XMLInputFactory
-dontwarn javax.xml.stream.events.Attribute
-dontwarn javax.xml.stream.events.Characters
-dontwarn javax.xml.stream.events.StartElement
-dontwarn javax.xml.stream.events.XMLEvent
