plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    buildFeatures {
        //necesario
        viewBinding=true
        dataBinding=true
    }
    namespace = "com.david.crudequipopokemon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.david.crudequipopokemon"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //appwrite
    implementation("io.appwrite:sdk-for-kotlin:5.0.1")

    //firebase
    implementation ("com.google.firebase:firebase-database:20.0.2")
    implementation ("com.google.firebase:firebase-core:20.0.0")
    implementation ("com.google.firebase:firebase-storage:20.0.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.firebase:firebase-analytics:20.0.2")
    implementation ("com.google.android.gms:play-services-auth:20.1.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1")
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-storage-ktx")
    //glide , para cargar una imagen desde un enlace
    implementation("com.github.bumptech.glide:glide:4.16.0") // Use the latest version
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}