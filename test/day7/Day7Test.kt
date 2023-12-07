package day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun `comparator should works as expected for part2`() {

        val max = listOf(
            Hand("AJJJJ", "AJJJ2", HandType.FOUR, 123),
            Hand("AQQQQ", "AQQQQ", HandType.FOUR, 123),
            Hand("T3333", "T3333", HandType.FOUR, 123),
            Hand("22222", "J2222", HandType.FIVE, 123),
            Hand("22222", "22222", HandType.FIVE, 123),
        ).maxWith(comparator(cardSortedPart2))

        assertEquals(Hand("22222", "22222", HandType.FIVE, 123), max)
    }
}