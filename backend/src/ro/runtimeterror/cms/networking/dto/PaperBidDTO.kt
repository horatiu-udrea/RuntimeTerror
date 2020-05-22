package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.controller.PaperBid

data class PaperBidDTO(val paper: PaperDTO, val bidResult: Int)

fun List<PaperBid>.toPaperBidDTO(): List<PaperBidDTO>
{
    return map { paperBid -> PaperBidDTO(paperBid.paper.toDTO(), paperBid.bidResult.value) }
}

data class BidDTO(val paperId: Int, val bidResult: Int)