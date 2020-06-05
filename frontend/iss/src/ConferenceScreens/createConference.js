import { HOST, PORT } from "../Globuls.js";
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});


$(document).ready(function () {
    let name;
    let currentPhase;
    let startDate;
    let endDate;
    let submissionDeadline;
    let proposalDeadline;
    let biddingDeadline;
    let submitPaperEarly;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",
        complete: function (data) {
            if (data.statusText == "OK") {
                name = data.responseJSON.name;
                currentPhase = data.responseJSON.currentPhase;
                startDate = data.responseJSON.startDate;
                endDate = data.responseJSON.endDate;
                submissionDeadline = data.responseJSON.submissionDeadline;
                proposalDeadline = data.responseJSON.proposalDeadline;
                biddingDeadline = data.responseJSON.biddingDeadline;
                submitPaperEarly = data.responseJSON.submitPaperEarly;
            } else {
                alert("can not get conference");
            }

        }

    });


    $("#createConference").click(function (e) {
        console.log(biddingDeadline, submissionDeadline, proposalDeadline);
        name = $("#conferenceName").val() == "" ? name : $("#conferenceName").val();
        currentPhase = $("#conferenceCurrentPhase").val() == "" ? currentPhase : $("#conferenceCurrentPhase").val();
        submitPaperEarly = $("#conferenceEarlySubmissions").val() == "" ? submitPaperEarly : $("#conferenceEarlySubmissions").val();
        if ($("#conferenceStartDate").val() != "") {
            let s = $("#conferenceStartDate").val().replace(/-/g, "/").split("/").reverse();
            startDate = (s[0] + "/" + s[1] + "/" + s[2]);
        }
        if ($("#conferenceEndDate").val() != "") {
            let endformat = $("#conferenceEndDate").val().replace(/-/g, "/").split("/").reverse();
            let end = endformat[0] + "/" + endformat[1] + "/" + endformat[2];
            endDate = end;
        }
        if ($("#conferenceSubmissionsDeadline").val() != "") {
            let submis = $("#conferenceSubmissionsDeadline").val().replace(/-/g, "/").split("/").reverse();
            let submission = submis[0] + "/" + submis[1] + "/" + submis[2];
            submissionDeadline = submission
        }
        if ($("#conferenceProposalsDeadline").val() != "") {
            let proposal = $("#conferenceProposalsDeadline").val().replace(/-/g, "/").split("/").reverse();
            let proposalDead = proposal[0] + "/" + proposal[1] + "/" + proposal[2];
            proposalDeadline = proposalDead;
        }
        if ($("#conferenceBiddingDeadline").val() != "") {
            let bidding = $("#conferenceBiddingDeadline").val().replace(/-/g, "/").split("/").reverse();
            let biddingDeaedline = bidding[0] + "/" + bidding[1] + "/" + bidding[2];
            biddingDeadline = biddingDeaedline;
        }
        console.log(biddingDeadline, submissionDeadline, proposalDeadline);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/conference",
            dataType: "json",
            data: JSON.stringify({
                name: name,
                currentPhase: currentPhase,
                startDate: startDate,
                endDate: endDate,
                submissionDeadline: submissionDeadline,
                proposalDeadline: proposalDeadline,
                biddingDeadline: biddingDeadline,
                submitPaperEarly: submitPaperEarly
            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("successfully created conference");
                    // window.location = "to be decided";
                } else {
                    alert("conference was not created");
                }

            }

        });

    });
    $.ajax({
        type: "get",
        url: HOST + PORT + "/authentication",
        contentType: "application/json",
        
        complete: function (data) {
            $("#username").text(data.responseJSON.name)
        }
    })
    
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

    $("#back").click(function () {
        
        window.location = "../../dist/index.html";

    }); 
});