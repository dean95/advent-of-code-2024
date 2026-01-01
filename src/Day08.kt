private fun main() {

    fun parseAntennaPositions(input: List<String>): Map<Char, MutableList<Pair<Int, Int>>> {
        val antennaPositions = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        for (i in input.indices) {
            for (j in input[i].indices) {
                val current = input[i][j]
                if (current == '.') continue

                antennaPositions.getOrPut(current) { mutableListOf() }.add(i to j)
            }
        }

        return antennaPositions
    }

    fun findAntinodePositions(
        rows: Int,
        cols: Int,
        antennaPositions: List<Pair<Int, Int>>,
        extend: Boolean
    ): Set<Pair<Int, Int>> {
        val res = mutableSetOf<Pair<Int, Int>>()

        fun addSequence(startR: Int, startC: Int, stepR: Int, stepC: Int) {
            var r = startR
            var c = startC

            while (r in 0 until rows && c in 0 until cols) {
                res.add(r to c)
                if (!extend) break
                r += stepR
                c += stepC
            }
        }

        for (i in antennaPositions.indices) {
            val (r1, c1) = antennaPositions[i]
            for (j in i + 1 until antennaPositions.size) {
                val (r2, c2) = antennaPositions[j]
                val dR = r2 - r1
                val dC = c2 - c1

                addSequence(r1 - dR, c1 - dC, -dR, -dC)
                addSequence(r2 + dR, c2 + dC, dR, dC)
            }
        }

        if (extend && antennaPositions.size > 1) {
            res.addAll(antennaPositions)
        }

        return res
    }

    fun getAntinodeCount(input: List<String>, extend: Boolean): Int {
        val antennaPositions = parseAntennaPositions(input)

        val antinodeLocations = mutableSetOf<Pair<Int, Int>>()

        for ((_, positions) in antennaPositions) {
            antinodeLocations.addAll(findAntinodePositions(input.size, input.first().length, positions, extend))
        }

        return antinodeLocations.size
    }

    fun part1(input: List<String>) = getAntinodeCount(input, extend = false)

    fun part2(input: List<String>) = getAntinodeCount(input, extend = true)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
