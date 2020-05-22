package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.PaperReview
import ro.runtimeterror.cms.model.UserReview

data class PaperReviewDTO(
    val paperDTOWithId: PaperDTOWithId,
    val recommendation: String,
    val qualifier: Int,
    val otherReviews: List<UserReviewDTO>
)

fun PaperReview.toDTO(): PaperReviewDTO
{
    return PaperReviewDTO(
        paper.toDTOWithId(),
        recommendation,
        qualifier.value,
        otherReviews.map { review -> review.toDTO() })
}

data class UserReviewDTO(val name: String, val recommendation: String, val qualifier: Int)

fun UserReview.toDTO(): UserReviewDTO
{
    return UserReviewDTO(user.name, recommendation, qualifier.value)
}

data class ChangeReviewDTO(val paperId: Int, val recommendation: String, val qualifier: Int)