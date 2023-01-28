package com.kyawlinnthant.news.data.ds

sealed class ThemeType(val value: Int) {
    object Default : ThemeType(1)
    object Light : ThemeType(2)
    object Dark : ThemeType(3)

    fun Int.asThemeType(): ThemeType {
        return when (this) {
            1 -> Default
            2 -> Light
            3 -> Dark
            else -> Default
        }
    }
}

