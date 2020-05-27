package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.model.PaperReview
import ro.runtimeterror.cms.model.Qualifier
import ro.runtimeterror.cms.model.validators.PaperValidator
import ro.runtimeterror.cms.model.validators.UserValidator
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.daos.UserDAO
import ro.runtimeterror.cms.database.daos.withAuthors
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.UserReview
import ro.runtimeterror.cms.model.validators.UniquenessValidator

class PaperReviewController
{

    /**
     * Get all the reviews made by the user or assigned to him and also the other reviews for the same paper
     */
    fun getReviews(userId: Int): List<PaperReview> = getPCMemberReviews(userId)


    private fun getPCMemberReviews(userId: Int): List<PaperReview> = transaction(connection){
        val authoredPapers = PaperDAO
            .all()
            .map { withAuthors(it) }
            .filter{ userId in it.authors.map {user-> user.userId } }
            .map { it.paperId }
            .toList()

        return@transaction ReviewTable
            .select{ReviewTable.userID eq userId}
            .filter { it[ReviewTable.paperID] !in authoredPapers }
            .map {
                PaperReview(
                        PaperDAO.wrapRow(PaperTable.select { PaperTable.id eq ReviewTable.paperID }.first()),
                        it[ReviewTable.recommandation],
                        Qualifier.from(it[ReviewTable.qualifier]),
                        getOtherReviews(userId, it[ReviewTable.paperID])
                    )
            }
    }

    private fun getOtherReviews(userId: Int, paperID: Int): List<UserReview> = transaction(connection) {
        return@transaction ReviewTable.select {
            (ReviewTable.paperID eq paperID) and (ReviewTable.userID eq userId)
        }.map {
            UserReview(
                    UserDAO.findById(it[ReviewTable.userID])!!,
                    it[ReviewTable.recommandation],
                    Qualifier.from(it[ReviewTable.qualifier])
            )
        }
    }

    /**
     * Change a review
     */
    fun review(userID: Int, paperID: Int, recommendation: String, qualifier: Int)= transaction(connection) {
            UserValidator.exists(userID)
            PaperValidator.exists(paperID)
            UniquenessValidator.reviewExists(userID, paperID)
            ReviewTable
                    .insert {
                        it[ReviewTable.userID] = userID
                        it[ReviewTable.paperID] = paperID
                        it[ReviewTable.qualifier] = Qualifier.from(qualifier).value
                        it[recommandation] = recommendation
                    }
        }
}
