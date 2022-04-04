package dev.yasan.metro.tehran.model.tehro

import dev.yasan.metro.tehran.data.db.MetroDatabase

/**
 * This class represents the type of a [Line] based on the data on [MetroDatabase].
 * Unlike the rest of the tables, this table is handled manually and is **not** automatically generated from the actual database.
 */
enum class LineType {
    /**
     * Invalid type.
     */
    INVALID,

    /**
     * Metro line.
     */
    METRO_LINE,

    /**
     * Metro branch.
     */
    METRO_BRANCH;

    companion object {

        /**
         * Converts an int to a [LineType] object.
         */
        fun fromInt(value: Int) = values().getOrNull(value) ?: INVALID
    }
}
