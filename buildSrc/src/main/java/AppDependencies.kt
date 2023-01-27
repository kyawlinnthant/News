object AppDependencies {

    //project level dependencies
    private val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    private val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val projectDependencies = arrayListOf<String>().apply {
        add(gradle)
        add(kotlin)
    }
    private val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    val projectHilt get() = hilt

    //app level dependencies
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
    val appDependencies = arrayListOf<String>().apply {
        add(coreKtx)
        add(lifecycleKtx)
    }

    //compose
    private val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    private val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    private val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    private val composeUiTest = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private val composeManifestTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    val appCompose = Requirement(
        implementations = arrayListOf<String>().apply {
            add(composeActivity)
            add(composeUi)
            add(composeTooling)
        },
        kapts = null,
        annotationProcessors = null,
        testImplementations = null,
        androidTestImplementations = arrayListOf<String>().apply {
            add(composeJunit)
        },
        debugImplementations = arrayListOf<String>().apply {
            add(composeUiTest)
            add(composeManifestTest)
        },
        kaptAndroidTests = null,
    )
    private val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    val appMaterial get() = material3
    private val splash = "androidx.core:core-splashscreen:${Versions.splash}"
    val appSplash get() = splash
    private val systemUi =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemUi}"
    val appSystemUi get() = systemUi

    //navigation
    private val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    val appNavigation get() = navigationCompose

    //hilt
    private val navigationHilt = "androidx.hilt:hilt-navigation-compose:${Versions.navigationHilt}"
    private val hiltV = "com.google.dagger:hilt-android:${Versions.hilt}"
    private val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    private val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    val appDi = Requirement(
        implementations = arrayListOf<String>().apply {
            add(navigationHilt)
            add(hiltV)
        },
        kapts = arrayListOf<String>().apply {
            add(hiltKapt)
        },
        testImplementations = arrayListOf<String>().apply {
            add(hiltTest)
        },
        androidTestImplementations = arrayListOf<String>().apply {
            add(hiltTest)
        }
    )

    //retrofit
    private val retrofitV = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private val retrofitCoroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    private val localebro = "com.localebro:okhttpprofiler:${Versions.localebro}"
    val appNetwork = Requirement(
        implementations = arrayListOf<String>().apply {
            add(retrofitV)
            add(gson)
            add(okhttp)
            add(retrofitCoroutines)
            add(localebro)
        }
    )

    //room
    private val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private val roomProcessor = "androidx.room:room-compiler:${Versions.room}"
    private val roomKapt = "androidx.room:room-compiler:${Versions.room}"


    val appDb = Requirement(
        implementations = arrayListOf<String>().apply {
            add(roomRuntime)
            add(roomKtx)
        },
        kapts = arrayListOf<String>().apply {
            add(roomKapt)
        },
        annotationProcessors = arrayListOf<String>().apply {
            add(roomProcessor)
        }
    )

    //unit test
    private val junit = "junit:junit:${Versions.junit}"
    private val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    val appUnitTest = arrayListOf<String>().apply {
        add(junit)
        add(coroutinesTest)
    }

    //android test
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val appAndroidTest = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(coroutinesTest)
    }

    //truth
    private val truth = "com.google.truth:truth:${Versions.truth}"
    val appTruth get() = truth

}
