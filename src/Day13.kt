private sealed interface Element {
    data class Integer(val value: Int) : Element
    data class List(val value: ArrayList<Element>) : Element

    companion object {
        fun newList(): Element {
            return List(ArrayList())
        }
    }
}

private fun ArrayList<Element>.addAtDepth(element: Element, depth: Int) {
    var lastAtDepth: ArrayList<Element> = this
    var currentDepth = 0

    while (currentDepth < depth) {
        lastAtDepth = (lastAtDepth.last() as Element.List).value
        currentDepth++
    }

    lastAtDepth.add(element)
}

private fun Element.compareTo(other: Element): Int {
    if (this is Element.Integer && other is Element.Integer) {
        return value.compareTo(other.value)
    } else if (this is Element.List && other is Element.Integer) {
        return this.compareTo(Element.List(arrayListOf(other)))
    } else if (this is Element.Integer && other is Element.List) {
        return Element.List(arrayListOf(this)).compareTo(other)
    } else if (this is Element.List && other is Element.List) {
        var index = 0
        val limit = minOf(this.value.size, other.value.size)
        while (index < limit) {
            val itemComp = this.value[index].compareTo(other.value[index])
            if (itemComp != 0) return itemComp
            index++
        }
        return this.value.size.compareTo(other.value.size)
    } else {
        error("Unreachable path")
    }
}

private fun parsePacket(rawPacket: String): ArrayList<Element> {
    val elements = ArrayList<Element>()

    var depth = 0
    var index = 1

    while (index < rawPacket.length - 1) {
        when (val char = rawPacket[index]) {
            '[' -> {
                elements.addAtDepth(Element.newList(), depth)
                depth++
            }
            ']' -> depth--
            ',' -> Unit
            else -> {
                var number = char.digitToInt()
                var nextDigit = rawPacket.getOrNull(index + 1)
                while (nextDigit != null && nextDigit.isDigit()) {
                    number = number * 10 + nextDigit.digitToInt()
                    index++
                    nextDigit = rawPacket.getOrNull(index + 1)
                }
                elements.addAtDepth(Element.Integer(number), depth)
            }
        }
        index++
    }

    return elements
}

private fun part1(input: List<String>): Int {
    return input.chunked(3) { (packet1, packet2) ->
        parsePacket(packet1) to parsePacket(packet2)
    }.foldIndexed(initial = 0) { index, acc, (packet1, packet2) ->
        if (Element.List(packet1).compareTo(Element.List(packet2)) <= 0) {
            acc + index + 1
        } else {
            acc
        }
    }
}

private fun part2(input: List<String>): Int {
    val sortedPackets = (input + listOf("[[2]]", "[[6]]"))
        .filter { line -> line.isNotEmpty() }
        .map { rawPacket -> parsePacket(rawPacket) }
        .sortedWith { packet1, packet2 -> Element.List(packet1).compareTo(Element.List(packet2)) }

    return (sortedPackets.indexOf(arrayListOf(Element.List(arrayListOf(Element.Integer(2))))) + 1) *
            (sortedPackets.indexOf(arrayListOf(Element.List(arrayListOf(Element.Integer(6))))) + 1)
}

fun main() {
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
