package day8

import println
import readInput
import java.math.BigInteger

data class Node(val left: String, val right: String)

fun findMinSteps(
    node: Node,
    nodes: Map<String, Node>,
    directions: String,
    target: (String) -> Boolean
): Long {
    var i = 0
    var n = node
    var count = 0L
    while (true) {
        val next = when (directions[i]) {
            'L' -> n.left
            'R' -> n.right
            else -> error("direction is invalid!")
        }
        count++
        if (target(next)) return count
        n = nodes[next]!!
        i = (i + 1) % directions.length
    }
}

fun parseNodes(input: List<String>): MutableMap<String, Node> =
    input.drop(2).fold(mutableMapOf()) { acc, row ->
        val (value, left, right) = row.split("=", ",")
        acc[value.trim()] = Node(
            left = left.trim().removePrefix("("),
            right = right.trim().removeSuffix(")")
        )
        acc
    }

fun lcm(x: Long, y: Long): Long {
    val xbi = BigInteger.valueOf(x)
    val ybi = BigInteger.valueOf(y)
    val lcm = xbi / xbi.gcd(ybi) * ybi
    return lcm.longValueExact()
}

fun part1(input: List<String>): Long =
    parseNodes(input).let { nodes ->
        findMinSteps(nodes["AAA"]!!, nodes, input.first()) { it == "ZZZ" }
    }

fun part2(input: List<String>): Long =
    parseNodes(input).let { nodes: Map<String, Node> ->
        nodes
            .mapNotNull { it.takeIf { it.key.endsWith("A") }?.value }
            .map { targetNode ->
                findMinSteps(targetNode, nodes, input.first()) { it.endsWith("Z") }
            }.reduce(::lcm)
    }

fun main() {
    val input = readInput("8.txt")
    part1(input).println()
    part2(input).println()
}