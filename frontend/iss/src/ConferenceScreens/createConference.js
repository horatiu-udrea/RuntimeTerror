$(document).ready(function () {

    $("#createConference").click(function () {

        $.post("http://localhost:8080/conference", {
            name: $("#conferenceName").val(),
            currentPhase: $("#conferenceCurrentPhase").val(),
            startDate: $("#conferenceStartDate").val(),
            endDate: $("#conferenceEndDate").val(),
            submissionDeadline: $("#conferenceSubmissionDeadline").val(),
            proposalDeadline: $("#conferenceProposalsDeadline").val(),
            biddingDeadline: $("#conferenceBiddingDealine").val(),
            submitPaperEarly: $("#conferenceEarlySubmissions").val()

        })
            .done(function () {
                //Nu stiu exact aici cum vine faza, dar for now...
                window.location = "ADD HOME LINK HERE";
            })
            .fail(function (error) {
                $("input[type=text]").val = error.responseText;
            });
    });
});