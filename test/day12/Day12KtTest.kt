package day12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day12KtTest {

    @Test
    fun part1_sample() {
        val input = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent().lines()

        assertEquals(21, part1(input))
    }
}