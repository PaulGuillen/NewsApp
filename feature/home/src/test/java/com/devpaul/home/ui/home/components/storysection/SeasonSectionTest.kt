package com.devpaul.home.ui.home.components.storysection

import com.devpaul.home.domain.entity.SeasonDetailEntity
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.home.domain.entity.SeasonYearEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class SeasonSectionTest {

    private val seasons = SeasonEntity(
        years = mapOf(
            "2025" to SeasonYearEntity(
                summer = season("21-12-2025", "20-3-2026")
            ),
            "2026" to SeasonYearEntity(
                autumn = season("20-3-2026", "21-6-2026"),
                winter = season("21-6-2026", "22-9-2026"),
                spring = season("22-9-2026", "21-12-2026"),
                summer = season("21-12-2026", "20-3-2027")
            )
        )
    )

    @Test
    fun `spring appears first followed by the seasonal cycle`() {
        val cards = seasons.toSeasonCards(LocalDate.of(2026, 10, 30))

        assertEquals(listOf("Primavera", "Verano", "Otoño", "Invierno"), cards.map { it.name })
        assertTrue(cards.first().isCurrent)
        assertEquals(1, cards.count { it.isCurrent })
    }

    @Test
    fun `winter appears first on its starting date`() {
        val cards = seasons.toSeasonCards(LocalDate.of(2026, 6, 21))

        assertEquals(listOf("Invierno", "Primavera", "Verano", "Otoño"), cards.map { it.name })
        assertTrue(cards.first().isCurrent)
        assertEquals(1, cards.count { it.isCurrent })
    }

    @Test
    fun `autumn replaces summer on the shared boundary date`() {
        val cards = seasons.toSeasonCards(LocalDate.of(2026, 3, 20))

        assertEquals(listOf("Otoño", "Invierno", "Primavera", "Verano"), cards.map { it.name })
        assertTrue(cards.first().isCurrent)
        assertEquals(1, cards.count { it.isCurrent })
    }

    @Test
    fun `summer appears first in December`() {
        val cards = seasons.toSeasonCards(LocalDate.of(2026, 12, 21))

        assertEquals(listOf("Verano", "Otoño", "Invierno", "Primavera"), cards.map { it.name })
        assertTrue(cards.first().isCurrent)
    }

    @Test
    fun `January uses the summer that started in the previous year`() {
        val cards = seasons.toSeasonCards(LocalDate.of(2026, 1, 15))

        assertEquals(listOf("Verano", "Otoño", "Invierno", "Primavera"), cards.map { it.name })
        assertEquals("21-12-2025", cards.first().startText)
        assertTrue(cards.first().isCurrent)
        assertEquals(4, cards.size)
    }

    @Test
    fun `empty response produces no cards`() {
        assertTrue(SeasonEntity().toSeasonCards(LocalDate.of(2026, 10, 30)).isEmpty())
    }

    @Test
    fun `invalid dates do not mark a season as current`() {
        val response = SeasonEntity(
            years = mapOf("2026" to SeasonYearEntity(spring = season("invalid", "invalid")))
        )

        val cards = response.toSeasonCards(LocalDate.of(2026, 10, 30))

        assertEquals(1, cards.size)
        assertFalse(cards.first().isCurrent)
    }

    private fun season(start: String, end: String) = SeasonDetailEntity(
        startDate = start,
        endDate = end
    )
}
