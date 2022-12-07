import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

abstract class Day(
    private val number: Int
) {
    abstract fun solvePuzzle1(): Any
    abstract fun solvePuzzle2(): Any

    fun readLines() = flow {
        val source = FileSystem.SYSTEM.source("src/main/resources/day$number.txt".toPath())

        source.use { fileSource ->
            fileSource.buffer().use { bufferedFileSource ->
                while (!bufferedFileSource.exhausted()) {
                    emit(bufferedFileSource.readUtf8LineStrict())
                }
            }
        }
    }

    fun readCharacters() = readLines().transform { line ->
        for (char in line) {
            emit(char)
        }
    }

    override fun toString() = "Day $number"
}
