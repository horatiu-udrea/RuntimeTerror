object ReviewController{
    /**
     * The reviewer gives the paper with paperID a review (0-7)
     *
     * @param {Int} paperID
     * @param {Int} reviewID
     *
     * @throws RuntimeException if the review parameter is not between 0-7
     */
    fun review(paperID: Int, reviewID: Int){
        if (reviewID !in 0..7){
            throw RuntimeException("The reviewID is not within the appropriate range (0-7)")
        }
    }

    /**
     * returns a list with all the paperIDs to be reviewed by the reviewer
     *
     * @return {list} - list with all the paperIDs that need to be reviewed
     */
    fun seePapers(){

    }
}