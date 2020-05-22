package ro.runtimeterror.cms.model

enum class PaperStatus(val value: Int)
{
    UNDECIDED(1),
    ACCEPTED(2),
    REJECTED(3),
    CONFLICTING(4);

    companion object
    {
        fun from(value: Int): PaperStatus
        {
            return PaperStatus.values().find {
                it.value == value
            } ?: throw RuntimeException("Invalid qualifier")
        }
    }
}