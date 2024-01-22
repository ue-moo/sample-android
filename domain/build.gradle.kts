plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.coroutinesCore)
    implementation(libs.hiltCore)
    ksp(libs.hiltCompiler)
}
