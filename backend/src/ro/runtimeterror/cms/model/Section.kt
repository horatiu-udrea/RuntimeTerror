package ro.runtimeterror.cms.model

import org.joda.time.DateTime

interface Section
{
    val sectionId: Int
    val roomName: String
    val name: String
    val description: String
    val startTime: DateTime
    val endTime: DateTime
}