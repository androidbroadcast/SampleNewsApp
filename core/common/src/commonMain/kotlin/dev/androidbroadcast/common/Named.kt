package dev.androidbroadcast.common

import me.tatarka.inject.annotations.Qualifier

@Qualifier
@Target(
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.TYPE
)
public annotation class Named(val value: String)
