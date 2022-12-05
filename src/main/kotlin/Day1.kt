import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Day1 : Day(1) {
    override fun solvePuzzle1() = runBlocking {
        val elves = loadElves("src/main/resources/day1.txt".toPath())

        val calories = elves.map(List<Int>::sum)

        calories.reduce { max, calorieCount -> maxOf(max, calorieCount) }
    }

    override fun solvePuzzle2() = runBlocking {
        val elves = loadElves("src/main/resources/day1.txt".toPath())

        val calories = elves.map(List<Int>::sum).toList()

        calories.sortedDescending().take(3).sum()
    }


    private suspend fun loadElves(path: Path): Flow<List<Int>> {
        val elf = mutableListOf<Int>()

        return readLines(FileSystem.SYSTEM.source(path)).transform {line ->
            if (line.isBlank()) {
                emit(elf)
                elf.clear()
            } else {
                elf += line.toInt()
            }
        }
    }
}
