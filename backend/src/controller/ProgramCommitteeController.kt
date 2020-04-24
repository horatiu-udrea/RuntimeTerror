import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ro.runtimeterror.cms.database.tables.ConferenceTable
import kotlin.RuntimeException


/**
 * Controller that's responsible for program committee functionality
 */
object ProgramCommitteeController {

    /**
     * Functionality for PCMembers bidding on papers
     *
     * @param {Int} paperID - the ID of the paper in question
     * @param {Int} userID - the ID of the PCMember bidding on the paper
     * @param {Int} reviewChoice - bid decision of the PCMember, can be:
     *          1 - pleased to review
     *          2 - indecisive
     *          3 - refuses to review
     *
     * @return Boolean - if the change has been successfully made to the database
     * @throws RuntimeException - if the reviewChoice isn't within the specified range
     */
    fun bidPapers(paperID: Int, userID: Int, reviewChoice: Int){
        if(reviewChoice !in 1..3) {
            throw RuntimeException("Invalid option")
        }
    }

    /**
     * parses all the papers into a list and returns them
     *
     * @return list - list containing all the papers
     * TODO Should it throw an exception if there aren't any papers in the database?
     */
    fun getPapers(){

    }

    /**
     * Method for setting the conference's information
     *
     * @param {String} name - The name of the conference
     * @param {DateTime} startDate - The start date of the conference
     *
     * @throws RuntimeException - throws an exception if the user doesn't
     */
    fun setConferenceInfo(name: String, startDate: DateTime,
                          endDate: DateTime, abstractDeadline: DateTime,
                          proposalDeadline: DateTime, biddingDeadline: DateTime,
                          submitPaperEarly: Boolean){

    }

    /**
     *  Gets information about the conference from the database
     *
     * @return String - Parses the data about the conference into a String an returns it
     */
    fun getConferenceInfo(){

    }

    /**
     *              ********* CO-CHAIR OPERATION ***********
     *
     *@param {Int} paperID - the id of the paper to be reviewed
     *@param {Int} userID - the id of the PCMember responsible for reviewing the paper
     *
     *@throws RuntimeException Throws an exception if the user doesn't have access rights
     */
    fun assignPapers(paperID: Int, userID: Int){

    }

    /**
     *  Allows a user with the accessRights of a Co-Chair or higher to start a discussion regarding a conflicting paper
     *              ********* CO-CHAIR OPERATION ***********
     * @param {Int} userID - the userID of the user with access rights of co-chair or higher
     * @param {Int} paperID - the id of the conflicting paper
     *
     * @throws RuntimeException if the user doesn't have appropriate access rights
     * @throws RuntimeException throws an exception if the paper isn't conflicting
     */
    fun requestDiscussion(userID: Int,
                          paperID: Int){

    }

    /**
     * Gets the conflicting papers (guessing on page load)
     *
     * @return String - Conflicting paperIDs to be reviewed
     * TODO How are we going to handle conflicting papers in terms of allowing PCMembers to vote on them?
     */
    fun getConflictingPapers(){

    }

    /**
     *          ********* CHAIR OPERATION ***********
     * allows the chair to make the final decision on a conflicting paper
     *
     * @param {Int} userID - The userID of the chair
     * @param {Int} paperID - the paperID of the conflicting paper
     * @param {Boolean} decision
     *
     * @throws RuntimeException if the user doesn't have sufficient rights to make final decision
     * @throws RuntimeException if the user doesn't exist
     * @throws RuntimeException if the paper isn't conflicting
     */
    fun getFinalDecision(userID: Int, decision: Boolean){

    }
}
