package ro.runtimeterror.cms.model

//TODO made the paper, recommendation, qualifier properties nullable because when a
//  non PCMember author is accessing the paper it doesn't make sense for him
//  to see the review he made to his paper

data class PaperReview(val paper: Paper?, val recommendation: String?, val qualifier: Qualifier?, val otherReviews: List<UserReview>)

data class UserReview(val user: User, val recommendation: String, val qualifier: Qualifier)