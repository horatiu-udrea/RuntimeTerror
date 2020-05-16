package ro.runtimeterror.cms.networking.route

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import ro.runtimeterror.cms.controller.PaperReviewController
import ro.runtimeterror.cms.model.UserType
import ro.runtimeterror.cms.networking.authorize
import ro.runtimeterror.cms.networking.dto.ChangeReviewDTO
import ro.runtimeterror.cms.networking.dto.toDTO
import ro.runtimeterror.cms.networking.userSession

fun Route.paperReviewRoute(paperReviewController: PaperReviewController)
{
    route("/paper/review") {
        get {
            authorize(UserType.PC_MEMBER)
            val user = userSession()
            val reviews = paperReviewController.getReviews(user.id)
            call.respond(reviews.map { review -> review.toDTO() })
        }

        put {
            authorize(UserType.PC_MEMBER)
            val user = userSession()
            val changeReview = call.receive<ChangeReviewDTO>()
            paperReviewController.review(user.id, changeReview.paperId, changeReview.recommendation, changeReview.qualifier)
        }
    }
}