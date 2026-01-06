private fun main() {
    fun buildDisk(input: String): ArrayList<Int> {
        val disk = ArrayList<Int>(input.sumOf { it.digitToInt() })

        var id = 0
        for (i in input.indices) {
            val n = input[i].digitToInt()
            if (i % 2 == 0) {
                repeat(n) { disk.add(id) }
                id++
            } else {
                repeat(n) { disk.add(-1) }
            }
        }

        return disk
    }

    fun part1(input: String): Long {
        val disk = buildDisk(input)

        fun findLeftFree(from: Int): Int {
            for (i in from until disk.size) {
                if (disk[i] == -1) return i
            }
            return -1
        }

        fun findRightFilled(from: Int): Int {
            for (i in from downTo 0) {
                if (disk[i] != -1) return i
            }
            return -1
        }

        var l = findLeftFree(0)
        var r = findRightFilled(disk.lastIndex)

        while (l != -1 && r != -1 && l < r) {
            disk[l] = disk[r]
            disk[r] = -1

            l = findLeftFree(l + 1)
            r = findRightFilled(r - 1)
        }

        var sum = 0L
        for (i in disk.indices) {
            val n = disk[i]
            if (n != -1) sum += n * i
        }
        return sum
    }

    fun part2(input: String): Long {
        val disk = buildDisk(input)

        fun findFile(id: Int): Pair<Int, Int> {
            val end = disk.indexOfLast { it == id }
            var start = end
            while (start > 0 && disk[start - 1] == id) start--
            return start to (end - start + 1)
        }

        fun findGap(minLength: Int, beforeIndex: Int): Int {
            var i = 0

            while (i < beforeIndex) {
                if (disk[i] != -1) {
                    i++
                    continue
                }

                val start = i
                while (i < beforeIndex && disk[i] == -1) i++
                val len = i - start
                if (len >= minLength) return start
            }

            return -1
        }

        val maxId = disk.last { it != -1 }
        for (fileId in maxId downTo 0) {
            val (fileStart, fileLen) = findFile(fileId)
            val gapStart = findGap(fileLen, fileStart)

            if (gapStart != -1) {
                for (k in 0 until fileLen) disk[gapStart + k] = fileId
                for (k in 0 until fileLen) disk[fileStart + k] = -1
            }
        }

        var sum = 0L
        for (i in disk.indices) {
            val n = disk[i]
            if (n != -1) sum += n * i
        }

        return sum
    }

    val input = readInput("Day09")
    part1(input.first())
    part2(input.first())
}
