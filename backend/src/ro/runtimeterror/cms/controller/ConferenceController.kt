package ro.runtimeterror.cms.controller

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ro.runtimeterror.cms.database.DatabaseSettings
import ro.runtimeterror.cms.database.tables.ConferenceTable
import ro.runtimeterror.cms.model.Conference
import ro.runtimeterror.cms.exceptions.ConferenceDetailsNotSetException
import ro.runtimeterror.cms.database.DatabaseSettings.connection

class ConferenceController
{
    /**
     * Conference details
     */
    fun getConferenceDetails(): Conference =
        transaction(connection){
            return@transaction ConferenceTable
                .selectAll()
                .map {
                    Conference(
                        it[ConferenceTable.name],
                        it[ConferenceTable.startDate],
                        it[ConferenceTable.endDate],
                        it[ConferenceTable.submissionDeadline],
                        it[ConferenceTable.proposalDeadline],
                        it[ConferenceTable.biddingDeadline],
                        it[ConferenceTable.submitPaperEarly],
                        it[ConferenceTable.currentPhase]
                    )
                }.firstOrNull()?:throw ConferenceDetailsNotSetException(
                "The details for the conference have not been set"
            )
        }

    /**
     * Modify conference details
     */
    fun changeConferenceInformation(conferenceInformation: Conference) = transaction(connection){
            ConferenceTable.update {
                it[name] = conferenceInformation.name
                it[endDate] = conferenceInformation.endDate
                it[startDate] = conferenceInformation.startDate
                it[submissionDeadline] = conferenceInformation.submissionDeadline
                it[proposalDeadline] = conferenceInformation.proposalDeadline
                it[biddingDeadline] = conferenceInformation.biddingDeadline
                it[submitPaperEarly] = conferenceInformation.submitPaperEarly
                it[currentPhase] = conferenceInformation.currentPhase
            }
        }

//    fun getPhase(): Int
//    {
//        var currentPhase: Int? = null
//        transaction {
//            currentPhase = ConferenceTable
//                .selectAll()
//                .map { it[ConferenceTable.currentPhase] }
//                .first()
//        }
//        return currentPhase?: throw RuntimeException("invalid phase value!")
//    }
}
