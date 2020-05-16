package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.controller.Author
import ro.runtimeterror.cms.controller.PaperBid
import ro.runtimeterror.cms.controller.toAuthor
import ro.runtimeterror.cms.model.Paper

data class AbstractDTO(val paperId: Int, val abstract: String)

data class PaperDTO(
    val name: String,
    val field: String,
    val keywords: String,
    val topics: String,
    val authors: List<Author>,
    val abstract: String,
    val documentPath: String,
    val conflicting: Boolean,
    val accepted: Boolean
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
    val conflicting: Boolean,
    val accepted: Boolean
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
        conflicting,
        accepted
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
        conflicting,
        accepted
    )
}