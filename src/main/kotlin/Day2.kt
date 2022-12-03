import Day2.Shape.Paper
import Day2.Shape.Rock
import Day2.Shape.Scissors
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class Day2 : Day {
    override fun solvePuzzle1() = runBlocking {
        val instructions = loadInstructions("src/main/resources/day2.txt".toPath())

        val scores = instructions.map { (opponent, player) -> play(player, opponent) }

        scores.reduce(Int::plus)
    }

    override fun solvePuzzle2() {

    }


    private fun loadInstructions(path: Path) = readLines(FileSystem.SYSTEM.source(path)).map { line ->
        Shape.map[line.first()]!! to Shape.map[line.last()]!!
    }

    private fun play(player: Shape, opponent: Shape): Int {
        return when (player) {
            Rock -> 1 + when (opponent) {
                Rock -> 3
                Paper -> 0
                Scissors -> 6
            }

            Paper -> 2 + when (opponent) {
                Rock -> 6
                Paper -> 3
                Scissors -> 0
            }

            Scissors -> 3 + when (opponent) {
                Rock -> 0
                Paper -> 6
                Scissors -> 3
            }
        }
    }

    private enum class Shape {
        Rock,
        Paper,
        Scissors;

        companion object {
            val map = mapOf(
                'A' to Rock,
                'B' to Paper,
                'C' to Scissors,
                'X' to Rock,
                'Y' to Paper,
                'Z' to Scissors
            )
        }
    }
}
