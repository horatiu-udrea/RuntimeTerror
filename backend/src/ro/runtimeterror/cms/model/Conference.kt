package ro.runtimeterror.cms.model

import org.joda.time.DateTime

class Conference(
    val name: String,
    val startDate: DateTime,
    val endDate: DateTime,
    val submissionDeadline: DateTime,
    val proposalDeadline: DateTime,
    val biddingDeadline: DateTime,
    val submitPaperEarly: Boolean,
    val currentPhase: Int
)