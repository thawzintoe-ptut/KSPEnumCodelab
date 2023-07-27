package com.ptut.kspenumcodelab.model

import com.ptut.annotation.Enum

// TODO step 29: create new Model (User) and declare annotation GenerateEnum class

data class User(
    // TODO step 30: declare property field with annotation that you want to crate enum class
    @Enum(enumConstants = ["Male", "Female", "Other"])
    val genderType: Int = 1
)
