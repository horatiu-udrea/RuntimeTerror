package ro.runtimeterror.cms.model

import java.lang.RuntimeException

enum class Type(val value: Int)
{
    ADMIN(5),
    CHAIR(4),
    CO_CHAIR(3),
    PC_MEMBER(2),
    AUTHOR(1),
    NORMAL(0);

    companion object
    {
        fun from(value: Int): Type
        {
            return values().find {
                it.value == value
            } ?: throw RuntimeException("Invalid access level")
        }
    }
}