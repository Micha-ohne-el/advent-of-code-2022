import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Day4 : Day {
    override fun solvePuzzle1() = runBlocking {
        val assignmentPairs = loadAssignmentPairs("src/main/resources/day4.txt".toPath())

        assignmentPairs.count { (elf1, elf2) ->
            val overlap = elf1.intersect(elf2)

            overlap.size == elf1.count() || overlap.size == elf2.count()
        }
    }

    override fun solvePuzzle2() {

    }


    private fun loadAssignmentPairs(path: Path) = readLines(FileSystem.SYSTEM.source(path)).map { line ->
        val assignments = line.split(',').map { assignment ->
            val parts = assignment.split('-')

            parts.first().toInt()..parts.last().toInt()
        }

        Pair(assignments.first(), assignments.last())
    }
}
