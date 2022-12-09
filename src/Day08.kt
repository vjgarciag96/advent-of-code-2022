fun main() {
    fun part1(input: List<String>): Int {
        return input.flatMapIndexed { j, treeLine ->
            treeLine.mapIndexed { i, tree ->
                when {
                    i == 0 || j == 0 || i == treeLine.length - 1 || j == input.size - 1 -> 1
                    treeLine.substring(0, i).max() < tree -> 1
                    treeLine.substring(i + 1).max() < tree -> 1
                    (0 until j).maxOf { input[it][i] } < tree -> 1
                    (j + 1 until treeLine.length).maxOf { input[it][i] } < tree -> 1
                    else -> 0
                }
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.flatMapIndexed { j, treeLine ->
            treeLine.mapIndexed { i, tree ->
                var leftScore = treeLine.substring(0, i).takeLastWhile { it < tree }.count()
                if (leftScore < i) leftScore++
                var rightScore = treeLine.substring(i + 1).takeWhile { it < tree }.count()
                if (rightScore < treeLine.length - i - 1) rightScore++
                var topScore = (0 until j).reversed().takeWhile { input[it][i] < tree }.count()
                if (topScore < (0 until j).count()) topScore++
                var bottomScore = (j + 1 until treeLine.length).takeWhile { input[it][i] < tree }.count()
                if (bottomScore < (j + 1 until treeLine.length).count()) bottomScore++
                leftScore * rightScore * topScore * bottomScore
            }
        }.max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
