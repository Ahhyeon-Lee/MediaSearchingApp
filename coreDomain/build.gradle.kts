plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.coreDomain"
}

dependencies {

    implementation(libs.bundles.android)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.junit)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":coreNetwork"))
    implementation(project(":commonModelUtil"))
}
