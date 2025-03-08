plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.squaretest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.squaretest"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Dependency Injection
    api(platform("io.insert-koin:koin-bom:3.5.6"))
    api("io.insert-koin:koin-android:3.5.6")
    api("io.insert-koin:koin-androidx-compose:3.5.6")

    // Networking
    api("com.google.code.gson:gson:2.10.1")
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    api("com.squareup.retrofit2:converter-gson:2.11.0")
    api(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")
    api("com.squareup.okhttp3:okhttp-tls:4.12.0")

    // Constraint Layout
    api("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")
    api("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    api("androidx.constraintlayout:constraintlayout-core:1.1.0-alpha13")

    // App Compat
    api("androidx.appcompat:appcompat:1.6.1")

    // Coil for images
    api("io.coil-kt:coil-compose:2.6.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.7")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.8.7")

    // Splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}