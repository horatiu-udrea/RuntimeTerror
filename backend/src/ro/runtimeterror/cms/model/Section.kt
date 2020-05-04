package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime
import ro.runtimeterror.cms.database.daos.RoomDAO

interface Section
{
    val sectionId: Int
    val roomName: String
    val name: String
    val description: String
    val startTime: DateTime
    val endTime: DateTime
}