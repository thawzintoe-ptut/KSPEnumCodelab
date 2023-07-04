package com.ptut.kspenumcodelab.model

import com.ptut.annotation.Enum
import com.ptut.annotation.GenerateEnum

// TODO step 29: create new Model (User) and declare annotation GenerateEnum class
@GenerateEnum
data class User(
    // TODO step 30: declare property field with annotation that you want to crate enum class
    @Enum(enumConstants = ["Male", "Female", "Other"])
    val genderType: Int = 1
)