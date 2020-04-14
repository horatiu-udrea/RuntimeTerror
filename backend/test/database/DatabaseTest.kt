package database

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import ro.runtimeterror.cms.database.DatabaseSettings

internal class DatabaseTest
{
    object TestTable : Table("Test")
    {
        val id = integer("id").autoIncrement() // Column<Int>
        val name = varchar("name", 50) // Column<String>

        override val primaryKey = PrimaryKey(id, name = "PK_Test_ID")
    }

    @Test
    fun testConnection()
    {
        transaction(DatabaseSettings.connection) {
            SchemaUtils.create(TestTable)
            SchemaUtils.drop(TestTable)
        }
    }
}