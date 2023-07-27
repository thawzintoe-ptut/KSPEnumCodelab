plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kspAndroid)
}

// TODO: step 7 change JavaVersion to JavaVersion.VERSION_17
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

dependencies{
    // TODO: step 5 implement project module -> annotation
    // TODO: step 6 add KSP api dependency and Kotlin poet library
    implementation(project(":enumksp:annotation"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.21-1.0.11")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    implementation(libs.squareup.ksp)
    implementation(libs.kotlinpoet)
}