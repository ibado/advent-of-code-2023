package day10

import day10.Direction.*
import println
import readInput

enum class Direction {
    UP, DOWN, FORWARD, BACKWARD
}

val nextDirMap = mapOf(
    UP to mapOf('|' to UP, 'F' to FORWARD, '7' to BACKWARD),
    DOWN to mapOf('|' to DOWN, 'J' to BACKWARD, 'L' to FORWARD),
    FORWARD to mapOf('-' to FORWARD, '7' to DOWN, 'J' to UP),
    BACKWARD to mapOf('-' to BACKWARD, 'F' to DOWN, 'L' to UP),
)

fun main() {
    val input = readInput("day10/input.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int =
    buildList {
        input.forEach {
            add(it.asSequence().toList())
        }
    }.let { grid ->
        val start = findStart(grid) ?: error("Boom! There's no starting point")

        listOf(
            solveRecursive(Args(grid, start.copy(first = start.first - 1), UP, 0)),
            solveRecursive(Args(grid, start.copy(first = start.first + 1), DOWN, 0)),
            solveRecursive(Args(grid, start.copy(second = start.second + 1), FORWARD, 0)),
            solveRecursive(Args(grid, start.copy(second = start.second - 1), BACKWARD, 0)),
        ).max().plus(1) / 2
    }

fun part2(input: List<String>): Long = 0

data class Args(
    val grid: List<List<Char>>,
    val point: Pair<Int, Int>,
    val direction: Direction,
    val steps: Int = 0
)

val solveRecursive = DeepRecursiveFunction<Args, Int> {
    val (grid, point, direction, steps) = it
    if (point.first !in grid.indices || point.second !in grid.first().indices) {
        return@DeepRecursiveFunction steps
    }

    val (x, y) = point
    val value = grid[x][y]
    val newDir: Direction = nextDirMap[direction]!![value] ?: return@DeepRecursiveFunction steps

    val newPoint = when (newDir) {
        UP -> point.copy(first = point.first - 1)

        DOWN -> point.copy(first = point.first + 1)

        FORWARD -> point.copy(second = point.second + 1)

        BACKWARD -> point.copy(second = point.second - 1)
    }

    callRecursive(Args(grid, newPoint, newDir, steps + 1))
}

fun findStart(grid: List<List<Char>>): Pair<Int, Int>? {
    for ((xIdx, row) in grid.withIndex()) {
        val yIdx = row.indexOf('S')
        if (yIdx != -1) return xIdx to yIdx
    }

    return null
}