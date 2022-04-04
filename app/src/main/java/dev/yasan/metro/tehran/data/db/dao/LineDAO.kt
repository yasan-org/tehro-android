package dev.yasan.metro.tehran.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import dev.yasan.metro.tehran.data.db.MetroDatabase
import dev.yasan.metro.tehran.model.tehro.Line

/**
 * Data access object for [Line].
 *
 * @see [MetroDatabase]
 */
@Dao
interface LineDAO {

    /**
     * @return A list of all [Line]s in [MetroDatabase].
     */
    @Query("SELECT * FROM lines ORDER BY id DESC")
    suspend fun getAll(): List<Line>

    /**
     * @return A [Line] with matching id if any exist.
     */
    @Query("SELECT * FROM lines WHERE id=:lineId")
    suspend fun getById(lineId: Int): Line?
}
