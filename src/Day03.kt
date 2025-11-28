private fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("""mul\((\d+),(\d+)\)""")

        var res = 0
        for (line in input) {
            for (match in regex.findAll(line)) {
                val (a, b) = match.destructured
                res += a.toInt() * b.toInt()
            }
        }

        return res
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")

        var enabled = true
        var res = 0
        for (line in input) {
            for (match in regex.findAll(line)) {
                val value = match.value
                when {
                    value == "do()" -> enabled = true
                    value == "don't()" -> enabled = false
                    value.startsWith("mul(") -> {
                        val (a, b) = match.destructured
                        res += if (enabled) a.toInt() * b.toInt() else 0
                    }
                }
            }
        }

        return res
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
