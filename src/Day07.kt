private data class DirectoryNode(
    val name: String,
    val parent: DirectoryNode?,
)

fun main() {
    fun part1(input: List<String>): Int {
        val visitedFiles = HashSet<String>()
        val sizeOfDirectories = HashMap<DirectoryNode, Int>()

        var currentDirectory: DirectoryNode? = null

        input.forEach { line ->
            if (line.startsWith("\$ cd")) {
                when {
                    line == "\$ cd .." -> currentDirectory = currentDirectory?.parent
                    line == "\$ cd /" -> currentDirectory = DirectoryNode("/", parent = null)
                    line.startsWith("\$ cd") -> currentDirectory =
                        DirectoryNode(line.substring(5), parent = currentDirectory)
                }
            } else if (!line.startsWith("dir") && !line.startsWith("\$ ls")) {
                val (size, filename) = line.split(" ")

                val dirStack = ArrayList<String>()

                var iterDir = currentDirectory
                while (iterDir?.parent != null) {
                    dirStack.add(iterDir.name)
                    iterDir = iterDir.parent
                }

                val fullyQualifiedFilename = dirStack.joinToString("/") + "/${filename.trim()}"

                if (fullyQualifiedFilename !in visitedFiles) {
                    var directory = currentDirectory
                    while (directory != null) {
                        val currentSize = sizeOfDirectories.getOrDefault(directory, 0)
                        sizeOfDirectories[directory] = currentSize + size.toInt()
                        directory = directory.parent
                    }
                    visitedFiles.add(fullyQualifiedFilename)
                }
            }
        }

        return sizeOfDirectories.values.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val visitedFiles = HashSet<String>()
        val sizeOfDirectories = HashMap<DirectoryNode, Int>()

        var currentDirectory: DirectoryNode? = null

        input.forEach { line ->
            if (line.startsWith("\$ cd")) {
                when {
                    line == "\$ cd .." -> currentDirectory = currentDirectory?.parent
                    line == "\$ cd /" -> currentDirectory = DirectoryNode("/", parent = null)
                    line.startsWith("\$ cd") -> currentDirectory =
                        DirectoryNode(line.substring(5), parent = currentDirectory)
                }
            } else if (!line.startsWith("dir") && !line.startsWith("\$ ls")) {
                val (size, filename) = line.split(" ")

                val dirStack = ArrayList<String>()

                var iterDir = currentDirectory
                while (iterDir?.parent != null) {
                    dirStack.add(iterDir!!.name)
                    iterDir = iterDir!!.parent
                }

                val fullyQualifiedFilename = dirStack.joinToString("/") + "/${filename.trim()}"

                if (fullyQualifiedFilename !in visitedFiles) {
                    var directory = currentDirectory
                    while (directory != null) {
                        val currentSize = sizeOfDirectories.getOrDefault(directory, 0)
                        sizeOfDirectories[directory!!] = currentSize + size.toInt()
                        directory = directory!!.parent
                    }
                    visitedFiles.add(fullyQualifiedFilename)
                }
            }
        }

        val availableSpace = 70000000 - sizeOfDirectories[DirectoryNode("/", parent = null)]!!

        val spaceToFree = 30000000 - availableSpace

        return sizeOfDirectories.values.filter { it >= spaceToFree }.min()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
