import {HOST, PORT} from "../src/Globuls.js"

$(document).ready(function () {
    let phase = 0;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",
        complete: function (data) {
            if (data.statusText == "OK") {
                phase = data.responseJSON.currentPhase;
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

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: HOST + PORT + "/authentication",
                dataType: "json",
                success: function (data) {
                    let role = data.responseJSON.type;
        
                    if (role == 1) {
                        if (phase == 1) $("#link").attr("../src/AuthorScreens/authorSubmit.html");
                        else if (phase == 2) $("#link").attr("../src/AuthorScreens/authorImproveAndUpdate.html");
                        else if (phase == 3) $("#link").attr("../src/AuthorScreens/authorImproveAndUpdate.html");
                        else $("#link").attr("../src/unavailable/unavailable.html");
                    }
        
                    if (role == 2) {
                        callAlert("What would you like to log in as?", "Author", "PcMember", function () {
                            
                            if (phase == 1) $("#link").attr("../src/AuthorScreens/authorImproveAndUpdate.html");
                            else if (phase == 2) $("#link").attr("../src/AuthorScreens/authorImproveAndUpdate.html");
                            else if (phase == 3) $("#link").attr("../src/AuthorScreens/authorImproveAndUpdate.html");
                            else $("#link").attr("../src/unavailable/unavailable.html");
                        },
                            function () {
                                if (phase == 2) {
                                    if (today < bidDate) $("#link").attr("../src/BiddingScreen/biddingScreen.html");
                                    else $("#link").attr("../src/ReviewingScreen/reviewingScreen.html");
                                    // modify grades
                                }
                                
                                else $("#link").attr("../src/unavailable/unavailable.html");
                            });
                        }

                    if (role == 3) {
                        if (phase == 0) $("#link").attr("../src/ConferenceScreens/changeDate.html");
                        else if (phase == 2) {
                            callAlert("Assign papers to reviewers, or deal with conflicting papers?", "Assign", "Conflicting", function () {
                                $("#link").attr("../src/AssignToReviewerScreen/assignToReviewer.html");
                            },
                                function () {
                                    $("#link").attr("../src/ConflictingDiscussion/conflictingDiscussion.html");
                                });
                        }
                        
                        else $("#link").attr("../src/unavailable/unavailable.html");
                    }
        
                    if (role == 4) {
                        if (phase == 0) $("#link").attr("../src/ConferenceScreens/changeDate.html");
                        else if (phase == 2) $("#link").attr("../src/AssignToReviewerScreen/assignToReviewer.html");
                        
                        else $("#link").attr( "../src/unavailable/unavailable.html");
                    }
        
                    if (role == 5) {
                        if (phase == 0)
                        callAlert("Pick Pc members or update conference?", "Pick members", "Update Conference", 
                        function () {
                            $("#link").attr("../src/PcMemberPickScreen/pcMemberPickScreen.html");
                        },
                        function () {
                            $("#link").attr("../src/ConferenceScreens/CreateConference.html")
                        });
                        else if (phase == 1) $("#link").attr("../src/ConferenceScreens/CreateConference.html"); // change accounts.
                        else if (phase == 2) $("#link").attr("../src/ConferenceScreens/CreateConference.html");
                        else if (phase == 3)
                        callAlert("Create sections or update conference?", "Create Sections", "Update Conference", 
                        function () {
                            $("#link").attr("../src/CreateSection/createSection.html");
                        },
                        function () {
                            $("#link").attr("../src/ConferenceScreens/CreateConference.html")
                        });
                        else $("#link").attr("../src/unavailable/unavailable.html");
                    }
                },
                error: function (data) {
                    if (data.responseJSON.error == "Not logged in!")
                        $("#link").attr("href", "../src/LoginScreen/loginScreen.html")
                }
            });
            
        }
    });
});