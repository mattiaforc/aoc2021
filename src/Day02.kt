fun main() {
    // pair is (horizontal, vertical)
    fun part1(input: List<String>): Int {
        return input.fold(Pair(0, 0)) { acc, s ->
            val instruction = s.split(" ")
                when (instruction[0]) {
                    "forward" -> acc.copy(acc.first + instruction[1].toInt(), acc.second)
                    "up" -> acc.copy(acc.first, acc.second - instruction[1].toInt())
                    "down" -> acc.copy(acc.first, acc.second + instruction[1].toInt())
                    else -> throw IllegalStateException("Unrecognized command")
                }
        }.let { it.first * it.second }
    }

    // triple is (horizontal, vertical, aim)
    fun part2(input: List<String>): Int {
        return input.fold(Triple(0, 0, 0)) { acc, s ->
            val instruction = s.split(" ")
            when (instruction[0]) {
                "forward" -> acc.copy(
                    acc.first + instruction[1].toInt(),
                    acc.second + (acc.third * instruction[1].toInt()),
                    acc.third
                )
                "up" -> acc.copy(acc.first, acc.second, acc.third - instruction[1].toInt())
                "down" -> acc.copy(acc.first, acc.second, acc.third + instruction[1].toInt())
                else -> throw IllegalStateException("Unrecognized command")
            }
        }.let { it.first * it.second }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}