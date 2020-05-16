package ro.runtimeterror.cms.model

import java.lang.RuntimeException

enum class Qualifier(val value: Int)
{
    NOT_YET_REVIEWED(0),
    STRONG_ACCEPT(1),
    ACCEPT(2),
    WEAK_ACCEPT(3),
    BORDERLINE_PAPER(4),
    WEAK_REJECT(5),
    REJECT(6),
    STRONG_REJECT(7);


    companion object
    {
        fun from(value: Int): Qualifier
        {
            return Qualifier.values().find {
                it.value == value
            } ?: throw RuntimeException("Invalid qualifier")
        }
    }
}