private fun IntRange.encloses(other: IntRange): Boolean {
    return first <= other.first && last >= other.last
}

private fun IntRange.overlaps(other: IntRange): Boolean {
    return any { other.contains(it) }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { pair -> pair.split(",").flatMap { range -> range.split("-").map { it.toInt() } } }
            .count { (firstRangeStart, firstRangeEnd, secondRangeStart, secondRangeEnd) ->
                val firstRange = firstRangeStart..firstRangeEnd
                val secondRange = secondRangeStart..secondRangeEnd

                firstRange.encloses(secondRange) || secondRange.encloses(firstRange)
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { pair -> pair.split(",").flatMap { range -> range.split("-").map { it.toInt() } } }
            .count { (firstRangeStart, firstRangeEnd, secondRangeStart, secondRangeEnd) ->
                val firstRange = firstRangeStart..firstRangeEnd
                val secondRange = secondRangeStart..secondRangeEnd

                firstRange.overlaps(secondRange)
            }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
