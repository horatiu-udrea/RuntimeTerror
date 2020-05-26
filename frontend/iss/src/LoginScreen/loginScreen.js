import { HOST, PORT } from "../Globuls.js"

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

$(document).ready(function () {
    let defaultLoginStripeHeight = $("#loginButtonStripe").height();
    let defaultSignUpStripeHeight = $("#signUpButtonStripe").height();
    let defaultTextBoxesHeight = $("#textboxesStripe").height();

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
        const username = $("#usernameInput").val();
        const password = $("#passwordInput").val();
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
                    if (dataUser.statusText == "OK") {
                        role = dataUser.responseJSON.type
                        console.log(dataUser.responseJSON.type)
                        $.ajax({
                            type: "GET",
                            contentType: "application/json",
                            url: HOST + PORT + "/conference",
                            dataType: "json",

                            complete: function (dataConference, statusText) {
                                if (dataConference.statusText == "OK") {
                                    bidDate = dataConference.responseJSON.biddingDeadline
                                    phase = dataConference.responseJSON.currentPhase;
                                    console.log(role, phase);
                                    window.location.assign("../AuthorScreens/authorSubmit.html");
                                    //  switch (role | phase) {
                                    //     case 0 | 1:
                                    //         window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                    //         break;
                                    //     case 0 | 2:
                                    //         window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                    //         break;
                                    //     case 0 | 3:
                                    //         window.location.assign("../BuyTicket_UnderConstruction/buyTicket.html");
                                    //         break;

                                    //     case 1 | 1:
                                    //         window.location.assign("../AuthorScreens/authorSubmit.html");
                                    //         break;
                                    //     case 1 | 2:
                                    //         window.location = "../AuthorScreens/authorUpload.html";  //TODO  what's upload and what's submit?
                                    //         break;
                                    //     case 1 | 3:
                                    //         window.location = "../AuthorScreens/authorImproveAndUpload.html";
                                    //         break;//TODO in phase 3 ar trebui sa aiba voie doar authori care sunt si speakeri. need to look into this. Also, cum afecteaza sectiunile chestia asta?

                                    //     //TODO Authors have a bit of a mess here... Because PcMembers can publish papers as well (with the exception of the chair and chair-juniors)
                                    //     //this might turn into a mess. Tired andu suggests that we add role to the login (as a drop-down list) and use it as a criteria for both login
                                    //     //if user has account, check role as well, if the role checks out all is good. That way we can give people the ability to go into author mode if they log i
                                    //     //with the role, and prevent people from loggin in with roles that weren't assigned to them. 21.05.2020

                                    //     case 2 | 2:
                                    //         if (today < bidDate)
                                    //             window.location = "../BiddingScreen/biddingScreen.html";
                                    //         else
                                    //             window.location = "../ReviewingScreen/reviewingScreen.html";
                                    //         break;
                                    //     case 2 | 3:
                                    //         window.location = "ADD pcmember in phase 3 LINK HERE";
                                    //         break;

                                    //     case 3 | 0:
                                    //         window.location = "../ConferenceScreens/changeDate.html";
                                    //         break;
                                    //     case 3 | 2:
                                    //         window.location = "../AssignToReviewerScreen/assignToReviewer.html";
                                    //         break;
                                    //     case 3 | 3:
                                    //         window.location = "ADD cochair in phase 3 LINK HERE";
                                    //         break;

                                    //     case 4 | 0:
                                    //         window.location = "../ConferenceScreens/changeDate.html";
                                    //         break;
                                    //     case 4 | 2:
                                    //         window.location = "../AssignToReviewerScreen/assignToReviewer.html";
                                    //         break;
                                    //     case 4 | 3:
                                    //         window.location = "ADD chair in phase 3 LINK HERE";
                                    //         break;

                                    //     case 5 | 0:
                                    //         window.location = "../PcMemberPickScreen/pcMemberPickScreen.html";
                                    //         break;
                                    //     case 5 | 1:
                                    //         window.location = "ADD scmember in phase 1 LINK HERE";
                                    //         break;
                                    //     case 5 | 2:
                                    //         window.location = "ADD scmember in phase 2 LINK HERE";
                                    //         break;
                                    //     case 5 | 3:
                                    //         window.location = "ADD scmember in phase 3 LINK HERE";
                                    //         break;
                                    //     default:
                                    //         window.location = "Nothing-to-do-here page....";
                                    //         break;
                                    // }

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