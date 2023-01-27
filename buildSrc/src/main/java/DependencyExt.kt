import org.gradle.api.artifacts.dsl.DependencyHandler

infix fun DependencyHandler.dependents(dependencies: List<String>) {
    dependencies.forEach {
        add("classpath", it)
    }
}

infix fun DependencyHandler.dependent(dependency: String) {
    add("classpath", dependency)
}

infix fun DependencyHandler.implements(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

infix fun DependencyHandler.implement(dependency: String) {
    add("implementation", dependency)
}

infix fun DependencyHandler.kapts(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

infix fun DependencyHandler.kapt(dependency: String) {
    add("kapt", dependency)
}

infix fun DependencyHandler.annotates(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("annotationProcessor", dependency)
    }
}

infix fun DependencyHandler.annotate(dependency: String) {
    add("annotationProcessor", dependency)
}


infix fun DependencyHandler.androidTests(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

infix fun DependencyHandler.androidTest(dependency: String) {
    add("androidTestImplementation", dependency)
}

infix fun DependencyHandler.unitTests(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

infix fun DependencyHandler.unitTest(dependency: String) {
    add("testImplementation", dependency)
}

infix fun DependencyHandler.debugTests(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

infix fun DependencyHandler.debugTest(dependency: String) {
    add("debugImplementation", dependency)
}

infix fun DependencyHandler.kaptTest(dependency: String) {
    add("kaptAndroidTest", dependency)
}

data class Requirement(
    val implementations: List<String>? = null,
    val kapts: List<String>? = null,
    val annotationProcessors: List<String>? = null,
    val testImplementations: List<String>? = null,
    val androidTestImplementations: List<String>? = null,
    val debugImplementations: List<String>? = null,
    val kaptAndroidTests: List<String>? = null,
)

infix fun DependencyHandler.needs(requirement: Requirement) {
    requirement.apply {
        implementations?.let {
            it.forEach { dependency ->
                add("implementation", dependency)
            }
        }
        kapts?.let {
            it.forEach { dependency ->
                add("kapt", dependency)
            }
        }
        annotationProcessors?.let {
            it.forEach { dependency ->
                add("annotationProcessor", dependency)
            }
        }
        androidTestImplementations?.let {
            it.forEach { dependency ->
                add("androidTestImplementation", dependency)
            }
        }
        testImplementations?.let {
            it.forEach { dependency ->
                add("testImplementation", dependency)
            }
        }
        debugImplementations?.let {
            it.forEach { dependency ->
                add("debugImplementation", dependency)
            }
        }
        kaptAndroidTests?.let {
            it.forEach { dependency ->
                add("kaptAndroidTest", dependency)
            }
        }

    }
}


