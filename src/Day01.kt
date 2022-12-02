import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var maxCalories = -1
        var currentCalories = 0

        input.forEach { line ->
            if (line.isEmpty()) {
                if (currentCalories > maxCalories) {
                    maxCalories = currentCalories
                }
                currentCalories = 0
            } else {
                currentCalories += line.toInt()
            }
        }
        if (currentCalories > maxCalories) {
            maxCalories = currentCalories
        }

        return maxCalories
    }

    fun part2(input: List<String>): Int {
        val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())
        var currentCalories = 0

        input.forEach { line ->
            if (line.isEmpty()) {
                if (currentCalories > 0) {
                    maxHeap.add(currentCalories)
                }
                currentCalories = 0
            } else {
                currentCalories += line.toInt()
            }
        }
        if (currentCalories > 0) {
            maxHeap.add(currentCalories)
        }

        return (1..3).sumOf { maxHeap.poll() ?: 0 }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24_000)
    check(part2(testInput) == 45_000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
