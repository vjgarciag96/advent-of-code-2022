import kotlin.math.absoluteValue

private data class Point(
    val x: Int,
    val y: Int,
)

private fun part1(input: List<String>): Int {
    val pointsVisitedByTail = HashSet<Point>()
    var headPosition = Point(x = 0, y = 0)
    var tailPosition = Point(x = 0, y = 0)

    pointsVisitedByTail.add(tailPosition)

    input.forEach { movement ->
        val (direction, distance) = movement.split(" ")
        repeat(distance.toInt()) {
            headPosition = when (direction) {
                "L" -> headPosition.copy(x = headPosition.x - 1)
                "R" -> headPosition.copy(x = headPosition.x + 1)
                "U" -> headPosition.copy(y = headPosition.y + 1)
                "D" -> headPosition.copy(y = headPosition.y - 1)
                else -> error("invalid direction $direction")
            }

            val xDistance = headPosition.x - tailPosition.x
            val yDistance = headPosition.y - tailPosition.y

            tailPosition = when {
                xDistance.absoluteValue < 2 && yDistance.absoluteValue < 2 -> tailPosition
                xDistance == 2 && yDistance == 0 -> tailPosition.copy(x = tailPosition.x + 1)
                xDistance == -2 && yDistance == 0 -> tailPosition.copy(x = tailPosition.x - 1)
                yDistance == 2 && xDistance == 0 -> tailPosition.copy(y = tailPosition.y + 1)
                yDistance == -2 && xDistance == 0 -> tailPosition.copy(y = tailPosition.y - 1)
                xDistance == 2 && yDistance == 1 -> tailPosition.copy(
                    x = tailPosition.x + 1,
                    y = tailPosition.y + 1
                )

                xDistance == -2 && yDistance == 1 -> tailPosition.copy(
                    x = tailPosition.x - 1,
                    y = tailPosition.y + 1
                )

                xDistance == 2 && yDistance == -1 -> tailPosition.copy(
                    x = tailPosition.x + 1,
                    y = tailPosition.y - 1
                )

                xDistance == -2 && yDistance == -1 -> tailPosition.copy(
                    x = tailPosition.x - 1,
                    y = tailPosition.y - 1
                )

                yDistance == 2 && xDistance == 1 -> tailPosition.copy(
                    x = tailPosition.x + 1,
                    y = tailPosition.y + 1
                )

                yDistance == 2 && xDistance == -1 -> tailPosition.copy(
                    x = tailPosition.x - 1,
                    y = tailPosition.y + 1
                )

                yDistance == -2 && xDistance == 1 -> tailPosition.copy(
                    x = tailPosition.x + 1,
                    y = tailPosition.y - 1
                )

                yDistance == -2 && xDistance == -1 -> tailPosition.copy(
                    x = tailPosition.x - 1,
                    y = tailPosition.y - 1
                )

                else -> error("Invalid distances -> x = $xDistance, y = $yDistance")
            }
            pointsVisitedByTail.add(tailPosition)
        }
    }

    return pointsVisitedByTail.count()
}

private fun part2(input: List<String>): Int {
    val pointsVisitedByTail = HashSet<Point>()

    val positions = Array(10) { Point(x = 0, y = 0) }.toMutableList()

    pointsVisitedByTail.add(positions.last())

    input.forEach { movement ->
        val (direction, distance) = movement.split(" ")

        repeat(distance.toInt()) {
            val headPosition = positions.first()
            positions[0] = when (direction) {
                "L" -> headPosition.copy(x = headPosition.x - 1)
                "R" -> headPosition.copy(x = headPosition.x + 1)
                "U" -> headPosition.copy(y = headPosition.y + 1)
                "D" -> headPosition.copy(y = headPosition.y - 1)
                else -> error("invalid direction $direction")
            }

            var pointerA = 0
            var pointerB = 1
            while (pointerB < positions.size) {
                val positionA = positions[pointerA]
                val positionB = positions[pointerB]
                val xDistance = positionA.x - positionB.x
                val yDistance = positionA.y - positionB.y

                val newPositionB = when {
                    xDistance.absoluteValue < 2 && yDistance.absoluteValue < 2 -> positionB
                    xDistance == 2 && yDistance == 0 -> positionB.copy(x = positionB.x + 1)
                    xDistance == -2 && yDistance == 0 -> positionB.copy(x = positionB.x - 1)
                    yDistance == 2 && xDistance == 0 -> positionB.copy(y = positionB.y + 1)
                    yDistance == -2 && xDistance == 0 -> positionB.copy(y = positionB.y - 1)
                    xDistance == 2 && yDistance == 1 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y + 1
                    )

                    xDistance == -2 && yDistance == 1 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y + 1
                    )

                    xDistance == 2 && yDistance == -1 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y - 1
                    )

                    xDistance == -2 && yDistance == -1 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y - 1
                    )

                    yDistance == 2 && xDistance == 1 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y + 1
                    )

                    yDistance == 2 && xDistance == -1 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y + 1
                    )

                    yDistance == -2 && xDistance == 1 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y - 1
                    )
                    yDistance == -2 && xDistance == -1 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y - 1
                    )
                    xDistance == 2 && yDistance == 2 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y + 1,
                    )
                    xDistance == -2 && yDistance == 2 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y + 1,
                    )
                    xDistance == 2 && yDistance == -2 -> positionB.copy(
                        x = positionB.x + 1,
                        y = positionB.y - 1,
                    )
                    xDistance == -2 && yDistance == -2 -> positionB.copy(
                        x = positionB.x - 1,
                        y = positionB.y - 1,
                    )
                    else -> error("Invalid distances -> x = $xDistance, y = $yDistance")
                }

                positions[pointerB] = newPositionB

                pointerA++
                pointerB++
            }
            pointsVisitedByTail.add(positions.last())
        }
    }

    return pointsVisitedByTail.count()
}

fun main() {
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
