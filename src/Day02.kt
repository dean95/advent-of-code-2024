private fun main() {
    fun isSafe(numbers: List<Int>): Boolean {
        val zipped = numbers.zipWithNext()
        val allDec = zipped.all { (a, b) ->
            a > b && a - b in 1..3
        }
        val allInc = zipped.all { (a, b) ->
            b > a && b - a in 1..3
        }
        return allInc || allDec
    }

    fun part1(input: List<String>): Int {
        var validCount = 0

        input.forEach { line ->
            if (isSafe(line.split(' ').map(String::toInt))) validCount++
        }

        return validCount
    }

    fun part2(input: List<String>): Int {
        var validCount = 0
        input.forEach { line ->
            val numbers = line.split(' ').map(String::toInt)
            for (i in numbers.indices) {
                if (isSafe(numbers.toMutableList().apply { removeAt(i) })) {
                    validCount++
                    break
                }
            }
        }
        return validCount
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
