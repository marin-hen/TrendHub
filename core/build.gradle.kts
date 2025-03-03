plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization)
}

apply(from = "../config/gradle/build-scripts/android.gradle")
apply(from = "../config/gradle/build-scripts/compose.gradle")

android {
    namespace = "com.example.core"

    buildTypes {
        all {
            buildConfigField(
                name = "BASE_URL",
                type = "String",
                value = "\"https://api.github.com\""
            )
        }
    }
}

dependencies {
    implementation(libs.serialization)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    debugImplementation(libs.androidx.ui.tooling)
    api(libs.compose.stable.collections)
    
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    // Retrofit
    api(libs.retrofit.core)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.convertor)
    implementation(libs.serialization)
    implementation(libs.okhttp)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}