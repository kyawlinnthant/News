import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
@Suppress("UnstableApiUsage") android {
    namespace = "com.kyawlinnthant.news"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kyawlinnthant.news"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val apiKey = gradleLocalProperties(rootDir).getProperty("API.KEY")
        buildConfigField(type = "String", name = "API_KEY", value = apiKey)
        buildConfigField(type = "String", name = "BASE_URL", value = "\"https://newsapi.org/\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    this need AppDependencies.app
    //compose
    this need AppDependencies.compose
    //network
    this need AppDependencies.network
    //db
    this need AppDependencies.db
    this kapt AppDependencies.dbKapt
    this annotate AppDependencies.dbAnnotation
    //di
    this need AppDependencies.di
    this kapt AppDependencies.diKapt
    //android test
    this androidTest AppDependencies.androidTest
    //unit test
    this unitTest AppDependencies.unitTest
    //debug test
    this debugTest AppDependencies.debugTest
}