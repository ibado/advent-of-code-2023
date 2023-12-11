package day11

import println
import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day11/input.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Long = solve(input, 1)

fun part2(input: List<String>): Long = solve(input, 999_999)

fun solve(input: List<String>, x: Int): Long {
    val (emptyRows, emptyCols) = computeEmpty(input)
    val things: List<Pair<Int, Int>> = findGalaxies(input)
    var sum = 0L
    for (i in things.indices) {
        for (j in (i + 1)..<things.size) {
            sum += shortestPath(things[i], things[j], emptyRows, emptyCols, x)
        }
    }
    return sum
}

fun shortestPath(
    a: Pair<Int, Int>,
    b: Pair<Int, Int>,
    emptyRows: List<Int>,
    emptyCols: List<Int>,
    multiplier: Int
): Long {
    val (ax, ay) = a
    val (bx, by) = b

    val xr = min(ax, bx)..max(ax, bx)
    val yr = min(ay, by)..max(ay, by)

    val xe = emptyRows.fold(0) { acc, i -> acc + if (i in xr) 1 else 0 } * multiplier
    val ye = emptyCols.fold(0) { acc, i -> acc + if (i in yr) 1 else 0 } * multiplier

    return abs(ax - bx) + abs(ay - by).toLong() + xe + ye
}

fun findGalaxies(expanded: List<String>): List<Pair<Int, Int>> {
    val galaxies = mutableListOf<Pair<Int, Int>>()
    for ((x, row) in expanded.withIndex()) {
        for ((y, char) in row.withIndex()) {
            if (char == '#') galaxies.add(x to y)
        }
    }
    return galaxies
}

fun computeEmpty(input: List<String>): Pair<List<Int>, List<Int>> {
    val emptyRows = mutableListOf<Int>()
    val nonEmptyCols = mutableSetOf<Int>()

    for ((i, row) in input.withIndex()) {
        val m = mutableSetOf<Int>()
        for((j, column) in row.withIndex()) {
            if (column == '#') m.add(j)
        }
        if (m.isEmpty()) emptyRows.add(i)
        nonEmptyCols.addAll(m)
    }

    val columns = (0..<input.first().length) - nonEmptyCols
    return emptyRows to columns
}