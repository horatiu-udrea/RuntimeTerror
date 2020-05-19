package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.model.PaperStatus

class PaperDecisionController
{
    /**
     * Decide the status of a paper (accepted, rejected, conflicting)
     */
    fun decide(paperID: Int, status: Int)
    {
        val paperStatus: PaperStatus = PaperStatus.from(status)
        transaction(DatabaseSettings.connection) {
            PaperTable.update({PaperTable.id eq paperID}) {
                it[PaperTable.status] = paperStatus.value
            }
        }
    }

}
