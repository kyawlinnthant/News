import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
@Suppress("UnstableApiUsage") android {
    namespace = "com.kyawlinnthant.news"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.kyawlinnthant.news"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.kyawlinnthant.news.db.DbTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val apiKey = gradleLocalProperties(rootDir).getProperty("API.KEY")
        buildConfigField(type = "String", name = "API_KEY", value = apiKey)
        buildConfigField(type = "String", name = "BASE_URL", value = "\"https://api.themoviedb.org/\"")
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    //app dependencies
    this implements AppDependencies.appDependencies
    this implement AppDependencies.appMaterial
    this implement AppDependencies.appSplash
    this implement AppDependencies.appSystemUi
    //compose
    this needs AppDependencies.appCompose
    this implement AppDependencies.appNavigation
    //di
    this needs AppDependencies.appDi
    //network
    this needs AppDependencies.appNetwork
    //db
    this needs AppDependencies.appDb

    //android test
    this androidTests AppDependencies.appAndroidTest
    //unit test
    this unitTests AppDependencies.appUnitTest
    //google truth
    this unitTest AppDependencies.appTruth
    this androidTest AppDependencies.appTruth
}