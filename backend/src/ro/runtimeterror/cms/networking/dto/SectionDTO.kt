package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Section

data class SectionDTO(
    val sectionId: Int,
    val sessionChair: String,
    val user: String,
    val name: String,
    val presentationDocumentPath: String,
    val startTime: String,
    val endTime: String,
    val roomName: String,
    val paper: String
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
        paper
    )
}

data class CreateSectionDTO(
    val name: String,
    val startTime: String,
    val endTime: String
)