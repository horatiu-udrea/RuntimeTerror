
import { HOST, PORT } from "../Globuls.js";
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

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
            console.log(data)
            if (data.statusText == "OK") {
                name = data.responseJSON.name;
                phase = data.responseJSON.currentPhase;
                startDate = data.responseJSON.startDate;
                endDate = data.responseJSON.endDate
                submisstiondeadline = data.responseJSON.submissionDeadline
                proposalDeadline = data.responseJSON.proposalDeadline
                biddingDeaedline = data.responseJSON.biddingDeadline
                submitEarly = data.responseJSON.submitPaperEarly
                document.getElementById("conferenceDetails").innerHTML = "The name of the conference is  " + name + "<br>" +
                    "current phase : " + phase + "<br>" +
                    "start date : " + startDate + "<br>" +
                    "end date : " + endDate + "<br>" +
                    "submissions deadline : " + submisstiondeadline + "<br>" +
                    "proposals deadline : " + proposalDeadline + "<br>" +
                    "bidding deadline : " + biddingDeaedline + "<br>" +
                    "allows early submissions : " + submitEarly + ":)<br> ";
            } else {
                alert("can not get details of the conference");
            }
        }
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

    $("#changeDateConference").click(function () {

        if ($("#changeConferenceSubmissionsDeadline").val() != "") {
            let submisstion = $("#changeConferenceSubmissionsDeadline").val().replace(/-/g, "/").split("/").reverse();
            submisstiondeadline = submisstion[0] + "/" + submisstion[1] + "/" + submisstion[2];
        }
        if ($("#changeConferenceProposalsDeadline").val() != "") {
            let proposal = $("#changeConferenceProposalsDeadline").val().replace(/-/g, "/").split("/").reverse();
            proposalDeadline = proposal[0] + "/" + proposal[1] + "/" + proposal[2];
        }
        if ($("#changeConferenceBiddingDeadline").val() != "") {
            let bidding = $("#changeConferenceBiddingDeadline").val().replace(/-/g, "/").split("/").reverse();
            biddingDeaedline = bidding[0] + "/" + bidding[1] + "/" + bidding[2];
        }
        //Andu added some shit here, sowwy.
        if($("#phaseSelect").children("option:selected").val() != "Phase") {
            phase = $("#phaseSelect").children("option:selected").val();
        }
        
       console.log(submisstiondeadline, biddingDeaedline, proposalDeadline);
        
     
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/conference",
            dataType: "json",
            data: JSON.stringify({
                name: name,
                currentPhase: phase,
                startDate: startDate,
                endDate: endDate,
                submissionDeadline: submisstiondeadline,
                proposalDeadline: proposalDeadline,
                biddingDeadline: biddingDeaedline,
                submitPaperEarly: submitEarly

            }),
            complete: function (data) {
                if (data.statusText == "OK") {
                    alert("the date was changed");
                    location.reload();
                } else {
                    alert("the date was not changed");
                }
            }
        })

    });
});