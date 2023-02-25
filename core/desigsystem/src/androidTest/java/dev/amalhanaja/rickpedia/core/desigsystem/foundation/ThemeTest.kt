package dev.amalhanaja.rickpedia.core.desigsystem.foundation

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTheme_withLightThemeAndNotDynamicTheme_colorSchemeShouldBeLight() {
        // When
        composeTestRule.setContent {
            RickpediaTheme(darkTheme = false, dynamicTheme = false) {
                // Then
                assertColorSchemeEquals(LightColorScheme, RickpediaTheme.colorScheme)
            }
        }
    }

    @Test
    fun testTheme_withDarkThemeAndNotDynamicTheme_colorSchemeShouldBeDark() {
        // When
        composeTestRule.setContent {
            RickpediaTheme(darkTheme = true, dynamicTheme = false) {
                // Then
                assertColorSchemeEquals(DarkColorScheme, RickpediaTheme.colorScheme)
            }
        }
    }

    @Test
    fun testTheme_withLightThemeAndDynamicTheme_colorSchemeShouldBeDynamicLight() {
        // When
        composeTestRule.setContent {
            RickpediaTheme(darkTheme = false, dynamicTheme = true) {
                // Then
                assertColorSchemeEquals(dynamicLightColorSchemeWithFallback(), RickpediaTheme.colorScheme)
            }
        }
    }

    @Test
    fun testTheme_withDarkThemeAndDynamicTheme_colorSchemeShouldBeDynamicDark() {
        // When
        composeTestRule.setContent {
            RickpediaTheme(darkTheme = true, dynamicTheme = true) {
                // Then
                assertColorSchemeEquals(dynamicDarkColorSchemeWithFallback(), RickpediaTheme.colorScheme)
            }
        }
    }

    @Test
    fun testThemeSpacingSystem_shouldReturnCurrentSpacingSystem() {
        // When
        composeTestRule.setContent {
            // Then
            RickpediaTheme(spacings = Spacings(xs = 1.dp)) {
                assertEquals(Spacings(xs = 1.dp), RickpediaTheme.spacings)
            }
        }
    }

    @Composable
    private fun dynamicLightColorSchemeWithFallback(): ColorScheme {
        return when {
            isSupportDynamicTheme() -> dynamicLightColorScheme(LocalContext.current)
            else -> LightColorScheme
        }
    }

    @Composable
    private fun dynamicDarkColorSchemeWithFallback(): ColorScheme {
        return when {
            isSupportDynamicTheme() -> dynamicDarkColorScheme(LocalContext.current)
            else -> DarkColorScheme
        }
    }

    private fun assertColorSchemeEquals(
        expected: ColorScheme,
        actual: ColorScheme,
    ) {
        assertEquals(expected.primary, actual.primary)
        assertEquals(expected.onPrimary, actual.onPrimary)
        assertEquals(expected.primaryContainer, actual.primaryContainer)
        assertEquals(expected.onPrimaryContainer, actual.onPrimaryContainer)
        assertEquals(expected.inversePrimary, actual.inversePrimary)
        assertEquals(expected.secondary, actual.secondary)
        assertEquals(expected.onSecondary, actual.onSecondary)
        assertEquals(expected.secondaryContainer, actual.secondaryContainer)
        assertEquals(expected.onSecondaryContainer, actual.onSecondaryContainer)
        assertEquals(expected.tertiary, actual.tertiary)
        assertEquals(expected.onTertiary, actual.onTertiary)
        assertEquals(expected.tertiaryContainer, actual.tertiaryContainer)
        assertEquals(expected.onTertiaryContainer, actual.onTertiaryContainer)
        assertEquals(expected.background, actual.background)
        assertEquals(expected.onBackground, actual.onBackground)
        assertEquals(expected.surface, actual.surface)
        assertEquals(expected.onSurface, actual.onSurface)
        assertEquals(expected.surfaceVariant, actual.surfaceVariant)
        assertEquals(expected.onSurfaceVariant, actual.onSurfaceVariant)
        assertEquals(expected.surfaceTint, actual.surfaceTint)
        assertEquals(expected.inverseSurface, actual.inverseSurface)
        assertEquals(expected.inverseOnSurface, actual.inverseOnSurface)
        assertEquals(expected.error, actual.error)
        assertEquals(expected.onError, actual.onError)
        assertEquals(expected.errorContainer, actual.errorContainer)
        assertEquals(expected.onErrorContainer, actual.onErrorContainer)
        assertEquals(expected.outline, actual.outline)
        assertEquals(expected.outlineVariant, actual.outlineVariant)
        assertEquals(expected.scrim, actual.scrim)
    }
}
