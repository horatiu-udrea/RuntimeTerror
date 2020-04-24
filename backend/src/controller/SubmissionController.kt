object SubmissionController{
    /**
     * allows the user to submit
     *
     * @param {String} field - The field for which the paper is concerned
     * @param {String} documentPath - The path for the user's' paper
     * @param {Boolean} conflicting - True if the reviews for the paper are conflicting, false otherwise
     * @param {String} proposalName - the title of the proposal submitted by the user
     * @param {String} keywords - TODO any idea what  the keywords are? and if they're a list this feels like it doesn't confide with 1NF?
     * @param {String} listOfAuthors - TODO this doesn't confide with 1NF?
     * @param {Boolean} accepted - True if the paper has been approved by the reviewers, false otherwise
     *
     */
    fun submitPaper(field: String,
                    documentPath: String,
                    conflicting: Boolean,
                    proposalName: String,
                    keywords: String,
                    listOfAuthors: String,
                    accepted: Boolean){

    }

    /**
     * @param {Int} userID - The userID of an author who submitted a paper already
     *
     * @return String with the user's current submission
     * @throws RuntimeException If the user didn't submit any pappers
     */
    fun getPaper(userID: Int){

    }

    /**
     * Method for uploading the presentation to the website
     *
     * @param {Int} sectionID - the section for which presentation pertains
     * TODO Some ambiguity here, the userID could be the speaker, the supervisor
     * @param {Int} userID - the userID of the presentation's speaker
     * @param {String} documentPath - The path for the presentation
     *
     * @throws RuntimeException The sectionID-userID combination is not unique
     */
    fun uploadPresentation(sectionID: Int,
                           userID: Int,
                           documentPath: Int){

    }

    /**
     * Method allowing speaker to update the presentation that he has put up online
     *
     * @param {Int} sectionID - the section for which the presentation pertains
     * @param {Int} userID - the userID of the presentation's speaker
     * @param {String} newDocumentPath - The path for the modified presentation
     *
     * @throws RuntimeException The sectionID-userID combination is not unique
     */
    fun improvePaper(sectionID: Int,
                     userID: Int,
                     newDocumentPath: String){

    }
}