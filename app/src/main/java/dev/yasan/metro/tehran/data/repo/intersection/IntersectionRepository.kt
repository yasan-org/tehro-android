package dev.yasan.metro.tehran.data.repo.intersection

import dev.yasan.metro.tehran.data.db.dao.IntersectionDAO
import dev.yasan.metro.tehran.data.db.entity.Intersection

/**
 * Handles all data related to [Intersection].
 */
interface IntersectionRepository {

    /**
     * @return A [Intersection] with matching id if any exist.
     *
     * @see IntersectionDAO
     */
    suspend fun getIntersection(intersectionId: Int): Intersection?

    /**
     * @return A [Intersection] with matching station if any exist.
     *
     * @see IntersectionDAO
     */
    suspend fun getIntersectionByStationId(stationId: Int): Intersection?

}
