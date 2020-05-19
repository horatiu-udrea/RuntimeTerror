package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.model.PaperReview
import ro.runtimeterror.cms.model.Qualifier
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator

class PaperReviewController
{
    /**
     * Get all the reviews made by the user or assigned to him and also the other reviews for the same paper
     * If the author is a PC member, he is not allowed to see the other reviews
     */
    fun getReviews(userId: Int): List<PaperReview>
    {
        /*val reviews: MutableList<Review> = ArrayList()
        transaction (DatabaseSettings.connection){
            ReviewTable
                    .select { ReviewTable.userID eq userId }
                    .forEach{reviews += Review(PaperDAO
                                .findById(it[ReviewTable.paperID])!!,
                                it[ReviewTable.recommandation],
                                Qualifier.from(it[ReviewTable.qualifier])
                        )
                    }
        }
        return reviews*/
        TODO()
    }

    /**
     * Change a review
     */
    fun review(userID: Int, paperID: Int, recommendation: String, qualifier: Qualifier)
    {
        UserValidator.exists(userID)
        PaperValidator.exists(paperID)
        transaction {
            ReviewTable
                    .insert {
                        it[ReviewTable.userID] = userID
                        it[ReviewTable.paperID] = paperID
                        it[ReviewTable.qualifier] = qualifier.value
                        it[recommandation] = recommendation
                    }
        }
    }

}
