package ro.runtimeterror.cms.model

import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime

interface Section
{
    val roomID: EntityID<Int>
    val name: String
    val description: String
    val startTime: DateTime
    val endTime: DateTime
}