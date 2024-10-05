package day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11KtTest {

    @Test
    fun part1_sample() {

        val input = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent()

        assertEquals(374, solve(input.lines(), 1))
    }

    @Test
    fun part2_sample() {

        val input = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent()

        assertEquals(8410, solve(input.lines(), 99))
    }
}