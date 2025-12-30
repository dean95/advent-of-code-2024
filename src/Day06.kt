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

    fun findStartPosition(input: List<CharArray>): Pair<Int, Int> {
        val r = input.indexOfFirst { '^' in it }
        val c = input[r].indexOf('^')
        return r to c
    }

    fun dfs(
        input: List<CharArray>,
        r: Int,
        c: Int,
        direction: Direction,
        visitedCells: MutableSet<Pair<Int, Int>>,
        visitedStates: MutableSet<Triple<Int, Int, Direction>>
    ): Boolean {
        val state = Triple(r, c, direction)
        if (state in visitedStates) return true

        visitedStates.add(state)

        val nR = r + direction.dx
        val nC = c + direction.dy

        if (nR !in input.indices || nC !in input[nR].indices) return false

        if (input[nR][nC] == '#') {
            if (dfs(input, r, c, direction.turnRight(), visitedCells, visitedStates)) return true
        } else {
            visitedCells.add(nR to nC)
            if (dfs(input, nR, nC, direction, visitedCells, visitedStates)) return true
        }

        return false
    }

    fun collectVisitedCells(input: List<CharArray>): Set<Pair<Int, Int>> {
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        val (startRow, startColumn) = findStartPosition(input)

        dfs(input, startRow, startColumn, Direction.UP, visited, mutableSetOf())

        return visited
    }

    fun part2(input: List<String>): Int {
        val modifiedInput = input.map { it.toCharArray() }

        val visited = collectVisitedCells(modifiedInput)

        val (startRow, startColumn) = findStartPosition(modifiedInput)

        var res = 0
        for ((visitedRow, visitedColumn) in visited) {
            val previous = modifiedInput[visitedRow][visitedColumn]
            if (previous != '.') continue

            modifiedInput[visitedRow][visitedColumn] = '#'
            if (dfs(modifiedInput, startRow, startColumn, Direction.UP, mutableSetOf(), mutableSetOf())) res++
            modifiedInput[visitedRow][visitedColumn] = previous
        }

        return res
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
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
