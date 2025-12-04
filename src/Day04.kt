private fun main() {
    fun part1(input: List<String>): Int {
        val directions = listOf(
            -1 to 0, 1 to 0, 0 to -1, 0 to 1,
            -1 to -1, -1 to 1, 1 to -1, 1 to 1
        )

        fun searchXmas(prefix: String, row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
            if (row !in input.indices || col !in input[row].indices) return false
            if (!"XMAS".startsWith(prefix)) return false
            if (prefix + input[row][col] == "XMAS") return true

            return searchXmas(prefix + input[row][col], row + dRow, col + dCol, dRow, dCol)
        }

        var count = 0
        for (row in input.indices) {
            for (col in input[row].indices) {
                for ((dRow, dCol) in directions) {
                    if (searchXmas("", row, col, dRow, dCol)) count++
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val mainDiagonalOffsets = listOf(-1 to -1, 1 to 1)
        val antiDiagonalOffsets = listOf(-1 to 1, 1 to -1)
        val requiredChars = setOf('M', 'S')

        var result = 0
        for (row in input.indices) {
            columns@ for (col in input[row].indices) {
                if (input[row][col] != 'A') continue@columns
                val diagonals = listOf(mainDiagonalOffsets.toMutableList(), antiDiagonalOffsets.toMutableList())
                for (diagonalOffsets in diagonals) {
                    val remainingChars = requiredChars.toMutableSet()
                    while (diagonalOffsets.isNotEmpty()) {
                        val (dRow, dCol) = diagonalOffsets.removeLast()
                        val newRow = row + dRow
                        val newCol = col + dCol
                        if (newRow !in input.indices || newCol !in input[row].indices) continue@columns
                        val foundChar = input[newRow][newCol]
                        if (!remainingChars.remove(foundChar)) continue@columns
                    }
                }
                result++
            }
        }

        return result
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
