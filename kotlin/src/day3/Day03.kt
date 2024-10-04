package day3

import println
import readInput

const val NOT_SET = -1

fun Char.isSymbol() = !isDigit().or(this == '.')

data class Number(val startAt: Int, val endAt: Int, val value: Int) {
    constructor(startAt: Int, endAt: Int, line: String)
            : this(startAt, endAt, line.substring(startAt, endAt + 1).toInt())

    fun isAdjacent(symbol: Symbol): Boolean =
        symbol.idx in (if (startAt == 0) 0 else startAt - 1)..(endAt + 1)
}

data class Symbol(val idx: Int, val idy: Int)

fun parseLine(
    row: Int,
    line: String,
    storeSymbol: (Char) -> Boolean = { true }
): Pair<List<Symbol>, List<Number>> {
    val symbols = mutableListOf<Symbol>()
    val numbers = mutableListOf<Number>()
    var numStartAt = NOT_SET
    fun addNumber(idx: Int) {
        if (numStartAt == NOT_SET) return
        numbers.add(Number(numStartAt, idx - 1, line))
        numStartAt = NOT_SET
    }
    for ((idx, char) in line.withIndex()) {
        when {
            char == '.' -> addNumber(idx)
            char.isSymbol() -> {
                if (storeSymbol(char)) symbols.add(Symbol(idx, row))
                addNumber(idx)
            }

            idx < line.length - 1 -> {
                if (numStartAt == NOT_SET) numStartAt = idx // beginning or middle of a number
            }

            else -> { // the last token is a number
                val from = if (numStartAt != NOT_SET) numStartAt else idx
                numbers.add(Number(from, idx, line))
            }
        }
    }

    return symbols to numbers
}

fun part1(input: List<String>): Int {
    var prevSymbols: List<Symbol> = emptyList()

    return input.mapIndexed(::parseLine)
        .windowed(2, 1, true) { window ->
            val symbols = prevSymbols + window.flatMap { (symbols, _) -> symbols }
            val (currLineSymbols, currLineNums) = window.first()
            prevSymbols = currLineSymbols
            currLineNums
                .filter { num -> symbols.any(num::isAdjacent) }
                .sumOf(Number::value)
        }.sum()
}

fun part2(input: List<String>): Int {
    var prevLineGears: List<Symbol> = emptyList() // only gears ('*')
    val numWithAdjacent = input.mapIndexed { i, it -> parseLine(i, it) { c -> c == '*' } }
        .windowed(2, 1, true) { window ->
            val gears = prevLineGears + window.flatMap { (gears, _) -> gears }
            val (currLineGears, currLineNums) = window.first()
            prevLineGears = currLineGears
            currLineNums.mapNotNull { num ->
                gears.fold(mutableListOf<Symbol>()) { acc, gear ->
                    acc.also {
                        if (num.isAdjacent(gear)) acc.add(gear)
                    }
                }.ifEmpty { null }?.let { num to it }
            }
        }.flatten()

    return numWithAdjacent.foldIndexed(0) { index, sum, (num, gears) ->
        numWithAdjacent.slice((index + 1)..<numWithAdjacent.size)
            .fold(sum) { acc, (n, gs) ->
                acc + if (gears.intersect(gs.toSet()).isNotEmpty()) {
                    num.value * n.value
                } else 0
            }
    }
}

fun main() {
    val input = readInput("3.txt")
    part1(input).println()
    part2(input).println()
}
