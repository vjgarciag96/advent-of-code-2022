import java.util.LinkedList
import java.util.Queue

fun main() {

    data class Node(val x: Int, val y: Int)

    fun buildGraph(input: List<String>): Map<Node, List<Node>> {
        val rowLength = input.first().length

        val adjacencyList = HashMap<Node, ArrayList<Node>>(rowLength * input.size)
        val elevations = input.map { it.replace('S', 'a').replace('E', 'z') }

        elevations.forEachIndexed { y, row ->
            row.forEachIndexed { x, nodeValue ->
                val adjacentNodes = ArrayList<Node>()
                // left node
                if (x > 0 && elevations[y][x - 1] <= nodeValue + 1) {
                    adjacentNodes.add(Node(x - 1, y))
                }
                // right node
                if (x < row.length - 1 && elevations[y][x + 1] <= nodeValue + 1) {
                    adjacentNodes.add(Node(x + 1, y))
                }
                // top node
                if (y > 0 && elevations[y - 1][x] <= nodeValue + 1) {
                    adjacentNodes.add(Node(x, y - 1))
                }
                // bottom node
                if (y < elevations.size - 1 && elevations[y + 1][x] <= nodeValue + 1) {
                    adjacentNodes.add(Node(x, y + 1))
                }

                adjacencyList[Node(x, y)] = adjacentNodes
            }
        }

        return adjacencyList
    }

    fun shortestPathLength(graph: Map<Node, List<Node>>, source: Node, destination: Node): Int? {
        val distances = HashMap<Node, Int>(graph.size)

        val visitedNodes = HashSet<Node>().apply { add(source) }
        val searchQueue: Queue<Node> = LinkedList()
        val sourceNeighbours = graph[source] ?: return null
        searchQueue.addAll(sourceNeighbours)
        sourceNeighbours.forEach { neighbour -> distances[neighbour] = 1 }

        while (!searchQueue.isEmpty()) {
            val currentNode: Node = searchQueue.poll()
            if (currentNode in visitedNodes) continue

            val adjacentNodes = graph[currentNode] ?: continue

            if (destination in adjacentNodes) {
                return distances.getValue(currentNode) + 1
            } else {
                adjacentNodes.forEach { newNode ->
                    distances[newNode] = distances.getValue(currentNode) + 1
                    searchQueue.add(newNode)
                }
                visitedNodes.add(currentNode)
            }
        }

        return null
    }

    fun part1(input: List<String>): Int {
        val startNode = Node(
            x = input.first { it.contains('S') }.indexOfFirst { it == 'S' },
            y = input.indexOfFirst { it.contains('S') },
        )
        val endNode = Node(
            x = input.first { it.contains('E') }.indexOfFirst { it == 'E' },
            y = input.indexOfFirst { it.contains('E') },
        )
        val graph = buildGraph(input)

        return shortestPathLength(graph, startNode, endNode)
            ?: error("No path found between $startNode and $endNode")
    }

    fun part2(input: List<String>): Int {
        val startNodes = ArrayList<Node>()
        input.forEachIndexed { j, row ->
            row.forEachIndexed { i, elevation ->
                if (elevation == 'a' || elevation == 'S') {
                    startNodes.add(Node(i, j))
                }
            }
        }
        val endNode = Node(
            x = input.first { it.contains('E') }.indexOfFirst { it == 'E' },
            y = input.indexOfFirst { it.contains('E') },
        )
        val graph = buildGraph(input)

        return startNodes.mapNotNull { startNode ->
            shortestPathLength(graph, startNode, endNode)
        }.min()
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
