plugins {
    id("mediaSearch.android.library")
    id("mediaSearch.android.hilt")
}

android {
    namespace = "com.example.coreDomain"
}

dependencies {

    implementation(libs.bundles.junit)
}
