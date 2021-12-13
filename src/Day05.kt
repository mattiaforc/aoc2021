import kotlin.math.*

class HydrotermalVent(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    var matrix: Array<Array<Int>> = Array(max(x1, x2) + 1) { Array(max(y1, y2) + 1) { 0 } }
    var angle = 0.0

    init {
        angle = abs((atan2((y2 - y1).toDouble(), (x2 - x1).toDouble()) * 180 / PI)) % 90
        if (angle == 45.0) {
            (if(x1 > x2) (x1 downTo x2) else (x1..x2))
                .zip(if(y1 > y2) (y1 downTo y2) else (y1..y2))
                .forEach { (x, y) -> matrix[x][y] = 1 }
        } else {
            (min(x1, x2)..max(x1, x2)).forEach { x -> (min(y1, y2)..max(y1, y2)).forEach { y -> matrix[x][y] = 1 } }
        }
    }
}

fun List<String>.toHydroVents(): List<HydrotermalVent> {
    return this.map {
        with(it.replace(" -> ", ",").split(",").map { n -> n.toInt() }) {
            HydrotermalVent(this[0], this[1], this[2], this[3])
        }
    }
}

fun List<HydrotermalVent>.countOverlapping(): Int {
    return this.fold(
        Array(this.maxOf { it.matrix.size })
        { Array(this.maxOf { it.matrix.maxOf { row -> row.size } }) { 0 } }
    ) { acc, vent ->
        vent.matrix.forEachIndexed { ix, x ->
            x.forEachIndexed { iy, y ->
                acc[ix][iy] += y
            }
        }; acc
    }.flatten().count { it >= 2 }
}

fun main() {
    fun part1(input: List<HydrotermalVent>) = input.filter { it.x1 == it.x2 || it.y1 == it.y2 }.countOverlapping()

    fun part2(input: List<HydrotermalVent>) =
        input.filter { it.x1 == it.x2 || it.y1 == it.y2 || it.angle == 45.0 }.countOverlapping()

    val testInput = readInput("Day05_test").toHydroVents()
    check(part1(testInput) == 5)

    val input = readInput("Day05").toHydroVents()
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 12)
    println(part2(input))
}