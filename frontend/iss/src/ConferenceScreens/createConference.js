$(document).ready(function () {

    $("#createConference").click(function (e) {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/conference",
            dataType: "json",
            data: JSON.stringify({
                name: $("#conferenceName").val(),
                currentPhase: $("#conferenceCurrentPhase").val(),
                startDate: $("#conferenceStartDate").val(),
                endDate: $("#conferenceEndDate").val(),
                submissionDeadline: $("#conferenceSubmissionDeadline").val(),
                proposalDeadline: $("#conferenceProposalsDeadline").val(),
                biddingDeadline: $("#conferenceBiddingDealine").val(),
                submitPaperEarly: $("#conferenceEarlySubmissions").val()
            }),
            complete: function(data){
                if (data.statusText == "OK"){
                    alert("successfully created conference");
                    window.location = "to be decided";
                } else {
                    alert("conference was not created");
                }

            }

        });
           
    });
});