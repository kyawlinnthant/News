buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        this dependents AppDependencies.projectDependencies
        this dependent AppDependencies.projectHilt
    }
}
tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}
