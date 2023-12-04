plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.corePreference"
}

dependencies {

    implementation(libs.bundles.network)
    implementation(libs.bundles.junit)
    implementation(libs.androidx.security.crypto)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":coreDomain"))
}