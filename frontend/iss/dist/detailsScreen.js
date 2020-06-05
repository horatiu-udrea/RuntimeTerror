import {HOST, PORT} from "../src/Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});


function callAlert(message, messageYes, messageNo, actionYes, actionNo){
    $("#alertMessage").text(message);   
    $("#alertButtonYes").click(actionYes);
    $("#alertButtonNo").click(actionNo);
    $("#alertButtonYes").text(messageYes);
    $("#alertButtonNo").text(messageNo);
    $("#alertBackground").toggle();
}

$(document).ready(function () {
    let phase = 0;
    let bidDate;

    $("#alertBackground").toggle();

    $("#alertBackground").click(function () {
        $("#alertBackground").toggle();
    })

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",
        complete: function (data) {
            if (data.statusText == "OK") {
                phase = data.responseJSON.currentPhase;
                bidDate = data.responseJSON.biddingDeadline;

                if(phase == 0) window.location.href = "../src/LoginScreen/loginScreen.html";
                if(phase != 3) $("#sectionsLink").text("");
                $("#dates").text(data.responseJSON.startDate + "-" + data.responseJSON.endDate)
                $("#name").text(data.responseJSON.name)
            }
        }
    });


    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/authentication",
        dataType: "json",
        complete: function (data) {
            let role = data.responseJSON.type;
            let today = Date();


            if (role == 1) {
                if (phase == 1) $("#link").attr("href", "../src/AuthorScreens/authorSubmit.html");
                else if (phase == 2) $("#link").attr("href", "../src/AuthorScreens/authorImproveAndUpdate.html");
                else if (phase == 3) $("#link").attr("href", "../src/AuthorScreens/authorImproveAndUpdate.html");
                else $("#link").attr("href", "../src/unavailable/unavailable.html");
            }

            if (role == 2) {
                $("#link").click(function (e) {
                e.preventDefault();
                
                callAlert("Check your papers or review other ones?", "Author", "PcMember", 
                function () {
                    
                    if (phase == 1) window.location.href = "../src/AuthorScreens/authorImproveAndUpdate.html";
                    else if (phase == 2) window.location.href = "../src/AuthorScreens/authorImproveAndUpdate.html";
                    else if (phase == 3) window.location.href = "../src/AuthorScreens/authorImproveAndUpdate.html";
                    else window.location.href = "../src/unavailable/unavailable.html";
                },
                    function () {
                        if (phase == 2) {
                            if (today < bidDate) window.location.href =  "../src/BiddingScreen/biddingScreen.html";
                            else window.location.href =  "../src/ReviewingScreen/reviewingScreen.html";
                            // modify grades
                        }
                        
                        else window.location.href =  "../src/unavailable/unavailable.html";
                    });
                });
                }

            if (role == 3) {
                if (phase == 0) $("#link").attr("href", "../src/ConferenceScreens/changeDate.html");
                else if (phase == 2) {
                    $("#link").click(function (e) {
                        e.preventDefault();
                    callAlert("Assign papers to reviewers, or deal with conflicting papers?", "Assign", "Conflicting", function () {
                        window.location.href =  "../src/AssignToReviewerScreen/assignToReviewer.html";
                    },
                        function () {
                            window.location.href =  "../src/ConflictingDiscussion/conflictingDiscussion.html";
                        });
                    })
                }
                
                else $("#link").attr("href", "../src/unavailable/unavailable.html");
            }

            if (role == 4) {
                if (phase == 0) $("#link").attr("href", "../src/ConferenceScreens/changeDate.html");
                else if (phase == 2) $("#link").attr("href", "../src/AssignToReviewerScreen/assignToReviewer.html");
                
                else $("#link").attr( "../src/unavailable/unavailable.html");
            }

            if (role == 5) {
                if (phase == 0)
                $("#link").click(function (e) {
                    e.preventDefault();
                callAlert("Pick Pc members or update conference?", "Pick members", "Update Conference", 
                function () {
                    window.location.href =  "../src/PcMemberPickScreen/pcMemberPickScreen.html";
                },
                function () {
                    window.location.href =  "../src/ConferenceScreens/CreateConference.html"
                });
                })
                else if (phase == 1) $("#link").attr("href", "../src/ConferenceScreens/CreateConference.html"); // change accounts.
                else if (phase == 2) $("#link").attr("href", "../src/ConferenceScreens/CreateConference.html");
                else if (phase == 3)
                $("#link").click(function (e) {
                    e.preventDefault();
                callAlert("Create sections or update conference?", "Create Sections", "Update Conference", 
                function () {
                    window.location.href =  "../src/CreateSection/createSection.html";
                },
                function () {
                    window.location.href =  "../src/ConferenceScreens/CreateConference.html"
                });
            })
                else $("#link").attr("href", "../src/unavailable/unavailable.html");
            }
        },
        error: function (data) {
            if (data.responseJSON.error == "Not logged in!")
                $("#link").attr("href", "../src/LoginScreen/loginScreen.html")
        }
    });
});