
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.runBlocking

class Day6 : Day(6) {
    override fun solvePuzzle1() = runBlocking {
        val chars = readCharacters()

        val startOfPacket = chars.sweep(4).withIndex().first { (index, chunk) ->
            chunk.distinct().size == chunk.size
        }

        startOfPacket.index + 4
    }

    override fun solvePuzzle2() {

    }


    /**
        Sweeps over the flow with a given chunk size.

        For example:
        ```
        flowOf(1, 2, 3, 4).sweep(2) // = flowOf([1, 2], [2, 3], [3, 4])
        ```
    */
    private fun <T> Flow<T>.sweep(size: Int): Flow<Collection<T>> {
        val queue = ArrayDeque<T>(size + 1)

        return transform { item ->
            queue.addLast(item)

            if (queue.size == size) {
                emit(queue)
            } else if (queue.size > size) {
                queue.removeFirst()
                emit(queue)
            }
        }
    }
}
