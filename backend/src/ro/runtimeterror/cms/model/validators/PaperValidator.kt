package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.tables.PaperSubmissionTable
import ro.runtimeterror.cms.database.tables.PaperTable
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.exceptions.PCMemberNotAssignedPaperException
import ro.runtimeterror.cms.exceptions.PaperDoesNotExistException

class PaperValidator {
    companion object{
        fun exists(paperID: Int){
            var exists = true
            transaction {
                exists = !PaperTable
                        .select { PaperTable.id eq paperID}
                        .empty()
            }
            if(!exists){
                throw PaperDoesNotExistException("The paper does not exists")
            }
        }

        fun checkPcMemberAssigned(userID: Int, paperID: Int) = transaction(connection) {
            if(
                ReviewTable
                    .select { (ReviewTable.userID eq userID) and (ReviewTable.paperID eq paperID)}
                    .empty()
            ){
                throw PCMemberNotAssignedPaperException("The PCMember is not assigned this paper exception!")
            }
        }
    }
}
