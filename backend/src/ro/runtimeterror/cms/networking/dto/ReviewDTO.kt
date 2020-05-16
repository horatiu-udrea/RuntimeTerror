package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.Review

data class ReviewDTO(val paperDTOWithId: PaperDTOWithId, val recommendation: String, val qualifier: Int)

fun Review.toDTO(): ReviewDTO
{
    return ReviewDTO(paper.toDTOWithId(), recommendation, qualifier.value)
}

data class ChangeReviewDTO(val paperId: Int, val recommendation: String, val qualifier: Int)