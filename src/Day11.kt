private fun main() {

    fun normalize(s: String) = s.trimStart('0').ifEmpty { "0" }

    fun blinkOne(s: String): List<String> =
        when {
            s == "0" -> listOf("1")
            s.length % 2 == 0 -> {
                val mid = s.length / 2
                val left = normalize(s.take(mid))
                val right = normalize(s.drop(mid))
                listOf(left, right)
            }

            else -> listOf((s.toLong() * 2024).toString())
        }


    fun countAfterBlinks(input: String, blink: Int): Long {
        var counts = input.split(' ').groupingBy { it }.eachCount().mapValues { (_, c) -> c.toLong() }

        repeat(blink) {
            val next = mutableMapOf<String, Long>()
            for ((stone, c) in counts) {
                for (out in blinkOne(stone)) {
                    next[out] = (next[out] ?: 0L) + c
                }
            }
            counts = next
        }

        return counts.values.sum()
    }

    fun part1(input: String) = countAfterBlinks(input, 25)
    fun part2(input: String) = countAfterBlinks(input, 75)

    val input = readInput("Day11")
    part1(input.first()).println()
    part2(input.first()).println()
}
