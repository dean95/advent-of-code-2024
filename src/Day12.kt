private fun main() {
    val dirs = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun part1(input: List<String>): Int {
        val rows = input.indices
        val cols = input.first().indices

        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        fun dfs(plantId: Char, r: Int, c: Int): IntArray {
            if (r !in rows || c !in cols) return intArrayOf(0, 1)
            if (input[r][c] != plantId) return intArrayOf(0, 1)
            if (r to c in visited) return intArrayOf(0, 0)

            visited.add(r to c)

            var area = 0
            var perimeter = 0
            for ((dr, dc) in dirs) {
                val (neighbourArea, neighbourFence) = dfs(plantId, r + dr, c + dc)
                area += neighbourArea
                perimeter += neighbourFence
            }

            return intArrayOf(area + 1, perimeter)
        }

        var totalPrice = 0
        for (r in input.indices) {
            for (c in input[r].indices) {
                val plantId = input[r][c]
                val (area, perimeter) = dfs(plantId, r, c)
                totalPrice += (area * perimeter)
            }
        }

        return totalPrice
    }

    fun part2(input: List<String>): Int {
        val rows = input.indices
        val cols = input.first().indices

        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        fun countSides(edges: Set<Pair<Int, Int>>): Int {
            val groups = edges.groupBy { it.first }.mapValues { (_, coords) -> coords.map { it.second }.sorted() }

            var sides = 0
            for ((_, nums) in groups) {
                var sequences = 1
                for (i in 1 until nums.size) {
                    if (nums[i] - nums[i - 1] != 1) sequences++
                }
                sides += sequences
            }

            return sides
        }

        fun dfs(
            plantId: Char,
            r: Int,
            c: Int,
            hEdges: MutableSet<Pair<Int, Int>>,
            vEdges: MutableSet<Pair<Int, Int>>
        ): Int {
            if (r !in rows || c !in cols) return 0
            if (input[r][c] != plantId) return 0
            if (r to c in visited) return 0

            visited.add(r to c)

            // Horizontal
            if (r == 0 || input[r - 1][c] != plantId) {
                val groupKey = r * 2 + 0
                hEdges.add(groupKey to c)
            }
            if (r == rows.last || input[r + 1][c] != plantId) {
                val groupKey = (r + 1) * 2 + 1
                hEdges.add(groupKey to c)
            }

            // Vertical
            if (c == 0 || input[r][c - 1] != plantId) {
                val groupKey = c * 2 + 0
                vEdges.add(groupKey to r)
            }
            if (c == cols.last || input[r][c + 1] != plantId) {
                val groupKey = (c + 1) * 2 + 1
                vEdges.add(groupKey to r)
            }

            var area = 1
            for ((dr, dc) in dirs) {
                area += dfs(plantId, r + dr, c + dc, hEdges, vEdges)
            }

            return area
        }

        var totalPrice = 0
        for (r in rows) {
            for (c in cols) {
                val plantId = input[r][c]
                val hEdges = mutableSetOf<Pair<Int, Int>>()
                val vEdges = mutableSetOf<Pair<Int, Int>>()

                val area = dfs(plantId, r, c, hEdges, vEdges)
                val sides = countSides(hEdges) + countSides(vEdges)
                totalPrice += (area * sides)
            }
        }

        return totalPrice
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
