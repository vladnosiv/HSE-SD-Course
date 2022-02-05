package ru.hse.parser

/**
 * Позволяет попробовать преобразовать строку в другую сущность
 * @param T тип сущности, в которую будет вестись преобразование
 */
interface Parser<T> {
    /**
     * Преобразовать строку в сущность с типом T
     * Если не получилось, возвращает ошибку
     */
    fun parse(line: String): Result<T>
}
