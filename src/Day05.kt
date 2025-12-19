private fun main() {

    data class ParsedInput(
        val rules: Set<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    fun parseInput(input: List<String>): ParsedInput {
        val rules = input
            .takeWhile { it.isNotBlank() }
            .map { line ->
                val (before, after) = line.split('|').map(String::toInt)
                before to after
            }.toSet()

        val updates = input
            .takeLastWhile { it.isNotBlank() }
            .map { line ->
                line.split(',').map(String::toInt)
            }

        return ParsedInput(rules, updates)
    }

    fun rulesByPage(rules: Set<Pair<Int, Int>>) =
        rules.groupBy { it.first }.mapValues { (_, values) -> values.map { it.second }.toSet() }

    fun isUpdateValid(
        update: List<Int>,
        blockedByPage: Map<Int, Set<Int>>
    ): Boolean {
        val seen = mutableSetOf<Int>()
        for (page in update) {
            val blocked = blockedByPage[page]
            if (blocked != null && blocked.any { it in seen }) return false
            seen.add(page)
        }
        return true
    }

    fun reorderUpdateByRules(
        update: List<Int>,
        rules: Set<Pair<Int, Int>>
    ) =
        update.sortedWith { a, b ->
            when {
                (a to b) in rules -> -1
                (b to a) in rules -> 1
                else -> 0
            }
        }

    fun middlePage(update: List<Int>) = update[update.size / 2]

    fun part1(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val blockedByPage = rulesByPage(rules)

        return updates
            .filter { isUpdateValid(it, blockedByPage) }
            .sumOf { middlePage(it) }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val blockedByPage = rulesByPage(rules)

        return updates
            .filterNot { isUpdateValid(it, blockedByPage) }
            .map { reorderUpdateByRules(it, rules) }
            .sumOf { middlePage(it) }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
