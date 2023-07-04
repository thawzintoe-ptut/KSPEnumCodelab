package com.ptut.annotation

// TODO : step 1 add GenerateEnum
// TODO : step 2 add Enum annotation
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class GenerateEnum

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Enum(val enumConstants: Array<String>)