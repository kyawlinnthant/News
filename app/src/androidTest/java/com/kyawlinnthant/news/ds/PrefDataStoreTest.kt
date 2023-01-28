package com.kyawlinnthant.news.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyawlinnthant.news.TestCoroutinesRule
import com.kyawlinnthant.news.data.ds.PrefDataStoreImpl
import com.kyawlinnthant.news.data.ds.PrefModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(PrefModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PrefDataStoreTest {

    @get:Rule
    val testRule = TestCoroutinesRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var pref: PrefDataStoreImpl? = null

    @Inject
    lateinit var ds: DataStore<Preferences>

    @Before
    fun setup() {
        hiltRule.inject()
        pref = PrefDataStoreImpl(
            ds = ds,
            io = testRule.testDispatcher
        )
    }

    @After
    fun teardown() {
        pref = null
    }

    @Test
    fun initial_dynamic_is_null() = runTest {
        val dynamic = pref?.pullEnabledDynamicColor()?.first()
        assertThat(dynamic).isNull()
    }

    @Test
    fun initial_theme_is_null() = runTest {
        val theme = pref?.pullAppTheme()?.first()
        assertThat(theme).isNull()
    }

    @Test
    fun put_dynamic_successfully_saved() = runTest {
        val isEnabled = true
        pref?.putEnabledDynamicColor(status = isEnabled)
        val expected = pref?.pullEnabledDynamicColor()?.first()
        assertThat(expected).isEqualTo(isEnabled)
    }

    @Test
    fun put_theme_successfully_saved() = runTest {
        val input = 100
        pref?.putAppTheme(value = input)
        val expected = pref?.pullAppTheme()?.first()
        assertThat(expected).isEqualTo(input)
    }

    @Test
    fun clear_successfully() = runTest {
        pref?.apply {
            putEnabledDynamicColor(status = false)
            putAppTheme(value = 100)
        }
        pref?.clearDs()
        val expected = pref?.pullAppTheme()?.first()
        assertThat(expected).isNull()
    }
}