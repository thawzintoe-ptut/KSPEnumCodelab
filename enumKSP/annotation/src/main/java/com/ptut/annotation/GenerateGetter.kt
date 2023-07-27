package com.ptut.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class GenerateGetter(val getterName: String)
