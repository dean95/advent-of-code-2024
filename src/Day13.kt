private fun main() {
    val regex = """\d+""".toRegex()

    fun parseNumbers(s: String) = regex.findAll(s).map { it.value.toLong() }.toList()

    fun solve(input: List<String>, prizeOffset: Long = 0L): Long {
        val blocks = input.filter { it.isNotBlank() }.chunked(3)

        var sum = 0L
        for (block in blocks) {
            val (ax, ay) = parseNumbers(block[0])
            val (bx, by) = parseNumbers(block[1])
            val (px0, py0) = parseNumbers(block[2])
            val px = px0 + prizeOffset
            val py = py0 + prizeOffset

            val denom = (ax * by - ay * bx)
            val ca = (px * by - py * bx).toDouble() / denom
            val cb = (px - ax * ca) / bx

            if (ca % 1 == 0.0 && cb % 1 == 0.0) sum += ca.toLong() * 3 + cb.toLong()
        }

        return sum
    }

    fun part1(input: List<String>) = solve(input)

    fun part2(input: List<String>) = solve(input, prizeOffset = 10_000_000_000_000)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
