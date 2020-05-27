package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Author
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.toAuthor

data class AbstractDTO(val paperId: Int, val abstract: String)

data class CreatePaperDTO(
    val name: String,
    val field: String,
    val keywords: String,
    val topics: String,
    val authors: List<Author>,
    val abstract: String,
    val status: Int
)

data class PaperDTO(
    val paperId: Int,
    val name: String,
    val field: String,
    val keywords: String,
    val topics: String,
    val authors: List<Author>,
    val abstract: String,
    val documentPath: String,
    val status: Int
)

fun List<Paper>.toDTO(): List<PaperDTO> = map { paper -> paper.toDTO() }

fun Paper.toDTO(): PaperDTO
{
    return PaperDTO(
        paperId,
        name,
        field,
        keywords,
        topics,
        authors.map{user -> user.toAuthor()},
        abstract,
        documentPath,
        paperStatus.value
    )
}