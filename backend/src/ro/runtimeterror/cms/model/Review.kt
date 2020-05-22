package ro.runtimeterror.cms.model

data class PaperReview(val paper: Paper, val recommendation: String, val qualifier: Qualifier, val otherReviews: List<UserReview>)

data class UserReview(val user: User, val recommendation: String, val qualifier: Qualifier)