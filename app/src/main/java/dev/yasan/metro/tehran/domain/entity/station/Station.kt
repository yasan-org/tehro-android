package dev.yasan.metro.tehran.domain.entity.station

import androidx.compose.material.icons.sharp.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.*
import dev.yasan.metro.tehran.data.source.local.database.MetroDatabase
import dev.yasan.metro.tehran.domain.entity.accessibility.AccessibilityLevelBlindness
import dev.yasan.metro.tehran.domain.entity.accessibility.AccessibilityLevelWheelchair
import dev.yasan.metro.tehran.domain.entity.accessibility.WcAvailabilityLevel
import dev.yasan.metro.tehran.domain.entity.intersection.Intersection
import dev.yasan.metro.tehran.domain.entity.line.Line
import dev.yasan.metro.tehran.presentation.theme.TehroIcons
import dev.yasan.metro.tehran.presentation.util.helper.LocaleHelper
import dev.yasan.metro.tehran.presentation.util.helper.PrideHelper
import kotlinx.parcelize.IgnoredOnParcel

/**
 * This class represents a station on [MetroDatabase].
 *
 * @see Line
 */
@Entity(
    tableName = "stations",
    foreignKeys = [
        ForeignKey(
            entity = Line::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("line_id"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = AccessibilityLevelWheelchair::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("accessibility_wheelchair_level"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = AccessibilityLevelBlindness::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("accessibility_blindness_level"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = WcAvailabilityLevel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("wc"),
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Station(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name_fa") val nameFa: String,
    @ColumnInfo(name = "name_en") val nameEn: String,
    @ColumnInfo(name = "line_id", index = true) val lineId: Int,
    @ColumnInfo(name = "position_in_line") val positionInLine: Int,
    @ColumnInfo(name = "location_lat") val locationLatitude: Double?,
    @ColumnInfo(name = "location_long") val locationLongitude: Double?,
    @ColumnInfo(name = "map_x") val mapX: Int?,
    @ColumnInfo(name = "map_y") val mapY: Int?,
    @ColumnInfo(name = "has_emergency_medical_services") val hasEmergencyMedicalServices: Boolean,
    @ColumnInfo(
        name = "accessibility_wheelchair_level",
        index = true,
        defaultValue = "1"
    ) val accessibilityWheelchairInt: Int,
    @ColumnInfo(
        name = "accessibility_blindness_level",
        index = true,
        defaultValue = "1"
    ) val accessibilityBlindnessInt: Int,
    @ColumnInfo(
        name = "wc",
        index = true,
        defaultValue = "1"
    ) val wcInt: Int,
) {

    /**
     * The proper name based on the device's default language.
     * @see nameFa
     * @see nameEn
     */
    val name: String get() = if (LocaleHelper.isFarsi) nameFa else nameEn

    @Ignore
    @IgnoredOnParcel
    var intersection: Intersection? = null

    @Ignore
    @IgnoredOnParcel
    var line: Line? = null

    @Ignore
    @IgnoredOnParcel
    var accessibilityLevelWheelchair: AccessibilityLevelWheelchair? = null

    @Ignore
    @IgnoredOnParcel
    var accessibilityLevelBlindness: AccessibilityLevelBlindness? = null

    @Ignore
    @IgnoredOnParcel
    var wc: WcAvailabilityLevel? = null

    /**
     * If the station has valid location data.
     */
    val hasLocation: Boolean get() = locationLatitude != null && locationLongitude != null

    /**
     * @return If the station is exactly the same station but in another line.
     */
    fun isVirtuallyTheSame(other: Station) = nameEn == other.nameEn

    /**
     * @return A custom icon based on the name of the station.
     */
    fun getIcon(): ImageVector? {
        return when {
            nameFa.contains("??????????????") -> TehroIcons.Sports
            nameFa.contains("????????") -> TehroIcons.HealthAndSafety
            nameFa.contains("??????????????") -> TehroIcons.School
            nameFa.contains("??????????") -> TehroIcons.WbSunny
            nameFa.contains("????????") || nameFa.contains("??????????") || nameFa.contains("???????? ????????") -> TehroIcons.LocalHospital
            nameFa.contains("????????") -> TehroIcons.MilitaryTech
            nameFa.contains("????????") -> TehroIcons.Audiotrack
            nameFa.contains("??????") || nameFa.contains("?????? ????") -> TehroIcons.Groups
            nameFa.contains("??????????") || nameFa.contains("?????? ??????") -> TehroIcons.Nature
            nameFa.contains("??????????") -> TehroIcons.Park
            nameFa.contains("?????? ????????") -> TehroIcons.Science
            nameFa.contains("????????????") -> TehroIcons.DoorSliding
            nameFa.contains("??????????") -> TehroIcons.CarRepair
            nameFa.contains("???????????????") -> TehroIcons.Whatshot
            nameFa.contains("???????????????") -> TehroIcons.Train
            nameFa.contains("??????????") -> TehroIcons.LocalFlorist
            nameFa.contains("??????????") ||
                    nameFa.contains("??????????") ||
                    nameFa.contains("????????????") ||
                    nameFa.contains("????????") ||
                    nameFa.contains("????????") -> TehroIcons.HistoryEdu
            nameFa.contains("??????????") -> TehroIcons.TripOrigin
            nameFa.contains("??????????????") -> TehroIcons.LocalAirport
            nameFa.contains("??????????") || nameFa.contains("???????? ??????") -> TehroIcons.Event
            nameFa.contains("??????????") -> if (PrideHelper.isPrideMonth) TehroIcons.Looks else TehroIcons.TheaterComedy
            else -> null
        }
    }

}
