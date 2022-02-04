package ru.hse.command

import ru.hse.executable.Executable
import ru.hse.executable.ExecutionResult
import java.io.*

class CatCommand(private val arguments: List<String>) : IOCommand, Executable {
    override val commandName: String = "cat"

    override fun run(input: InputStream, output: OutputStream, error: OutputStream): ExecutionResult {
        return when {
            arguments.isEmpty() -> processEmptyArgumentList(input, output, error)
            else -> processNotEmptyArgumentList(output, error)
        }
    }

    private fun processEmptyArgumentList(
        input: InputStream,
        output: OutputStream,
        error: OutputStream
    ): ExecutionResult {
        return if (safeIO(error) { input.transferTo(output) }) {
            ExecutionResult.success()
        } else {
            ExecutionResult.fail()
        }
    }

    private fun processNotEmptyArgumentList(
        output: OutputStream,
        error: OutputStream
    ): ExecutionResult {
        var isSuccessful = true
        for (fileName in arguments) {
            if (!readFile(fileName, error) { it.transferTo(output) }) {
                isSuccessful = false
            }
        }
        return if (isSuccessful) ExecutionResult.success() else ExecutionResult.fail()
    }
}
