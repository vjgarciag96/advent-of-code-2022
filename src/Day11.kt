private val INT_NUMBER = Regex("[0-9]+")
private val OPERATION = Regex("[*+] ([0-9]+|old)")

private sealed interface Operation {
    data class Add(val arg1: Arg) : Operation
    data class Multiply(val arg1: Arg) : Operation

    sealed class Arg {
        object Input : Arg()
        data class Other(val value: Int) : Arg()
    }
}

private fun Operation.execute(input: Int): Int {
    return when (this) {
        is Operation.Add -> input + arg1.toInt(input)
        is Operation.Multiply -> input * arg1.toInt(input)
    }
}

private fun Operation.Arg.toInt(input: Int): Int {
    return when (this) {
        Operation.Arg.Input -> input
        is Operation.Arg.Other -> value
    }
}

private fun parseArgument(rawArgument: String): Operation.Arg {
    return when (rawArgument) {
        "old" -> Operation.Arg.Input
        else -> Operation.Arg.Other(rawArgument.toInt())
    }
}

private data class Test(
    val divisibleBy: Int,
    val ifTrueThrowTo: Int,
    val ifFalseThrowTo: Int,
)

private data class Monkey<T>(
    val items: ArrayList<T>,
    val operation: Operation,
    val test: Test,
)

fun main() {
    fun part1(input: List<String>): Int {
        val monkeys = input.chunked(size = 7) { monkeyDescription ->
            val startingItems = INT_NUMBER.findAll(monkeyDescription[1]).map { it.value.toInt() }
            val (operator, arg) = OPERATION.findAll(monkeyDescription[2]).map { it.value }.single().split(" ")
            val operation = when (operator) {
                "*" -> Operation.Multiply(parseArgument(arg))
                "+" -> Operation.Add(parseArgument(arg))
                else -> error("invalid operator $operator")
            }
            val divisibleBy = INT_NUMBER.findAll(monkeyDescription[3]).map { it.value }.single().toInt()
            val ifTrueThrowTo = INT_NUMBER.findAll(monkeyDescription[4]).map { it.value }.single().toInt()
            val ifFalseThrowTo = INT_NUMBER.findAll(monkeyDescription[5]).map { it.value }.single().toInt()

            Monkey(
                items = startingItems.toCollection(ArrayList()),
                operation = operation,
                test = Test(
                    divisibleBy = divisibleBy,
                    ifTrueThrowTo = ifTrueThrowTo,
                    ifFalseThrowTo = ifFalseThrowTo,
                )
            )
        }

        val monkeyInspectionCount = Array(monkeys.size) { 0 }

        repeat(20) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = monkey.operation.execute(input = item)
                    val reducedWorryLevel = worryLevel / 3
                    val divisibleBy = reducedWorryLevel % monkey.test.divisibleBy == 0
                    if (divisibleBy) {
                        monkeys[monkey.test.ifTrueThrowTo].items.add(reducedWorryLevel)
                    } else {
                        monkeys[monkey.test.ifFalseThrowTo].items.add(reducedWorryLevel)
                    }
                    monkeyInspectionCount[index]++
                }
                monkey.items.clear()
            }
        }

        val (monkey1, monkey2) = monkeyInspectionCount.sortedDescending().take(2)

        return monkey1 * monkey2
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(size = 7) { monkeyDescription ->
            val startingItems = INT_NUMBER.findAll(monkeyDescription[1]).map { it.value.toInt() }
            val (operator, arg) = OPERATION.findAll(monkeyDescription[2]).map { it.value }.single().split(" ")
            val operation = when (operator) {
                "*" -> Operation.Multiply(parseArgument(arg))
                "+" -> Operation.Add(parseArgument(arg))
                else -> error("invalid operator $operator")
            }
            val divisibleBy = INT_NUMBER.findAll(monkeyDescription[3]).map { it.value }.single().toInt()
            val ifTrueThrowTo = INT_NUMBER.findAll(monkeyDescription[4]).map { it.value }.single().toInt()
            val ifFalseThrowTo = INT_NUMBER.findAll(monkeyDescription[5]).map { it.value }.single().toInt()

            Monkey(
                items = startingItems.map { element -> arrayListOf(element) }.toCollection(ArrayList()),
                operation = operation,
                test = Test(
                    divisibleBy = divisibleBy,
                    ifTrueThrowTo = ifTrueThrowTo,
                    ifFalseThrowTo = ifFalseThrowTo,
                )
            )
        }

        val allDivisors = monkeys.map { it.test.divisibleBy }
        monkeys.forEach { monkey ->
            monkey.items.forEachIndexed { index, item ->
                val itemValue = item.single()
                val dividedByValues = allDivisors.map { divisor -> itemValue % divisor }
                monkey.items[index] = ArrayList(dividedByValues)
            }
        }

        val monkeyInspectionCount = Array(monkeys.size) { 0 }

        repeat(10_000) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { item ->
                    item.forEachIndexed { index, itemValue ->
                        item[index] = monkey.operation.execute(input = itemValue) % allDivisors[index]
                    }
                    val worryLevelToTest = item[allDivisors.indexOf(monkey.test.divisibleBy)]
                    val divisibleBy = worryLevelToTest % monkey.test.divisibleBy == 0
                    if (divisibleBy) {
                        monkeys[monkey.test.ifTrueThrowTo].items.add(item)
                    } else {
                        monkeys[monkey.test.ifFalseThrowTo].items.add(item)
                    }
                    monkeyInspectionCount[index]++
                }
                monkey.items.clear()
            }
            println("$it ${monkeyInspectionCount.joinToString(",")}")
        }

        val (monkey1, monkey2) = monkeyInspectionCount.sortedDescending().take(2)

        return monkey1.toLong() * monkey2
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
