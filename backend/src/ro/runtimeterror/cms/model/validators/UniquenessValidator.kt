package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.tables.BidPaperTable
import ro.runtimeterror.cms.database.tables.ReviewTable
import ro.runtimeterror.cms.exceptions.PrimaryKeyAlreadyExistException

class UniquenessValidator {
    companion object{
        fun reviewExists(userID: Int, paperID: Int){
            transaction(connection) {
                if(
                    ReviewTable
                        .selectAll()
                        .any { it[ReviewTable.paperID] == paperID && it[ReviewTable.userID] == userID }
                ){
                    throw PrimaryKeyAlreadyExistException("Review Already Exists!")
                }
            }
        }

        fun bidExists(userID: Int, paperID: Int): Boolean {
                return BidPaperTable
                    .selectAll()
                    .any{it[BidPaperTable.userID] == userID && it[BidPaperTable.paperID] == paperID}
        }
    }
}