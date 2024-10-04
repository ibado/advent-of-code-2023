package day12

import println
import readInput

fun main() {
    val input = readInput("12.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Long =
    input.sumOf { line ->
        val (springs, n) = line.split(" ")
        val ns = n.split(",").map(String::toInt)
        val indices =
            springs.mapIndexedNotNull { index, c -> c.takeIf { it == '?' }?.let { index } }
        computeArrangements(springs, indices, ns)
    }

fun computeArrangements(original: String, indices: List<Int>, target: List<Int>): Long {
    if (indices.isEmpty()) return 0
    val variant = StringBuilder(original)
    var count = 0L
    for (s in setOf('#', '.')) {
        for (i in indices) {
            variant.setCharAt(i, s)
        }

        val hashtags = variant
            .split("?", ".")
            .mapNotNull { it.takeIf { it.isNotBlank() }?.length }

        count += if (hashtags == target) 1 else 0 + computeArrangements(
            variant.toString(),
            indices.drop(1),
            target
        )
    }

    return count
}

fun part2(input: List<String>): Long = 0