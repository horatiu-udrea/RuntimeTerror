package ro.runtimeterror.cms.model

import java.lang.RuntimeException

enum class PaperBidResult(val value: Int)
{
    PLEASED_TO_REVIEW(1),
    INDECISIVE(2),
    REFUSE_TO_REVIEW(3);

    companion object
    {
        fun from(value: Int): PaperBidResult
        {
            return PaperBidResult.values().find {
                it.value == value
            } ?: throw RuntimeException("Invalid paper bid result")
        }
    }
}