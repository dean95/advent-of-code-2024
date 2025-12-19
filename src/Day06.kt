private fun main() {
    fun part1(input: List<String>): Int {
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        fun dfs(r: Int, c: Int, direction: Direction) {
            val nR = r + direction.dx
            val nC = c + direction.dy

            if (nR !in input.indices || nC !in input[nR].indices) return

            if (input[nR][nC] == '#') {
                dfs(r, c, direction.turnRight())
            } else {
                visited.add(nR to nC)
                dfs(nR, nC, direction)
            }
        }

        val r = input.indexOfFirst { '^' in it }
        val c = input[r].indexOf('^')
        visited.add(r to c)

        dfs(r, c, Direction.UP)

        return visited.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day06")
    part1(input).println()
}

enum class Direction(val dx: Int, val dy: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    fun turnRight() = when (this) {
        UP -> RIGHT
        DOWN -> LEFT
        LEFT -> UP
        RIGHT -> DOWN
    }
}
