package day9

import println
import readInput

fun main() {
    val input = readInput("day9/input.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Long = input
    .map(::parseInts)
    .sumOf(::processLine)

fun part2(input: List<String>): Long = input
    .map { parseInts(it).reversed() }
    .sumOf(::processLine)

private fun processLine(line: List<Int>): Long =
    generateSequence(line) { it.windowed(2) { (a, b) -> b - a } }
        .takeWhile { item -> item.any { it != 0 } }
        .sumOf(List<Int>::last)
        .toLong()

private fun parseInts(it: String) = it.split(" ").map { it.toInt() }