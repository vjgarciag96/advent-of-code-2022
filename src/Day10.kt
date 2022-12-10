private val cyclesToSelect = setOf(20, 60, 100, 140, 180, 220)

private fun part1(input: List<String>): Int {
    return input.fold(initial = listOf(1)) { state, instruction ->
        when (instruction) {
            "noop" -> state + listOf(state.last())
            else -> {
                val (_, valueToAdd) = instruction.split(" ")
                state + listOf(state.last(), state.last() + valueToAdd.toInt())
            }
        }
    }.mapIndexed { cycle, value ->
        if (cycle + 1 in cyclesToSelect) {
            (cycle + 1) * value
        } else {
            0
        }
    }.sum()
}

private fun part2(input: List<String>): String {
    return input.fold(initial = listOf(1)) { state, instruction ->
        when (instruction) {
            "noop" -> state + listOf(state.last())
            else -> {
                val (_, valueToAdd) = instruction.split(" ")
                state + listOf(state.last(), state.last() + valueToAdd.toInt())
            }
        }
    }.mapIndexed { index, x ->
        val crtPixels = setOf(x - 1, x, x + 1)
        if (index % 40 in crtPixels) '#' else '.'
    }.joinToString(separator = "")
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    println(part2(testInput))

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
