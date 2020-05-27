package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.PaperSuggestion

data class PaperDecisionDTO(val paperId: Int, val status: Int)

data class PaperSuggestionDTO(val paperDTO: PaperDTO, val suggestion: Int)

fun List<PaperSuggestion>.toDTO(): List<PaperSuggestionDTO>
{
    return map { paperSuggestion ->
        PaperSuggestionDTO(
            paperSuggestion.paper.toDTO(),
            paperSuggestion.suggestion.value
        )
    }
}