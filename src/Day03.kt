private fun main() {
    fun part1(input: String): Long {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        return regex.findAll(input).map { result ->
            val (x, y) = result.destructured
            x.toLong() * y.toLong()
        }.sum()
    }

    fun part2(input: String): Long {
        val regex = """do\(\)|don't\(\)|mul\((\d+),(\d+)\)""".toRegex()
        var enabled = true
        var sum = 0L

        regex.findAll(input).forEach { matchResult ->
            when (matchResult.value) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else -> {
                    val (x, y) = matchResult.destructured
                    if (enabled) {
                        sum += x.toLong() * y.toLong()
                    }
                }
            }
        }

        return sum
    }

    val input = readText("Day03")
    part1(input).println()
    part2(input).println()
}
