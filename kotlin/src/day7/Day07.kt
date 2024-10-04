package day7

import println
import readInput

val cardSortedPart1 = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
val cardSortedPart2 = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

enum class HandType(val num: Int) {
    FIVE(1),
    FOUR(2),
    FULL(3),
    THREE(4),
    TWO_PAIR(5),
    ONE_PAIR(6),
    HIGH_CARD(7);
}

data class Hand(val cards: String, val original: String, val type: HandType, val bid: Int)

fun Hand(cards: String, bid: Int): Hand =
    Hand(cards, cards, resolveType(cards), bid)

fun Hand.fromOriginal(cards: String): Hand =
    Hand(cards, original, resolveType(cards), bid)

fun resolveType(cards: String): HandType {
    val repeated = cards.groupingBy { it }.eachCount().values
    return when (repeated.max()) {
        5 -> HandType.FIVE
        4 -> HandType.FOUR
        3 -> if (2 in repeated) HandType.FULL else HandType.THREE
        2 -> if (repeated.count { it == 2 } == 2) HandType.TWO_PAIR else HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
    }
}

fun comparator(cards: Collection<Char>): Comparator<Hand> =
    Comparator { one, other ->
        if (other.cards == one.cards && other.original == one.original) 0
        else if (one.type == other.type) {
            (0..<5).fold(0) { acc, i ->
                val thisValue = cards.indexOf(one.original[i])
                val otherValue = cards.indexOf(other.original[i])
                if (thisValue != otherValue) return@Comparator otherValue - thisValue
                else acc
            }
        } else other.type.num - one.type.num
    }

fun maximizeCard(hand: Hand): Hand =
    hand.cards.foldIndexed(mutableListOf<Int>()) { idx, acc, char ->
        acc.apply { if (char == 'J') add(idx) }
    }.let { indices ->
        mutableSetOf<Hand>()
            .also { computeVariants(it, hand, indices) }
            .maxWith(comparator(cardSortedPart2))
    }

fun computeVariants(variants: MutableSet<Hand>, hand: Hand, indices: List<Int>) {
    if (indices.isEmpty()) return
    for (i in indices) {
        for (symbol in cardSortedPart2) {
            val variant = hand.fromOriginal(
                hand.cards.replaceRange(i, i + 1, symbol.toString()),
            )
            variants.add(variant)
            computeVariants(variants, variant, indices.drop(1))
        }
    }
}

fun solve(
    input: List<String>,
    sortedCardValues: List<Char>,
    createHand: (cards: String, bid: Int) -> Hand
): Int = input
    .map { line ->
        line.split(" ").let { (cards, bid) -> createHand(cards, bid.toInt()) }
    }
    .sortedWith(comparator(sortedCardValues))
    .foldIndexed(0) { i, sum, hand -> sum + hand.bid * (i + 1) }

fun part1(input: List<String>): Int = solve(input, cardSortedPart1, ::Hand)

fun part2(input: List<String>): Int = solve(input, cardSortedPart2) { cards, bid ->
    val hand = Hand(cards, bid)
    if ('J' in cards && hand.type != HandType.FIVE) maximizeCard(hand) else hand
}

fun main() {
    val input = readInput("7.txt")
    part1(input).println()
    part2(input).println()
}