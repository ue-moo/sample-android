package com.github.uemoo.android.data.db.dao

/**
 * DataAccessObject の IF
 */
internal interface Dao<T> {
    fun insert(value: T)
    fun delete()
    fun query(): T?
}
