package com.ptut.annotation
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Enum(val enumConstants: Array<String>)