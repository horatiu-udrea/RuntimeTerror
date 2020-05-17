package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.model.Qualifier
import ro.runtimeterror.cms.model.Review
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator

class PaperReviewController
{
    /**
     * Get all the review made by the user or assigned to him
     */
//    TODO Not sure about this one at all
    fun getReviews(userId: Int): List<Review>
    {
        val reviews: MutableList<Review> = ArrayList()
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
        return reviews
    }

    /**
     * todo you sure it's change a review?
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
