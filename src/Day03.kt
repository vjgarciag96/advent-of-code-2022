private val A_TO_Z_UPPERCASE = 65..90
private val A_TO_Z_LOWERCASE = 97..122

private fun calculatePriority(char: Char): Int {
    return when (val charCode = char.code) {
        in A_TO_Z_LOWERCASE -> charCode % (A_TO_Z_LOWERCASE.first - 1)
        in A_TO_Z_UPPERCASE -> A_TO_Z_LOWERCASE.count() + (charCode % (A_TO_Z_UPPERCASE.first - 1))
        else -> error("invalid char $char")
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val firstCompartment = rucksack.substring(
                startIndex = 0,
                endIndex = rucksack.length / 2,
            ).toHashSet()

            val invalidItemType = rucksack.substring(
                rucksack.length / 2,
                rucksack.length,
            ).first { itemType -> firstCompartment.contains(itemType) }

            calculatePriority(invalidItemType)
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(size = 3) { (elf1, elf2, elf3) ->
            val elf1Types = elf1.toHashSet()
            val elf2Types = elf2.toHashSet()

            val commonType = elf3.first { itemType ->
                itemType in elf1Types && itemType in elf2Types
            }
            calculatePriority(commonType)
        }.sum()
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
