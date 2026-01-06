private fun main() {
    val dirs = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun getRowsAndColumns(input: List<String>) = input.indices to input[0].indices

    fun countPathsFromZero(
        input: List<String>,
        r: Int,
        c: Int,
        allowRevisitsAcrossPaths: Boolean,
        rows: IntRange,
        columns: IntRange,
        visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ): Int {
        if (r to c in visited) return 0
        visited.add(r to c)

        if (input[r][c].digitToInt() == 9) return 1

        val height = input[r][c].digitToInt()
        var score = 0

        for ((dr, dc) in dirs) {
            val nr = r + dr
            val nc = c + dc

            if (nr in rows && nc in columns) {
                val nextHeight = input[nr][nc].digitToInt()
                if (nextHeight - height == 1) {
                    score += countPathsFromZero(input, nr, nc, allowRevisitsAcrossPaths, rows, columns, visited)
                    if (allowRevisitsAcrossPaths) visited.remove(nr to nc)
                }
            }
        }

        return score
    }

    fun calculateScore(input: List<String>, allowRevisitsAcrossPaths: Boolean): Int {
        val (rows, columns) = getRowsAndColumns(input)
        var sum = 0

        for (r in input.indices) {
            for (c in input[r].indices) {
                if (input[r][c].digitToInt() == 0) {
                    sum += countPathsFromZero(input, r, c, allowRevisitsAcrossPaths, rows, columns)
                }
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int = calculateScore(input, allowRevisitsAcrossPaths = false)

    fun part2(input: List<String>): Int = calculateScore(input, allowRevisitsAcrossPaths = true)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
