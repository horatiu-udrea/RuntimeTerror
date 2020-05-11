package ro.runtimeterror.cms.database.daos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ro.runtimeterror.cms.database.tables.UserTable
import ro.runtimeterror.cms.model.User
import ro.runtimeterror.cms.model.UserType

class UserDAO(id: EntityID<Int>) : IntEntity(id), User
{
    companion object : IntEntityClass<UserDAO>(UserTable)

    override var name by UserTable.name
    override var username by UserTable.username
    override var password by UserTable.password
    override var affiliation by UserTable.affiliation
    override var email by UserTable.email
    override var webPage by UserTable.webPage
    override var validated by UserTable.validated
    override var hasTicket by UserTable.hasTicket
    private var typeValue by UserTable.type

    override val userId: Int
        get() = id.value

    override var type: UserType
        get() = UserType.from(typeValue)
        set(value)
        {
            typeValue = value.value
        }
}