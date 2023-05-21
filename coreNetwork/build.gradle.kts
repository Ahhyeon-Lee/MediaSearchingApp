plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.coreNetwork"
}

dependencies {

    implementation(libs.bundles.junit)
    implementation(libs.bundles.network)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":coreDomain"))
}