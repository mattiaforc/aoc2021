import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<Int>.increments(): Int {
    return this
        .zipWithNext()
        .fold(0) { acc, pair -> if (pair.second > pair.first) acc + 1 else acc }
}

fun List<String>.readBingo(): Pair<Plays, Array<Board>> {
    return Pair(
        this[0].split(",").map { it.toInt() }.toTypedArray(),
        this.drop(2)
            .filter { it.isNotBlank() }
            .windowed(5, 5)
            .map { Board(it.map { row -> row.split("\\s+".toRegex()).filter { str -> str.isNotBlank() }.map { num -> Box(num.toInt(), false) }.toTypedArray() }.toTypedArray()) }
            .toTypedArray()
    )
}