package ro.runtimeterror.cms.model

import org.joda.time.DateTime

interface Section
{
    val sectionId: Int
    val sessionChair: User?
    val user: User?
    val paper: Paper?
    val name: String
    val presentationDocumentPath: String
    val startTime: DateTime
    val endTime: DateTime
    val roomName: String
}