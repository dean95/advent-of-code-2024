import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> = input.map { line ->
        line.split("\\s+".toRegex()).map(String::toInt).let { it[0] to it[1] }
    }.unzip()

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        return firstList.sorted()
            .zip(secondList.sorted())
            .sumOf { (firstNumber, secondNumber) -> abs(firstNumber - secondNumber) }
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        val numberFrequencyMap = secondList.groupingBy { it }.eachCount()

        return firstList.sumOf { n ->
            n * numberFrequencyMap.getOrDefault(n, 0)
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
