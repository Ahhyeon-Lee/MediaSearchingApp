plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.coreNetwork"
}

dependencies {

    implementation(libs.bundles.network)
    implementation(libs.bundles.junit)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":commonModelUtil"))
}