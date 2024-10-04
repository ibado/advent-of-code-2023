package day6

import println
import readInput

data class Race(val time: Long, val distance: Long)

fun extractNums(input: String, prefix: String): List<Long> =
    input.removePrefix(prefix)
        .trim()
        .split(" ")
        .filterNot(String::isBlank)
        .map(String::toLong)

fun extractNum(input: String, prefix: String): Long =
    input.removePrefix(prefix)
        .trim()
        .replace(" ", "")
        .toLong()

fun parseRaces(input: List<String>): List<Race> {
    val times = extractNums(input.first(), "Time:")
    val distances = extractNums(input[1], "Distance:")
    return times.zip(distances).map { (time, distance) -> Race(time, distance) }
}

fun parseSingleRace(input: List<String>): Race {
    val time = extractNum(input.first(), "Time:")
    val distance = extractNum(input[1], "Distance:")
    return Race(time, distance)
}

fun calculateWins(race: Race): Int =
    (1..<race.time).fold(0) { wins, i ->
        wins + if (i * (race.time - i) > race.distance) 1 else 0
    }

fun part1(input: List<String>): Int =
    parseRaces(input)
        .map(::calculateWins)
        .reduce(Int::times)

fun part2(input: List<String>): Int {
    val race = parseSingleRace(input)
    return calculateWins(race)
}

fun main() {
    val input = readInput("6.txt")
    part1(input).println()
    part2(input).println()
}