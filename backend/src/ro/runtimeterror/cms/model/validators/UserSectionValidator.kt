package ro.runtimeterror.cms.model.validators

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.DatabaseSettings.connection
import ro.runtimeterror.cms.database.tables.UserSectionChoiceTable
import ro.runtimeterror.cms.exceptions.UserSectionChoiceAlreadyExists

class UserSectionValidator {
    companion object{
        fun exists(userID: Int, sectionId: Int) = transaction(connection) {
            if(UserSectionChoiceTable
                    .selectAll()
                    .any { it[UserSectionChoiceTable.sectionID] == sectionId && it[UserSectionChoiceTable.userID] == userID }
            ){
                throw UserSectionChoiceAlreadyExists("The user has already picked this section!")
            }

        }
    }
}
