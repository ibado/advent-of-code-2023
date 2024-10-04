package day4

import println
import readInput

fun part1(input: List<String>): Int =
    input
        .map(::calculateWins)
        .sumOf { wins ->
            if (wins > 0) {
                (1..wins).reduce { i, _ -> i.times(2) }
            } else 0
        }

fun part2(input: List<String>): Int =
    input
        .associateTo(mutableMapOf()) { cardLine ->
            extractCardId(cardLine) to Card(calculateWins(cardLine), 1)
        }
        .also { countMap -> repeat(input.size) { computeCards(countMap, it + 1) } }
        .values
        .sumOf { it.copies }

fun main() {
    val input = readInput("4.txt")
    part1(input).println()
    part2(input).println()
}

private fun calculateWins(line: String): Int {
    val r = Regex("[0-9]+")
    val (winning, mine) = line.split(":")[1].split("|")
    val winingList = r.findAll(winning).map { it.value }.toList()
    val mineList = r.findAll(mine).map { it.value }.toList()
    val intersection = mineList.intersect(winingList.toSet())
    return intersection.size
}

data class Card(val wins: Int, val copies: Int)

private fun extractCardId(cardLine: String): Int =
    cardLine.split(":")
        .first()
        .split(" ")
        .last()
        .toInt()

private fun computeCards(countMap: MutableMap<Int, Card>, id: Int) {
    for (i in (id + 1)..(id + countMap[id]!!.wins)) {
        val prev = countMap[i]!!
        countMap[i] = prev.copy(copies = prev.copies + 1)
        computeCards(countMap, i)
    }
}