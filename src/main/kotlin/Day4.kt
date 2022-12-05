import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class Day4 : Day(4) {
    override fun solvePuzzle1() = runBlocking {
        val assignmentPairs = loadAssignmentPairs()

        assignmentPairs.count { (elf1, elf2) ->
            val overlap = elf1.intersect(elf2)

            overlap.size == elf1.count() || overlap.size == elf2.count()
        }
    }

    override fun solvePuzzle2() = runBlocking {
        val assignmentPairs = loadAssignmentPairs()

        assignmentPairs.count { (elf1, elf2) ->
            val overlap = elf1.intersect(elf2)

            overlap.isNotEmpty()
        }
    }


    private fun loadAssignmentPairs() = readLines().map { line ->
        val assignments = line.split(',').map { assignment ->
            val parts = assignment.split('-')

            parts.first().toInt()..parts.last().toInt()
        }

        Pair(assignments.first(), assignments.last())
    }
}
