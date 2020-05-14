package ro.runtimeterror.cms.controller

import ro.runtimeterror.cms.model.Conference
import ro.runtimeterror.cms.repository.Repository

class ConferenceController(private val repository: Repository)
{
    /**
     * Conference details
     */
    fun getConferenceDetails(): Conference
    {
        TODO("Not yet implemented")
    }

    /**
     * Modify conference details
     */
    fun changeConferenceInformation(conferenceInformation: Conference)
    {
        TODO("Not yet implemented")
    }

    fun getPhase(): Int
    {
        TODO("Not yet implemented")
    }

}
