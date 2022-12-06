private val INSTRUCTION_PATTERN = Regex("[0-9]+")

fun main() {
    fun part1(input: List<String>): String {
        val cratesPlan = input.takeWhile { it.isNotBlank() }
        val stackCount = cratesPlan.last().trimEnd().last().digitToInt()
        val maxStackHeight = cratesPlan.size - 1

        val crates = Array(stackCount) { i ->
            val stack = ArrayList<Char>()
            val charIndex = 1 + 4 * i
            repeat(maxStackHeight) { j ->
                val row: String = cratesPlan[j]
                if (charIndex < row.length) {
                    val char = row[charIndex]
                    if (char != ' ') {
                        stack.add(row[charIndex])
                    }
                }
            }
            stack
        }

        val instructions = input.takeLast(input.size - (cratesPlan.size + 1))
        instructions.forEach { instruction ->
            val (move, from, to) = INSTRUCTION_PATTERN
                .findAll(input = instruction)
                .map { it.value }
                .toList()

            repeat(move.toInt()) {
                val fromItem = crates[from.toInt() - 1].removeFirstOrNull()
                fromItem?.let { crates[to.toInt() - 1].add(0, it) }
            }
        }

        return crates.joinToString(separator = "") { column -> column.first().toString() }
    }

    fun part2(input: List<String>): String {
        val cratesPlan = input.takeWhile { it.isNotBlank() }
        val stackCount = cratesPlan.last().trimEnd().last().digitToInt()
        val maxStackHeight = cratesPlan.size - 1

        val crates = Array(stackCount) { i ->
            val stack = ArrayList<Char>()
            val charIndex = 1 + 4 * i
            repeat(maxStackHeight) { j ->
                val row: String = cratesPlan[j]
                if (charIndex < row.length) {
                    val char = row[charIndex]
                    if (char != ' ') {
                        stack.add(row[charIndex])
                    }
                }
            }
            stack
        }

        val instructions = input.takeLast(input.size - (cratesPlan.size + 1))
        instructions.forEach { instruction ->
            val (move, from, to) = INSTRUCTION_PATTERN
                .findAll(input = instruction)
                .map { it.value }
                .toList()

            val originStack = crates[from.toInt() - 1]
            val nItemsToMove = move.toInt().coerceAtMost(originStack.size)
            val itemsToMove = originStack.subList(0, nItemsToMove)
            crates[to.toInt() - 1].addAll(0, itemsToMove)
            repeat(nItemsToMove) { originStack.removeFirst() }
        }

        return crates.joinToString(separator = "") { column -> column.first().toString() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
