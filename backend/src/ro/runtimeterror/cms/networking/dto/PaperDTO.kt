package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Paper


data class PaperDTO(
    val userId: Int,
    val name: String,
    val field: String,
    val keywords: String,
    val topics: String,
    val authors: String,
    val documentPath: String,
    val conflicting: Boolean,
    val accepted: Boolean
)

fun Paper.toDTO(): PaperDTO
{
    return PaperDTO(
        user.userId,
        name,
        field,
        keywords,
        topics,
        authors,
        documentPath,
        conflicting,
        accepted
    )
}