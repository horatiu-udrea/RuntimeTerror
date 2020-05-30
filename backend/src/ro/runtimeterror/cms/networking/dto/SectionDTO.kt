package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Section

data class SectionDTO(
    val sectionId: Int,
    val sessionChairId: Int?,
    val userId: Int?,
    val name: String,
    val presentationDocumentPath: String,
    val startTime: String,
    val endTime: String,
    val roomName: String,
    val paperId: Int?
)

fun Section.toDTO(): SectionDTO
{
    return SectionDTO(
        sectionId,
        sessionChair,
        user,
        name,
        presentationDocumentPath,
        startTime.toString(dateTimeFormatter),
        endTime.toString(dateTimeFormatter),
        roomName,
        paperId
    )
}

data class CreateSectionDTO(
    val name: String,
    val startTime: String,
    val endTime: String
)