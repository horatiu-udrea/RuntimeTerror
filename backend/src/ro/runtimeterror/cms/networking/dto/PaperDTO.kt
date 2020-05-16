package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Author
import ro.runtimeterror.cms.model.Paper
import ro.runtimeterror.cms.model.toAuthor

data class AbstractDTO(val paperId: Int, val abstract: String)

data class PaperDTO(
    val name: String,
    val field: String,
    val keywords: String,
    val topics: String,
    val authors: List<Author>,
    val abstract: String,
    val documentPath: String,
    val status: Int
)

data class PaperDTOWithId(
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

fun Paper.toDTO(): PaperDTO
{
    return PaperDTO(
        name,
        field,
        keywords,
        topics,
        authors.map { user -> user.toAuthor() },
        abstract,
        documentPath,
        paperStatus.value
    )
}

fun List<Paper>.toDTO(): List<PaperDTOWithId> = map { paper -> paper.toDTOWithId() }

fun Paper.toDTOWithId(): PaperDTOWithId
{
    return PaperDTOWithId(
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