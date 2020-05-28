
import { HOST, PORT } from "../Globuls.js";
$(document).ready(function () {
    let name;
    let phase;
    let startDate;
    let endDate;
    let submisstiondeadline;
    let proposalDeadline;
    let biddingDeaedline;
    let submitEarly;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",

        complete: function (data, statusText) {
            if (data.statusText == "OK") {
                name = data.responseJSON.name;
                phase = data.responseJSON.currentPhase;
                startDate = data.responseJSON.startDate;
                endDate = data.responseJSON.endDate
                submisstiondeadline = data.responseJSON.submissionDeadline
                proposalDeadline = data.responseJSON.proposalDeadline
                biddingDeaedline = data.responseJSON.biddingDeadline
                submitEarly = data.responseJSON.submitPaperEarly
                document.getElementById("conference Details").innerHTML = "The name of the conference is  " + name + "<br>" +
                    "current phase : " + phase + "<br>" +
                    "start date : " + startDate + "<br>" +
                    "end date : " + endDate + "<br>" +
                    "submissions deadline : " + submisstiondeadline + "<br>" +
                    "proposals deadline : " + proposalDeadline + "<br>" +
                    "bidding deadline : " + biddingDeaedline + "<br>" +
                    "allows early submissions : " + submitEarly + "<br> :)";
            } else {
                alert("can not get details of the conference");
            }
        }
    });




    $("#changeDateConference").click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/conference/changeDate",
            dataType: "json",
            data: JSON.stringify({
                name: name,
                currentPhase:phase,
                startDate: startDate,
                endDate:endDate,
                submissionDeadline: $("#conferenceSubmissionDeadline").val() == "" ? submissionDeadline :  $("#conferenceSubmissionDeadline").val(),
                proposalDeadline: $("#conferenceProposalsDeadline").val() == "" ? proposalDeadline : $("#conferenceProposalsDeadline").val(),
                biddingDeadline: $("#conferenceBiddingDealine").val() == "" ? biddingDeaedline :  $("#conferenceBiddingDealine").val(),
                submitPaperEarly: submitEarly
                
            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("the date was changed");
                } else {
                    alert("the date was not changed");
                }
            }
        })

    });
});