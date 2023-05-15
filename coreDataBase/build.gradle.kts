plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.coredatabase"
}

dependencies {

    implementation(libs.bundles.junit)

    implementation(libs.bundles.room)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)

    implementation(project(":commonModelUtil"))
}