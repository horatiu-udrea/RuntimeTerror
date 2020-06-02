import { HOST, PORT } from "../Globuls.js"

// TODO Execute this in every page before executing ajax calls in order to send cookies to server
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

function stopAllAnimations() {
    $("#loginButtonStripe").stop();
    $("#textboxesStripe").stop();
    $("#signUpButtonStripe").stop();
}

function resetToDefaultAnimated(d1, d2, d3) {
    $("#loginButtonStripe").animate({
        height: d1
    });
    $("#textboxesStripe").animate({
        height: d2
    });
    $("#signUpButtonStripe").animate({
        height: d3
    });
}

function callAlert(message, messageYes, messageNo, actionYes, actionNo) {
    $("#alertMessage").text(message);
    $("#alertButtonYes").click(actionYes);
    $("#alertButtonNo").click(actionNo);
    $("#alertButtonYes").text(messageYes);
    $("#alertButtonNo").text(messageNo);
    $("#alertBackground").toggle();
}

$(document).ready(function () {

    let defaultLoginStripeHeight = $("#loginButtonStripe").height();
    let defaultSignUpStripeHeight = $("#signUpButtonStripe").height();
    let defaultTextBoxesHeight = $("#textboxesStripe").height();
    $("#alertBackground").toggle();
    $("#alertBackground").click(function () {
        $("#alertBackground").toggle();
    })


    //Login Button Animations
    $("#loginButtonStripe").mouseenter(function () {
        stopAllAnimations();
        $("#loginButtonStripe").animate({
            height: defaultLoginStripeHeight + 40
        });
        $("#textboxesStripe").animate({
            height: defaultTextBoxesHeight - 20
        });
        $("#signUpButtonStripe").animate({
            height: defaultSignUpStripeHeight
        });
    })
    $("#loginButtonStripe").mouseleave(function () {
        stopAllAnimations();
        resetToDefaultAnimated(defaultLoginStripeHeight, defaultTextBoxesHeight, defaultSignUpStripeHeight);
    })

    //Signup Button Animations
    $("#signUpButtonStripe").mouseenter(function () {
        stopAllAnimations();
        $("#signUpButtonStripe").animate({
            height: defaultSignUpStripeHeight + 40
        });
        $("#loginButtonStripe").animate({
            height: defaultLoginStripeHeight - 20
        });
        $("#textboxesStripe").animate({
            height: defaultTextBoxesHeight
        });
    })

    $("#signUpButtonStripe").mouseleave(function () {
        stopAllAnimations();
        resetToDefaultAnimated(defaultLoginStripeHeight, defaultTextBoxesHeight, defaultSignUpStripeHeight);
    })

    //Login Button Click
    $("#loginButtonStripe").click(function () {
        let username = $("#usernameInput").val();
        let password = $("#passwordInput").val();
        // const User =

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/authentication/login",
            data: JSON.stringify({ username: username, password: password }),
            dataType: "json",

            complete: function (data, statusText) {
                console.log(data, statusText)
                if (data.statusText == "OK") {
                    localStorage.setItem("user", username);
                    donePost(data);
                } else {
                    failPost(data);
                }
            }
        })
        function failPost(error) {
            $("input[type=text]").val = error.responseText;
            console.log(error);
            if (error.statusText == "OK") {
                donePost(error);
            }
            alert("failed");
        };
        function donePost(data) {
            // alert("done");
            let role, phase, bidDate;
            let today = new Date();

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: HOST + PORT + "/authentication",
                dataType: "json",

                complete: function (dataUser, statusText) {
                    console.log(dataUser)
                    if (dataUser.statusText == "OK" || dataUser.statusText == "success") {
                        role = dataUser.responseJSON.type
                        console.log(dataUser.responseJSON.type)
                        $.ajax({
                            type: "GET",
                            contentType: "application/json",
                            url: HOST + PORT + "/conference",
                            dataType: "json",

                            complete: function (dataConference, statusText) {
                                if (dataConference.statusText == "OK") {
                                    let split = dataConference.responseJSON.biddingDeadline.split("/");
                                    bidDate = new Date(split[2] + "-" + split[1] + "-" + split[0]);
                                    phase = dataConference.responseJSON.currentPhase;
                                    console.log(role, phase);

                                    if (role == 0) {
                                        if (phase == 1) window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                        else if (phase == 2) window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                        else if (phase == 3) window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                        else window.location.href = "Nothing-to-do-here page....";
                                    }

                                    if (role == 1) {
                                        if (phase == 1) window.location = "../AuthorScreens/authorSubmit.html";
                                        else if (phase == 2) window.location = "../AuthorScreens/authorImproveAndUpdate.html";
                                        else if (phase == 3) window.location = "../AuthorScreens/authorImproveAndUpdate.html";
                                        else window.location = "Nothing-to-do-here page....";
                                    }

                                    if (role == 2) {
                                        callAlert("What would you like to log in as?", "Author", "PcMember", function () {
                                            if (phase == 1) window.location = "../AuthorScreens/authorSubmit.html";
                                            else if (phase == 2) window.location = "../AuthorScreens/authorImproveAndUpdate.html";
                                            else if (phase == 3) window.location = "../AuthorScreens/authorImproveAndUpdate.html";
                                            else window.location = "Nothing-to-do-here page....";
                                        },
                                            function () {
                                                if (phase == 2) {
                                                    if (today < bidDate) window.location.href = "../BiddingScreen/biddingScreen.html";
                                                    else window.location.href = "../ReviewingScreen/reviewingScreen.html";
                                                }
                                                else if (phase == 3 && localStorage.getItem("choosed")!== localStorage.getItem("user")) window.location.href = "../SectionScreen/sectionScreen.html";
                                                else window.location.href = "Nothing-to-do-here page....";
                                            });
                                    }

                                    if (role == 3) {
                                        if (phase == 0) window.location.href = "../ConferenceScreens/changeDate.html";
                                        else if (phase == 2) {
                                            callAlert("Assign papers to reviewers, or deal with conflicting papers?", "Assign", "Conflicting", function () {
                                                window.location.href = "../AssignToReviewerScreen/assignToReviewer.html";
                                            },
                                                function () {
                                                    window.location.href = "../ConflictingDiscussion/conflictingDiscussion.html";
                                                });
                                        }
                                        else if (phase == 3 && localStorage.getItem("choosed")!== localStorage.getItem("user")) window.location.href = "../SectionScreen/sectionScreen.html";
                                        else window.location.href = "Nothing-to-do-here page....";
                                    } //TODO Pune conflicting discussion la co-chair si chair in phase 2

                                    if (role == 4) {
                                        if (phase == 0) window.location.href = "../ConferenceScreens/changeDate.html";
                                        else if (phase == 2) window.location.href = "../AssignToReviewerScreen/assignToReviewer.html";
                                        else if (phase == 3 && localStorage.getItem("choosed")!== localStorage.getItem("user")) window.location.href = "../SectionScreen/sectionScreen.html";
                                        else window.location.href = "Nothing-to-do-here page....";
                                    }

                                    if (role == 5) {
                                        if (phase == 0) window.location.href = "../PcMemberPickScreen/pcMemberPickScreen.html";
                                        else if (phase == 1) window.location.href = "-";
                                        else if (phase == 2) window.location.href = "../AssignToReviewerScreen/assignToReviewer.html";
                                        else if (phase == 3) window.location.href = "../CreateSection/createSection.html";
                                        else window.location.href = "";
                                    }

                                } else {
                                    alert("Can not gat conference details");
                                }
                            },

                        });
                    }
                }

                // $.get(HOST + PORT + "/authentication", function (data) {
                //     role = data.type
                //     bidDate = Date.parse(data.biddingDeadline)
                // });
                // console.log(role);

                // $.get(HOST + PORT + "/conference", function (data) {
                //     phase = data.phase
                // });
                // console.log(role, bidDate, phase);

            });

        }
    });
})