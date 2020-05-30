package ro.runtimeterror.cms.model

import org.joda.time.DateTime

interface Section
{
    val sectionId: Int
    val sessionChair: Int?
    val user: Int?
    val paperId: Int?
    val name: String
    val presentationDocumentPath: String
    val startTime: DateTime
    val endTime: DateTime
    val roomName: String
}