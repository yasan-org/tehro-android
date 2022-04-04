package dev.yasan.metro.tehran.model.tehro.accessibility

import androidx.room.*
import dev.yasan.metro.tehran.data.db.MetroDatabase
import dev.yasan.metro.tehran.model.tehro.Station

/**
 * This class defines different WC availability levels on [MetroDatabase].
 *
 * @see Station
 */
@Entity(tableName = "stations_wc_availability_levels")
class WcAvailabilityLevel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "description_en") override val descriptionEn: String,
    @ColumnInfo(name = "description_fa") override val descriptionFa: String,
) : AccessibilityLevel(
    id = id,
    descriptionEn = descriptionEn,
    descriptionFa = descriptionFa,
    maxValue = 3
)


