buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        this dependent AppDependencies.dependency
    }
}
tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}