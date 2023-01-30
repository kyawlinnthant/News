plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    api("junit:junit:${Versions.junit}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}")
}