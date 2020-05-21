import { HOST, PORT } from "../Globuls.js"

function stopAllAnimations () {
    $("#loginButtonStripe").stop();
    $("#textboxesStripe").stop();
    $("#signUpButtonStripe").stop();
}

function resetToDefaultAnimated(d1, d2, d3){
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
    $("#loginButtonStripe").mouseenter(function(){
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
    $("#loginButtonStripe").mouseleave(function(){
        stopAllAnimations();
        resetToDefaultAnimated(defaultLoginStripeHeight, defaultTextBoxesHeight,defaultSignUpStripeHeight);
    })

    //Signup Button Animations
    $("#signUpButtonStripe").mouseenter(function(){
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

    $("#signUpButtonStripe").mouseleave(function(){
        stopAllAnimations();
        resetToDefaultAnimated(defaultLoginStripeHeight, defaultTextBoxesHeight, defaultSignUpStripeHeight);
    })

    //Login Button Click
    $("#loginButtonStripe").click(function(){
        /*
        http://localhost:4444
        */
        $.post(HOST + PORT + "/authentication/login", {username: $("#usernameInput").val(), password: $("#passwordInput").val()})
        .done(function(){
        
            let role, phase;

            $.get(HOST + PORT + "/authentication", function(data){
                role = data.type
            });

            $.get(HOST + PORT + "/conference", function(data){
                phase = data.phase
            });
            
            switch (role| phase){
                case 0 | 1:
                    window.location = "ADD listener in phase 1 LINK HERE";
                break;
                case 0 | 2:
                    window.location = "ADD listener in phase 2 LINK HERE";
                break;
                case 0 | 3:
                    window.location = "ADD listener in phase 3 LINK HERE";
                break;

                case 1 | 1:
                    window.location = "authorSubmit.html";
                break;
                case 1 | 2:
                    window.location = "ADD author in phase 2 LINK HERE";    
                break;
                case 1 | 3:
                    window.location = "ADD author in phase 3 LINK HERE";    
                break;
            
                case 2 | 1:
                    window.location = "ADD pcmember in phase 1 LINK HERE";
                break;
                case 2 | 2:
                    window.location = "ADD pcmember in phase 2 LINK HERE";
                break;
                case 2 | 3:
                    window.location = "ADD pcmember in phase 3 LINK HERE";
                break;
                
                case 3 | 1:
                    window.location = "ADD cochair in phase 1 LINK HERE";
                break;
                case 3 | 2:
                    window.location = "ADD cochair in phase 2 LINK HERE";
                break;
                case 3 | 3:
                    window.location = "ADD cochair in phase 3 LINK HERE";
                break;

                case 4 | 1:
                    window.location = "ADD chair in phase 1 LINK HERE";
                break;
                case 4 | 2:
                    window.location = "ADD chair in phase 2 LINK HERE";
                break;
                case 4 | 3:
                    window.location = "ADD chair in phase 3 LINK HERE";
                break;

                case 5 | 1:
                    window.location = "ADD scmember in phase 1 LINK HERE";
                break;
                case 5 | 2:
                    window.location = "ADD scmember in phase 2 LINK HERE";
                break;
                case 5 | 3:
                    window.location = "ADD scmember in phase 3 LINK HERE";
                break;
            }
        })
        .fail(function(error){
            $("input[type=text]").val = error.responseText;
        });
    });
})