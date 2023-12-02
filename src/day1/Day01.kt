package day1

import println
import readInput

val patterns = mapOf(
    "oneight" to "18",
    "twone" to "21",
    "threeight" to "38",
    "fiveight" to "58",
    "sevenine" to "79",
    "eightwo" to "82",
    "eighthree" to "83",
    "nineight" to "98",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

fun part1(input: List<String>): Int =
    input.sumOf { line ->
        val first = line.find(Char::isDigit)
        val last = line.findLast(Char::isDigit)
        "$first$last".toInt()
    }

fun part2(input: List<String>): Int {
    val regex = Regex(patterns.keys.joinToString("|") + "|[1-9]")
    return input.sumOf { line ->
        val matches = regex.findAll(line)
        val first = matches.first().value.let {
            if (it.length != 1) patterns[it]!!.first() else it
        }
        val last = matches.last().value.let {
            if (it.length != 1) patterns[it]!!.last() else it
        }

        "$first$last".toInt()
    }
}

fun main() {
    val input = readInput("day1/input.txt")
    part1(input).println()
    part2(input).println()
}
