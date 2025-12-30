private fun main() {

    fun isValidPart1(target: Long, current: Long, candidates: List<Long>): Boolean {
        if (current > target) return false
        if (candidates.isEmpty()) return current == target

        val canAdd = isValidPart1(target, current + candidates[0], candidates.drop(1))
        val canMultiply = isValidPart1(target, current * candidates[0], candidates.drop(1))

        return canAdd || canMultiply
    }

    fun isValidPart2(target: Long, current: Long, candidates: List<Long>): Boolean {
        if (current > target) return false
        if (candidates.isEmpty()) return current == target

        val canAdd = isValidPart2(target, current + candidates[0], candidates.drop(1))
        val canMultiply = isValidPart2(target, current * candidates[0], candidates.drop(1))
        val canConcat = isValidPart2(target, "$current${candidates[0]}".toLong(), candidates.drop(1))

        return canAdd || canMultiply || canConcat
    }

    fun parseLine(line: String): Pair<Long, List<Long>> {
        val target = line.substringBefore(':').toLong()
        val nums = line.substringAfter(": ").split(' ').map { it.toLong() }

        return target to nums
    }


    fun part1(input: List<String>) = input.sumOf { line ->
        val (target, nums) = parseLine(line)
        if (isValidPart1(target, nums.first(), nums.drop(1))) target else 0
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        val (target, nums) = parseLine(line)
        if (isValidPart2(target, nums.first(), nums.drop(1))) target else 0
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
