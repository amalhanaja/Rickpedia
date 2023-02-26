package dev.amalhanaja.rickpedia.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.mockk.spyk
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RickpediaPreferenceDataStoreTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())
    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var subject: RickpediaPreferenceDataStore

    @Before
    fun setUp() {
        testDataStore = spyk(
            PreferenceDataStoreFactory.create(
                scope = testScope,
                produceFile = { tmpFolder.newFile("test.preferences_pb") },
            ),
        )
        subject = RickpediaPreferenceDataStore(testDataStore)
    }

    @Test
    fun whenInitiallyGetAllCharacterNextPage_thenReturnNull() = testScope.runTest {
        // When
        val result = subject.allCharacterNextPage.first()

        // Then
        assertNull(result)
    }

    @Test
    fun whenSetAllCharacterNextPage_thenUpdateGetAllCharacterNextPage() = testScope.runTest {
        // When
        subject.setAllCharacterNextPage(12052022)

        // Then
        assertEquals(12052022, subject.allCharacterNextPage.first())
    }
}
