fun main() {
    fun List<String>.rowsToColumns(): List<String> {
        val step = this[0].length
        val flat = this.joinToString("")

        return (0 until step).map { flat.slice(it until flat.length step step) }
    }

    fun List<String>.ones(): String {
        return this.rowsToColumns().map { list -> list.count { num -> num == '1' } >= list.count { num -> num == '0' } }
            .joinToString("") { if (it) "1" else "0" }
    }

    fun String.reverseBinary(): String {
        return this.map { c -> if (c == '1') '0' else '1' }.joinToString("")
    }

    fun part1(input: List<String>): Int {
        return input.ones()
            .let { it.toInt(2) * it.reverseBinary().toInt(2) }
    }

    fun part2(input: List<String>): Int {
        val oxygen = input[0].indices
            .fold(input) { acc, idx ->
                if (acc.size == 1) acc else {
                    val ones = acc.ones(); acc.filter { it[idx] == ones[idx] }
                }
            }
            .single()
            .toInt(2)

        val co2 = input[0].indices
            .fold(input) { acc, idx ->
                if (acc.size == 1) acc else {
                    val zeros = acc.ones().reverseBinary(); acc.filter { it[idx] == zeros[idx] }
                }
            }
            .single()
            .toInt(2)

        return oxygen * co2
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))

    check(part2(testInput) == 230)
    println(part2(input))
}