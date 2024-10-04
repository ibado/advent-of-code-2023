package day15

import println
import readInput

fun main() {
    val input = readInput("15.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int =
    input.first()
        .split(",")
        .sumOf(::hash)

fun part2(input: List<String>): Int =
    input.first()
        .split(",")
        .map {
            val (label, operand) = it.split("-", "=")
            Step(label, operand.toIntOrNull())
        }
        .groupBy { hash(it.label) }
        .map { (boxNum, steps) ->
            steps.fold(mutableMapOf<String, Int>()) { acc, (label, lensNum) ->
                if (lensNum == null) acc.remove(label)
                else acc[label] = lensNum
                acc
            }.values.withIndex().sumOf { (i, lensNum) ->
                (boxNum + 1) * (i + 1) * lensNum
            }
        }
        .sum()


private fun hash(step: String): Int =
    step.fold(0) { acc, char ->
        (acc + char.code) * 17 % 256
    }

data class Step(val label: String, val lensNum: Int?)
