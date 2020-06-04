package ro.runtimeterror.cms.model

import org.joda.time.DateTime

interface Section
{
    val sectionId: Int
    val sessionChair: String
    val user: String
    val paper: String
    val name: String
    val presentationDocumentPath: String
    val startTime: DateTime
    val endTime: DateTime
    val roomName: String
}