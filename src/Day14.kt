import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val rocks = input.flatMap { line ->
            val rockPathPoints = line.split(" -> ").map { rawPoint ->
                val (x, y) = rawPoint.split(",")
                Point(x.toInt(), y.toInt())
            }

            rockPathPoints.windowed(size = 2) { (pointA, pointB) ->
                val horizontalDistance = abs(pointA.x - pointB.x)
                val verticalDistance = abs(pointA.y - pointB.y)

                if (verticalDistance == 0) {
                    val maxX = maxOf(pointA.x, pointB.x)
                    val minX = minOf(pointA.x, pointB.x)
                    (minX..maxX).map { x -> Point(x, pointA.y) }
                } else if (horizontalDistance == 0) {
                    val maxY = maxOf(pointA.y, pointB.y)
                    val minY = minOf(pointA.y, pointB.y)
                    (minY..maxY).map { y -> Point(pointA.x, y) }
                } else {
                    error("Lines must be vertical or horizontal")
                }
            }.flatten()
        }

        val sandObstacles = ArrayList<Point>().apply { addAll(rocks) }
        val downfallLimit = rocks.maxBy { it.y }.y

        var currentSandUnit = Point(500, 0)

        while (currentSandUnit.y <= downfallLimit) {
            val downPoint = Point(currentSandUnit.x, currentSandUnit.y + 1)
            val downLeftPoint = Point(currentSandUnit.x - 1, currentSandUnit.y + 1)
            val downRightPoint = Point(currentSandUnit.x + 1, currentSandUnit.y + 1)

            currentSandUnit = if (downPoint !in sandObstacles) {
                downPoint
            } else if (downLeftPoint !in sandObstacles) {
                downLeftPoint
            } else if (downRightPoint !in sandObstacles) {
                downRightPoint
            } else {
                sandObstacles.add(currentSandUnit)
                Point(500, 0)
            }
        }

        return sandObstacles.minus(rocks).count()
    }

    fun part2(input: List<String>): Int {
        val rocks = input.flatMap { line ->
            val rockPathPoints = line.split(" -> ").map { rawPoint ->
                val (x, y) = rawPoint.split(",")
                Point(x.toInt(), y.toInt())
            }

            rockPathPoints.windowed(size = 2) { (pointA, pointB) ->
                val horizontalDistance = abs(pointA.x - pointB.x)
                val verticalDistance = abs(pointA.y - pointB.y)

                if (verticalDistance == 0) {
                    val maxX = maxOf(pointA.x, pointB.x)
                    val minX = minOf(pointA.x, pointB.x)
                    (minX..maxX).map { x -> Point(x, pointA.y) }
                } else if (horizontalDistance == 0) {
                    val maxY = maxOf(pointA.y, pointB.y)
                    val minY = minOf(pointA.y, pointB.y)
                    (minY..maxY).map { y -> Point(pointA.x, y) }
                } else {
                    error("Lines must be vertical or horizontal")
                }
            }.flatten()
        }

        val sandObstacles = HashSet<Point>().apply { addAll(rocks) }
        val floor = rocks.maxBy { it.y }.y + 2

        var currentSandUnit = Point(500, 0)

        while (true) {
            val downPoint = Point(currentSandUnit.x, currentSandUnit.y + 1)
            val downLeftPoint = Point(currentSandUnit.x - 1, currentSandUnit.y + 1)
            val downRightPoint = Point(currentSandUnit.x + 1, currentSandUnit.y + 1)

            currentSandUnit = if (downPoint !in sandObstacles && downPoint.y < floor) {
                downPoint
            } else if (downLeftPoint !in sandObstacles && downPoint.y < floor) {
                    downLeftPoint
            } else if (downRightPoint !in sandObstacles && downPoint.y < floor) {
                downRightPoint
            } else {
                sandObstacles.add(currentSandUnit)
                if (currentSandUnit == Point(500, 0)) break
                Point(500, 0)
            }
        }

        return sandObstacles.minus(rocks).count()
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
