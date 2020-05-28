package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.tables.PaperTable
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
    }
}
