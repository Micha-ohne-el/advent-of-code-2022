import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.transform
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

    override fun solvePuzzle2() = runBlocking {
        val groups = loadGroups("src/main/resources/day3.txt".toPath())

        val badges = groups.map { (elf1, elf2, elf3) ->
            elf1.intersect(elf2).intersect(elf3).first()
        }

        val priorities = badges.map { it.priority }

        priorities.reduce(Int::plus)
    }


    private fun loadRucksacks(path: Path)
        = readLines(FileSystem.SYSTEM.source(path)).map { it.halve() }

    private fun loadGroups(path: Path): Flow<Triple<Set<Char>, Set<Char>, Set<Char>>> {
        val group = mutableListOf<Set<Char>>()

        return readLines(FileSystem.SYSTEM.source(path)).transform { line ->
            group += line.toSet()

            if (group.size >= 3) {
                emit(Triple(group[0].toSet(), group[1].toSet(), group[2].toSet()))
                group.clear()
            }
        }
    }

    private val Char.priority get() = if (isLowerCase()) {
        code - 'a'.code + 1
    } else {
        code - 'A'.code + 27
    }

    private fun String.halve(): Pair<Set<Char>, Set<Char>> {
        val chunks = chunked(length / 2)

        return Pair(chunks[0].toSet(), chunks[1].toSet())
    }
}
