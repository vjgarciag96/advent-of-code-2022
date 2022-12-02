import RockPaperScissorsPlayResult.*
import RockPaperScissorsShape.*

private enum class RockPaperScissorsShape {
    ROCK,
    PAPER,
    SCISSORS,
}

private enum class RockPaperScissorsPlayResult {
    WIN,
    LOSE,
    DRAW
}

private fun play(
    shapeA: RockPaperScissorsShape,
    shapeB: RockPaperScissorsShape,
): RockPaperScissorsPlayResult {
    return when (shapeA) {
        ROCK -> when (shapeB) {
            PAPER -> LOSE
            ROCK -> DRAW
            SCISSORS -> WIN
        }

        PAPER -> when (shapeB) {
            SCISSORS -> LOSE
            PAPER -> DRAW
            ROCK -> WIN
        }

        SCISSORS -> when (shapeB) {
            ROCK -> LOSE
            SCISSORS -> DRAW
            PAPER -> WIN
        }
    }
}

private fun deserializeOpponentShape(rawShape: String): RockPaperScissorsShape {
    return when (rawShape) {
        "A" -> ROCK
        "B" -> PAPER
        "C" -> SCISSORS
        else -> error("Invalid shape $rawShape")
    }
}

private fun deserializeYourShape(rawShape: String): RockPaperScissorsShape {
    return when (rawShape) {
        "X" -> ROCK
        "Y" -> PAPER
        "Z" -> SCISSORS
        else -> error("Invalid shape $rawShape")
    }
}

private fun deserializeResult(result: String): RockPaperScissorsPlayResult {
    return when (result) {
        "X" -> LOSE
        "Y" -> DRAW
        "Z" -> WIN
        else -> error("Invalid result $result")
    }
}

private fun calculateExpectedShape(
    opponentShape: RockPaperScissorsShape,
    expectedResult: RockPaperScissorsPlayResult,
): RockPaperScissorsShape {
    return RockPaperScissorsShape.values().first { yourShape ->
        play(shapeA = yourShape, shapeB = opponentShape) == expectedResult
    }
}

private fun calculateScore(
    result: RockPaperScissorsPlayResult,
    shape: RockPaperScissorsShape,
): Int {
    val resultScore = when (result) {
        WIN -> 6
        LOSE -> 0
        DRAW -> 3
    }
    val shapeScore = when (shape) {
        ROCK -> 1
        PAPER -> 2
        SCISSORS -> 3
    }

    return resultScore + shapeScore
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .sumOf { (opponent, you) ->
                val yourShape = deserializeYourShape(you)
                val opponentShape = deserializeOpponentShape(opponent)
                val result = play(shapeA = yourShape, shapeB = opponentShape)
                calculateScore(result, yourShape)
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .sumOf { (opponent, expectedResult) ->
                val opponentShape = deserializeOpponentShape(opponent)
                val result = deserializeResult(expectedResult)
                val yourShape = calculateExpectedShape(opponentShape, result)
                calculateScore(result, yourShape)
            }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
