fun main() {
    for (day in days) {
        println("======= ${day::class.simpleName} =======")
        println("Puzzle1: ${day.solvePuzzle1()}")
        println("Puzzle2: ${day.solvePuzzle2()}")
    }

    println("Merry Christmas \uD83C\uDF84")
}


private val days = listOf<Day>(
    Day1()
)
