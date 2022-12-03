import Day2.Outcome.Draw
import Day2.Outcome.Loss
import Day2.Outcome.Win
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
        val instructions = loadInstructions("src/main/resources/day2.txt".toPath()).map {
            (opponent, suggestion) -> opponent.toShape() to suggestion.toShape()
        }

        val scores = instructions.map { (opponent, suggestion) ->
            suggestion.value + suggestion.playAgainst(opponent).value
        }

        scores.reduce(Int::plus)
    }

    override fun solvePuzzle2() = runBlocking {
        val instructions = loadInstructions("src/main/resources/day2.txt".toPath()).map { (opponent, suggestion) ->
            opponent.toShape() to suggestion.toOutcome()
        }

        val scores = instructions.map { (opponent, outcome) ->
            val suggestion = outcome.achieveAgainst(opponent)

            suggestion.value + suggestion.playAgainst(opponent).value
        }

        scores.reduce(Int::plus)
    }


    private fun loadInstructions(path: Path)
        = readLines(FileSystem.SYSTEM.source(path)).map { line -> line.first() to line.last() }

    private val shapeMap = mapOf(
        'A' to Rock, 'B' to Paper, 'C' to Scissors,
        'X' to Rock, 'Y' to Paper, 'Z' to Scissors
    )
    private fun Char.toShape() = shapeMap[this]!!

    private val outcomeMap = mapOf('X' to Loss, 'Y' to Draw, 'Z' to Win)
    private fun Char.toOutcome() = outcomeMap[this]!!

    private sealed class Shape(
        val value: Int
    ) {
        object Rock : Shape(1) {
            override fun playAgainst(other: Shape) = when (other) {
                Scissors -> Win
                Paper -> Loss
                Rock -> Draw
            }
        }
        object Paper : Shape(2) {
            override fun playAgainst(other: Shape) = when (other) {
                Rock -> Win
                Scissors -> Loss
                Paper -> Draw
            }
        }
        object Scissors : Shape(3) {
            override fun playAgainst(other: Shape) = when (other) {
                Paper -> Win
                Rock -> Loss
                Scissors -> Draw
            }
        }

        abstract fun playAgainst(other: Shape): Outcome
    }

    private sealed class Outcome(
        val value: Int
    ) {
        object Win : Outcome(6) {
            override fun achieveAgainst(other: Shape) = when (other) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }
        }
        object Loss : Outcome(0) {
            override fun achieveAgainst(other: Shape) = when (other) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }
        }
        object Draw : Outcome(3) {
            override fun achieveAgainst(other: Shape) = other
        }

        abstract fun achieveAgainst(other: Shape): Shape
    }
}
