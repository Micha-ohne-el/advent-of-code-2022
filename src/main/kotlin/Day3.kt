
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Day3 : Day {
    override fun solvePuzzle1() = runBlocking {
        val rucksacks = loadRucksacks("src/main/resources/day3.txt".toPath())

        val commonItems = rucksacks.map { (compartment1, compartment2) ->
            compartment1.intersect(compartment2).first()
        }

        val priorities = commonItems.map { it.priority }

        priorities.reduce(Int::plus)
    }

    override fun solvePuzzle2() {

    }


    private fun loadRucksacks(path: Path)
        = readLines(FileSystem.SYSTEM.source(path)).map { it.halve(String::toSet) }

    private val Char.priority get() = if (isLowerCase()) {
        code - 'a'.code + 1
    } else {
        code - 'A'.code + 27
    }

    private fun <T> String.halve(transform: (String) -> T): Pair<T, T> {
        val chunks = chunked(length / 2)

        return Pair(transform(chunks[0]), transform(chunks[1]))
    }
}
