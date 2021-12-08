typealias Rows = Array<Array<Box>>
typealias Plays = Array<Int>
typealias Box = Pair<Int, Boolean>

fun Rows.columns(): Rows = (0 until this[0].size).map {
    val flat = this.flatten(); flat.slice(it until flat.size step this[0].size).toTypedArray()
}.toTypedArray()

fun Rows.isComplete(): Boolean {
    return this.firstOrNull { row -> row.all { it.second } }?.any() ?: false
}

class Board(private val rows: Rows) {
    fun markNumber(num: Int): Boolean {
        return this.rows.flatten()
            .indexOfFirst { !it.second && it.first == num }.takeIf { it != -1 }
            ?.apply { rows[this / 5][this % 5] = rows[this / 5][this % 5].copy(second = true) }
            ?.let { this.isComplete() } ?: false
    }

    fun isComplete(): Boolean {
        return this.rows.isComplete() || this.rows.columns().isComplete()
    }

    fun sumUnmarked(): Int {
        return this.rows.flatten().filter { !it.second }.sumOf { it.first }
    }
}

fun main() {
    fun part1(input: Pair<Plays, Array<Board>>): Int {
        input.first.forEach { num ->
            input.second.forEach { board -> if(board.markNumber(num)) return num * board.sumUnmarked() }
        }
        throw IllegalStateException("No board is a winning board!")
    }

    fun part2(input: Pair<Plays, Array<Board>>): Int {
        return with(input.first.map { num -> input.second.filterNot { it.isComplete() }.filter { it.markNumber(num) } }
            .let {
                Pair(it.indexOfLast { list -> list.isNotEmpty() }, it.last { list -> list.isNotEmpty() })
            }) {
            input.first[this.first] * this.second[0].sumUnmarked()
        }
    }

    var testInput = readInput("Day04_test").readBingo()
    check(part1(testInput.copy()) == 4512)

    var input = readInput("Day04").readBingo()
    println(part1(input))

    testInput = readInput("Day04_test").readBingo()
    check(part2(testInput) == 1924)
    input = readInput("Day04").readBingo()
    println(part2(input))
}