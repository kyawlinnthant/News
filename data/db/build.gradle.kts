plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
@Suppress("UnstableApiUsage")
android {
    namespace = "com.kyawlinnthant.db"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "com.kyawlinnthant.db.DbTestRunner"

        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
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

    //db
    this needs AppDependencies.appDb
    //di
    this needs AppDependencies.appDi

    //android test
    this androidTests AppDependencies.appAndroidTest
    //unit test
    this unitTests AppDependencies.appUnitTest
    //google truth
    this androidTest AppDependencies.appTruth

}