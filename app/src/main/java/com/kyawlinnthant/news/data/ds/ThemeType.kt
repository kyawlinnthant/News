package com.kyawlinnthant.news.data.ds

enum class ThemeType {
    DEFAULT,
    LIGHT,
    DARK
}

fun Int.asThemeType(): ThemeType {
    return when (this) {
        1 -> ThemeType.DEFAULT
        2 -> ThemeType.LIGHT
        3 -> ThemeType.DARK
        else -> ThemeType.DEFAULT
    }
}

fun ThemeType.toInt(): Int {
    return when (this) {
        ThemeType.DEFAULT -> 1
        ThemeType.LIGHT -> 2
        ThemeType.DARK -> 3
    }
}

