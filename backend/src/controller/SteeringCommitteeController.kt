import org.joda.time.DateTime

object SteeringCommitteeController {
    /**
     * Create a new conference by setting the given fields in the database
     *
     * @param {String} name - The name of the conference
     * @param {DateTime} startDate - The start date of the conference
     * @param {DateTime} endDate - The date for which the deadline ends
     * @param {DateTime} abstractDeadline - The deadline for submitting the abstract by the authors
     * @param {DateTime} proposalDeadline - The deadline for author proposals
     * @param {DateTime} biddingDeadline - the deadline for which the PCMembers can bid on papers
     * TODO what's the difference between the proposal deadline and the abstract deadline
     * @param {Boolean} submitPaperEarly - I have no idea what the point of this is
     */
    fun createConference(name: String,
                         startDate: DateTime,
                         endDate: DateTime,
                         abstractDeadline: DateTime,
                         proposalDeadline: DateTime,
                         biddingDeadline: DateTime,
                         submitPaperEarly: Boolean){

    }

    /**
     * Adds a new section to the conference
     *
     * @param {Int} roomID - The id of the room for which the section is going to be held
     * @param {String} name - The name of the section
     * @param {String} description - information about said section
     * @param {DateTime} startTime - The time for which the first event of the section starts
     * @param {DateTime} endTime - The time for which the last event of the section ends
     *
     * @throws RuntimeException - The room is already take
     */
    fun createConferenceSection(roomID: Int,
                                name: String,
                                description: String,
                                startTime: DateTime,
                                endTime: DateTime){

    }
}