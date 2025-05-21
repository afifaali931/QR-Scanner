plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"

    id("androidx.navigation.safeargs.kotlin")

}
android {
    namespace = "com.example.qrcodescanner"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.qrcodescanner"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    viewBinding {
        enable =  true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


// QR Code

        implementation ("com.github.yuriy-budiyev:code-scanner:2.3.0")

    // SplashScreen API
    implementation("androidx.core:core-splashscreen:1.0.1")


// Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")


    // ZXing QR Code library
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.5.2")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    //load an image by using Glide
    implementation(platform("androidx.compose:compose-bom:2025.02.00"))
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    // Koin Core for dependency injection
    implementation (libs.xio.insert.koin.koin.core11)
    implementation (libs.koin.android)


    // Lifecycle libraries (optional but recommended)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)


    kapt(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.work.runtime.ktx)

    // Coroutine Support (optional but recommended)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)


    // Glide (Image Loading)
    implementation (libs.glide)
    kapt (libs.compiler)


    // File Provider (if needed for sharing images)
    implementation ("androidx.core:core-ktx:1.13.1")


    implementation ("com.github.alexzhirkevich:custom-qr-generator:2.0.0-alpha01")


    /* // --- Room (Database, DAO, Coroutine support)
     implementation ("androidx.room:room-runtime:2.6.1")
     kapt ("androidx.room:room-compiler:2.6.1")
     implementation ("androidx.room:room-ktx:2.6.1") // Coroutine support

 // --- ViewModel + LiveData + Lifecycle
     implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
     implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

 // --- Kotlin Coroutines
     implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
     implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

 // --- Glide (for loading saved QR image thumbnails)
     implementation ("com.github.bumptech.glide:glide:4.16.0")
     kapt ("com.github.bumptech.glide:compiler:4.16.0")

 // --- Koin (for Dependency Injection)
     implementation ("io.insert-koin:koin-android:3.5.3")
     implementation ("io.insert-koin:koin-androidx-viewmodel:3.5.3") // if using viewModel DSL

 // --- For Date Formatting (optional)
     implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

 // --- File Provider (if needed for sharing images)
     implementation ("androidx.core:core-ktx:1.13.1")
 */

}