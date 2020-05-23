package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.PaperStatus
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.daos.PaperDAO
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.model.Paper

import ro.runtimeterror.cms.model.PaperSuggestion
import ro.runtimeterror.cms.model.Qualifier

class PaperDecisionController
{
    /**
     * Decide the status of a paper (accepted, rejected, conflicting)
     */
    fun decide(paperID: Int, status: Int) = transaction(connection) {
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.status] = PaperStatus.from(status).value
            }
        }

    /**
     * Get all the papers and associate a suggestion based on the reviews
     *
     * Suggestions:
     *  If no accept qualifier-> reject status
     *  if no decline qualifier -> accept status
     *  else conflicting
     */
    fun getPapers(): List<PaperSuggestion> = transaction(connection){
        val allPapers: List<Paper> = PaperDAO.all().with(PaperDAO::authorIterable).toList()
        return@transaction allPapers
            .map {
                if(noAccepts(it.paperId)){
                    PaperSuggestion(it, PaperStatus.REJECTED)
                }else if(noRejects(it.paperId)){
                    PaperSuggestion(it, PaperStatus.ACCEPTED)
                }else{
                    PaperSuggestion(it, PaperStatus.CONFLICTING)
                }
            }
            .toList()
    }

//    Returns true if there are no rejects
    private fun noRejects(paperId: Int): Boolean = transaction(connection){
        return@transaction ReviewTable
            .select { ReviewTable.paperID eq paperId }
            .filter { it[ReviewTable.qualifier] != Qualifier.STRONG_REJECT.value || it[ReviewTable.qualifier] != Qualifier.REJECT.value}
            .isEmpty()
    }
//  Returns true if there are no accepts
    private fun noAccepts(paperId: Int): Boolean = transaction(connection){
        return@transaction ReviewTable
            .select { ReviewTable.paperID eq paperId }
            .filter { it[ReviewTable.qualifier] != Qualifier.STRONG_ACCEPT.value || it[ReviewTable.qualifier] != Qualifier.ACCEPT.value}
            .isEmpty()
    }
}
