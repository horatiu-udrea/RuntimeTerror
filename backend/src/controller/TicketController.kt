object TicketController{
    /**
     * changes the field hasTicket to true in the Users table
     *
     * @param {Int} userID
     *
     * @throws RuntimeException the user already has a ticket
     *
     */
    fun buyTicket(userID: Int){

    }

    /**
     * gets general information regarding the conference
     *              StartDate of conference
     *              EndDate of conference
     *              maybe
     *                      location of conference
     *                      price
     *
     *
     * @return String ticketInformation
     *
     */
    fun ticketInformation(){

    }

    /**
     *
     * Gets information regarding all the sections that the user's registered to
     *
     * @param {Int} userID
     *
     * @return String - with details regarding all the sections that the user is registered to
     */
    fun ticketDetails(userID: Int){

    }

    /**
     * Lets a normal user choose the section that he wants to join
     * @param {Int} roomID - the roomID of the room associated with a given section
     *
     *
     */
    fun chooseSection(roomID: Int){

    }

    /**
     * gives the user more details about a specific section, given the roomID
     *
     * @param {Int} roomID
     * @return String - details regarding a specific section
     *
     */
    fun getSection(roomID: Int){

    }
}

