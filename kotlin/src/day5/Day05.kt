package day5

import println
import readInput
import kotlin.math.min

data class SeedMapEntry(val destinationRange: LongRange, val sourceRange: LongRange)

private fun computeMaps(input: List<String>): List<List<SeedMapEntry>> {
    val maps = mutableListOf<List<SeedMapEntry>>()
    var currMap = mutableListOf<SeedMapEntry>()
    for ((i, line) in input.withIndex()) {
        if (i < 2) continue
        when {
            line.isBlank().or(i == input.size - 1) -> {
                maps.add(currMap)
                currMap = mutableListOf()
            }

            line.first().isLetter() -> continue
            line.first().isDigit() -> {
                val (dest, src, range) = line.split(" ")
                val srcNum = src.toLong()
                val destNum = dest.toLong()
                val rangeNum = range.toLong()
                currMap.add(SeedMapEntry(destNum..<destNum + rangeNum, srcNum..<srcNum + rangeNum))
            }
        }
    }
    return maps
}

fun part1(input: List<String>): Int {
    val seeds = input.first()
        .removePrefix("seeds: ")
        .trim()
        .split(" ")
        .map { it.toLong() }

    val seedMaps = computeMaps(input)

    var result = Long.MAX_VALUE
    for (seed in seeds) {
        var value = seed
        for (seedMap in seedMaps) {
            val entry = seedMap.find { value in it.sourceRange } ?: continue
            value = entry.destinationRange.first + (value - entry.sourceRange.first)
        }
        result = min(value, result)
    }
    return result.toInt()
}

fun part2(input: List<String>): Int {
    val seeds = input.first()
        .removePrefix("seeds: ")
        .trim()
        .split(" ")
        .map { it.toLong() }
        .windowed(2, 2) { window ->
            val (first, second) = window
            first..<(first + second)
        }

    val seedMaps = computeMaps(input)

    var result = Long.MAX_VALUE
    for (seedRange in seeds) { // TODO: run it in parallel using coroutines
        for (seed in seedRange) {
            var value = seed
            for (seedMap in seedMaps) {
                val l = seedMap.find { value in it.sourceRange } ?: continue
                value = l.destinationRange.first + (value - l.sourceRange.first)
            }
            result = min(value, result)
        }
    }

    return result.toInt()
}

fun main() {
    val input = readInput("5.txt")
    part1(input).println()
    part2(input).println()
}