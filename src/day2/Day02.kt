package day2

import println
import readInput

data class Round(val red: Int, val green: Int, val blue: Int) {
    fun isPossible(): Boolean = red <= 12 && green <= 13 && blue <= 14
}

data class Game(val id: Int, val rounds: List<Round>) {
    fun isPossible(): Boolean = rounds.all(Round::isPossible)

    fun minRequirementPower(): Int = with(rounds) {
        maxOf { it.red } * maxOf { it.green } * maxOf { it.blue }
    }

    companion object {
        fun fromInput(input: String): Game {
            val (idPart, rest) = input.split(":")
            val rounds = rest.split(";").map {
                val red = redRegex.find(it)?.value?.removeSuffix(" red")?.toInt() ?: 0
                val green = greenRegex.find(it)?.value?.removeSuffix(" green")?.toInt() ?: 0
                val blue = blueRegex.find(it)?.value?.removeSuffix(" blue")?.toInt() ?: 0
                Round(red, green, blue)
            }
            return Game(idPart.removePrefix("Game ").toInt(), rounds)
        }
    }
}

val redRegex = Regex("[0-9]+ red")
val greenRegex = Regex("[0-9]+ green")
val blueRegex = Regex("[0-9]+ blue")

fun part1(input: List<String>): Int =
    input.map(Game.Companion::fromInput)
        .sumOf { if (it.isPossible()) it.id else 0 }

fun part2(input: List<String>): Int =
    input.map(Game.Companion::fromInput)
        .sumOf(Game::minRequirementPower)

fun main() {
    val input = readInput("day2/input.txt")
    part1(input).println()
    part2(input).println()
}
