import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        input.forEach { line ->
            val (a, b) = line.trim().split(Regex("\\s+")).map { it.toInt() }
            firstList.add(a)
            secondList.add(b)
        }

        return firstList to secondList
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        val firstSorted = firstList.sorted()
        val secondSorted = secondList.sorted()

        var sum = 0
        firstSorted.zip(secondSorted).forEach { (a, b) ->
            val dist = abs(a - b)
            sum += dist
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        val freqMap = secondList.groupBy { it }.mapValues { (_, value) -> value.count() }

        var res = 0
        firstList.forEach { a ->
            val b = freqMap[a] ?: 0
            res += a * b
        }

        return res
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
