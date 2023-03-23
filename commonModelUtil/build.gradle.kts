plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.commonModelUtil"
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.androidx)
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.bundles.junit)
    implementation(libs.androidx.security.crypto)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}