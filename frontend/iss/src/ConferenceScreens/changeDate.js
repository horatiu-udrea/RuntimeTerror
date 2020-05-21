

$(document).ready(function () {
    $.get("http://localhost:8080/conference", function(data) {
        $("#conferenceDetails").html(data)
    });


    $("#changeDateConference").click(function () {
        $.post("http://localhost:8080/changeDate", {
            submissionDeadline: $("#changeConferenceSubmissionDeadline").val(),
            proposalDeadline: $("#changeConferenceProposalsDeadline").val(),
            biddingDeadline: $("#changeCnferenceBiddingDealine").val(),
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