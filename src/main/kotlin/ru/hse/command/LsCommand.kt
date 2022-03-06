package ru.hse.command

import ru.hse.environment.Environment
import ru.hse.executable.Executable
import ru.hse.executable.ExecutionResult
import ru.hse.utils.writeln
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.path.*

/**
 * ls [arg ] -- выводит список файлов в поток вывода
 * Если в качестве аргумента дана директория
 *   (отсутствие аргументов воспринимается как текущая директория),
 *   то выводит все файлы в этой директории
 * Иначе считает, что в качестве аргумента дано регулярное выражение и
 *  выводит все файлы под него подходящие
 */
class LsCommand(
    private val environment: Environment,
    private val args: List<String>
) : Executable {
    override fun run(input: InputStream, output: OutputStream, error: OutputStream
    ): ExecutionResult = when (args.size) {
        0 -> {
            environment.workDirectory.listDirectoryEntries().forEach {
                if (it.name[0] != '.') {
                    output.writeln(it.name)
                }
            }
            ExecutionResult.success
        }
        1 -> {
            val argPath = environment.workDirectory.resolve(args[0])
            val (glob, runPath) = if (argPath.isDirectory()) {
                "*" to argPath
            } else {
                args[0] to Path(".")
            }
            runPath.listDirectoryEntries(glob).forEach {
                if (it.name[0] != '.') {
                    output.writeln(it.name)
                }
            }
            ExecutionResult.success
        }
        else -> {
            error.writeln("ls: too many arguments")
            ExecutionResult.fail
        }
    }
}
