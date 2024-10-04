package day10

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10KtTest {

     @Test
     fun part1_clean_sample1() {
         val input = """
              .....
              .S-7.
              .|.|.
              .L-J.
              .....
         """.trimIndent()

         assertEquals(4, part1(input.lines()))
     }

    @Test
    fun part1_obfuscated_sample1() {
        val input = """
             -L|F7
             7S-7|
             L|7||
             -L-J|
             L|-JF
         """.trimIndent()

        assertEquals(4, part1(input.lines()))
    }

    @Test
    fun part2_sample1() {
        val input = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........
        """.trimIndent()

        assertEquals(4, part2(input.lines()))
    }
}