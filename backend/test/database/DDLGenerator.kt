package ro.runtimeterror.cms.database

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ro.runtimeterror.cms.database.tables.*
import java.io.File


fun main()
{
    DatabaseSettings.connection
    transaction {
        val createStatements = SchemaUtils.createStatements(
                ConferenceTable,
                UserTable,
                PaperTable,
                PaperSubmissionTable,
                BidPaperTable,
                ReviewTable,
                SectionTable,
                UserSectionChoiceTable
        )
        val file = File("../database/ddl.sql")
        file.createNewFile()
        file.bufferedWriter().use { out ->
            createStatements.forEach {
                out.write(it)
                out.write(";")
                out.newLine()
            }
        }
    }
}