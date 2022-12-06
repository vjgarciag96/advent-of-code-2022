fun main() {
    fun part1(input: List<String>): Int {
        val windows = input.single().windowed(size = 4)

        val startOfPacketWindowIndex = windows.indexOfFirst { window ->
            val visitedChars = HashSet<Char>()
            window.all { visitedChars.add(it) }
        }

        return startOfPacketWindowIndex + 4
    }

    fun part2(input: List<String>): Int {
        val windows = input.single().windowed(size = 14)

        val messageWindowIndex = windows.indexOfFirst { window ->
            val visitedChars = HashSet<Char>()
            window.all { visitedChars.add(it) }
        }

        return messageWindowIndex + 14
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
