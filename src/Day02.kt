import kotlin.math.abs

private fun main() {

    fun parseInput(input: List<String>): List<List<Int>> {
        val reports = mutableListOf<List<Int>>()

        input.forEach { line ->
            val level = line.split(' ').map { it.toInt() }
            reports.add(level)
        }

        return reports
    }

    fun isSafe(report: List<Int>): Boolean {
        var direction = 0

        for (i in 1..report.lastIndex) {
            val prev = report[i - 1]
            val current = report[i]

            val diff = current - prev

            if (abs(diff) !in 1..3) return false

            if (direction == 1 && diff <= 0) return false
            if (direction == -1 && diff >= 0) return false

            if (direction != 0) continue
            direction = if (diff > 0) 1 else -1
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val reports = parseInput(input)

        var safeReports = 0
        for (report in reports) {
            if (isSafe(report)) safeReports++
        }

        return safeReports
    }

    fun part2(input: List<String>): Int {
        val reports = parseInput(input)

        var safeReports = 0
        for (report in reports) {
            val mutableReport = report.toMutableList()
            if (isSafe(mutableReport)) {
                safeReports++
                continue
            }

            for (i in report.indices) {
                val element = mutableReport.removeAt(i)
                if (isSafe(mutableReport)) {
                    safeReports++
                    break
                }
                mutableReport.add(i, element)
            }
        }

        return safeReports
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
