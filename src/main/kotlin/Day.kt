import kotlinx.coroutines.flow.flow
import okio.Source
import okio.buffer

abstract class Day(
    private val number: Int
) {
    abstract fun solvePuzzle1(): Any
    abstract fun solvePuzzle2(): Any

    fun readLines(source: Source) = flow {
        source.use { fileSource ->
            fileSource.buffer().use { bufferedFileSource ->
                while (!bufferedFileSource.exhausted()) {
                    emit(bufferedFileSource.readUtf8LineStrict())
                }
            }
        }
    }

    override fun toString() = "Day $number"
}
