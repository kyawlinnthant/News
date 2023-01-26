object AppDependencies {

    //dependency
    private val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    private val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    private val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    //android ui
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
    private val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    private val splash = "androidx.core:core-splashscreen:${Versions.splash}"

    //compose
    private val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    private val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"

    //retrofit
    private val retrofitV = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private val retrofitCoroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    private val localebro = "com.localebro:okhttpprofiler:${Versions.localebro}"

    //database
    private val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private val roomProcessor = "androidx.room:room-compiler:${Versions.room}"
    private val roomKapt = "androidx.room:room-compiler:${Versions.room}"

    //dependency injection
    private val hiltV = "com.google.dagger:hilt-android:${Versions.hilt}"
    private val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    private val composeUiTest = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private val composeManifestTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"

    val dependency = arrayListOf<String>().apply {
        add(gradle)
        add(kotlin)
        add(hilt)
    }
    val app = arrayListOf<String>().apply {
        add(coreKtx)
        add(lifecycleKtx)
        add(material3)
        add(splash)
    }
    val compose = arrayListOf<String>().apply {
        add(composeActivity)
        add(composeUi)
        add(composeTooling)
    }
    val network = arrayListOf<String>().apply {
        add(retrofitV)
        add(gson)
        add(okhttp)
        add(retrofitCoroutines)
        add(localebro)
    }
    val db = arrayListOf<String>().apply {
        add(roomRuntime)
        add(roomKtx)
    }
    val di = arrayListOf<String>().apply {
        add(hiltV)
    }
    val diKapt = arrayListOf<String>().apply {
        add(hiltKapt)
    }
    val dbKapt = arrayListOf<String>().apply {
        add(roomKapt)
    }
    val dbAnnotation = arrayListOf<String>().apply {
        add(roomProcessor)
    }
    val androidTest = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(composeJunit)
    }
    val unitTest = arrayListOf<String>().apply {
        add(junit)
    }
    val debugTest = arrayListOf<String>().apply {
        add(composeUiTest)
        add(composeManifestTest)
    }

}
