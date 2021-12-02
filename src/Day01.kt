fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.increments()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .zipWithNext()
            .zipWithNext { a, b -> a.first + a.second + b.second }
            .increments()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}