import {HOST, PORT} from "../Globuls";
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});


$(document).ready(function () {

    $("#createConference").click(function (e) {

        let s = $("#conferenceStartDate").val().replace(/-/g, "/").split("/").reverse();
        let start = s[0] + "/" + s[1] + "/" + s[2];
        let endformat = $("#conferenceEndDate").val().replace(/-/g, "/").split("/").reverse();
        let end = endformat[0] + "/" + endformat[1] + "/" + endformat[2];
        let submis = $("#conferenceSubmissionsDeadline").val().replace(/-/g, "/").split("/").reverse();
        let submission = submis[0] + "/" + submis[1] + "/" + submis[2];
        let proposal = $("#conferenceProposalsDeadline").val().replace(/-/g, "/").split("/").reverse();
        let proposalDeadline = proposal[0] + "/" + proposal[1] + "/" + proposal[2];
        let bidding = $("#conferenceBiddingDeadline").val().replace(/-/g, "/").split("/").reverse();
        let biddingDeaedline = bidding[0] + "/" + bidding[1] + "/" + bidding[2];

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/conference",
            dataType: "json",
            data: JSON.stringify({
                name: $("#conferenceName").val(),
                currentPhase: $("#conferenceCurrentPhase").val(),
                startDate: start,
                endDate: end,
                submissionDeadline: submission,
                proposalDeadline: proposalDeadline,
                biddingDeadline: biddingDeaedline,
                submitPaperEarly: $("#conferenceEarlySubmissions").val()
            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("successfully created conference");
                    window.location = "to be decided";
                } else {
                    alert("conference was not created");
                }

            }

        });

    });
    $("#logout").click(function () {
        $.ajax({
            type: "POST",
            url: HOST + PORT + "/authentication/logout",
            contentType: "application/json",

            complete: function (data) {
                if (data.statusText == "OK") {
                    localStorage.clear();
                    window.location = "../../dist/index.html";
                } else {
                    alert("fail");
                }
            }
        })
    });
});