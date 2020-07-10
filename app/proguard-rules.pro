-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keep public class * extends java.lang.Exception

# For enumeration classes
-keepclassmembers enum * {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}


# For kotlin-reflect
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

# AndroidPdfViewer
-keep class com.shockwave.**

