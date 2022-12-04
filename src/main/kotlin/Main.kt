import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    for (day in days) {
        println("======= ${day::class.simpleName} =======")

        val time1 = measureTime {
            print("Puzzle1: ${day.solvePuzzle1()} ")
        }
        println("(took $time1)")

        val time2 = measureTime {
            print("Puzzle1: ${day.solvePuzzle2()} ")
        }
        println("(took $time2)")
    }

    println("\uD83C\uDF84 Merry Christmas \uD83C\uDF84")
}


private val days = listOf(
    Day1(),
    Day2(),
    Day3(),
    Day4()
)
