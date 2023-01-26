import org.gradle.api.artifacts.dsl.DependencyHandler

infix fun DependencyHandler.dependent(dependencies : List<String>){
    dependencies.forEach {
        add("classpath",it)
    }
}

infix fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

infix fun DependencyHandler.annotate(list: List<String>) {
    list.forEach { dependency ->
        add("annotationProcessor", dependency)
    }
}

infix fun DependencyHandler.need(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

infix fun DependencyHandler.androidTest(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

infix fun DependencyHandler.unitTest(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

infix fun DependencyHandler.debugTest(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}