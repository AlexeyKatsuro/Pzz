package com.alexeykatsuro.pzz.data

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class ProcessLifetime

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class AppInstanceId

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class AppLang

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class AppOs

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class UiExecutor

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class HasBiometrics

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class CacheDir

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class AsanImza