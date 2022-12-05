import java.util.Stack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.runBlocking

class Day5 : Day(5) {
    override fun solvePuzzle1() = runBlocking {
        val (stacks, instructions) = loadStacksAndInstructions()

        instructions.collect { instruction ->
            for (i in 1..instruction.amount) {
                val crate = stacks[instruction.from - 1].pop()

                stacks[instruction.to - 1].push(crate)
            }
        }

        stacks.map { it.peek() }.joinToString("")
    }

    override fun solvePuzzle2() {

    }


    private val instructionPattern = Regex("""move (?<amount>\d+) from (?<from>\d+) to (?<to>\d+)""")

    private suspend fun loadStacksAndInstructions(): Pair<List<Stack<Char>>, Flow<Instruction>> {
        val lines = readLines()

        val crateLines = lines.takeWhile { it.contains("[") }
        val stacks = parseCrates(crateLines)

        val instructionLines = lines.dropWhile { !it.startsWith("move") }
        val instructions = parseInstructions(instructionLines)

        return Pair(stacks, instructions)
    }

    private suspend fun parseCrates(lines: Flow<String>): List<Stack<Char>> {
        val stacks = mutableMapOf<Int, Stack<Char>>()

        lines.collect { line ->
            val crates = line.chunked(4) { it.trim(' ', '[', ']').firstOrNull() }

            for (i in crates.indices) {
                if (crates[i] == null) continue

                val stack = stacks.getOrPut(i, ::Stack)
                stack.add(0, crates[i])
            }
        }

        return stacks.toSortedMap().values.toList()
    }

    private fun parseInstructions(lines: Flow<String>) = lines.map { line ->
        val match = instructionPattern.matchEntire(line)!!

        Instruction(
            match.groups["amount"]!!.value.toInt(),
            match.groups["from"]!!.value.toInt(),
            match.groups["to"]!!.value.toInt()
        )
    }

    private class Instruction(
        val amount: Int,
        val from: Int,
        val to: Int
    )
}
