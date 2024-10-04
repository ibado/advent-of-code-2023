package day9

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day09KtTest {

    @Test
    fun part1_sample() {
        val input = """
             0 3 6 9 12 15
             1 3 6 10 15 21
             10 13 16 21 30 45
        """.trimIndent()

        assertEquals(114, part1(input.lines()))
    }

    @Test
    fun part2_sample() {
        val input = """
             0 3 6 9 12 15
             1 3 6 10 15 21
             10 13 16 21 30 45
        """.trimIndent()

        assertEquals(2, part2(input.lines()))
    }
}