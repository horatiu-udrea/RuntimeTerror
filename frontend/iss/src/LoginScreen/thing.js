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
        resetToDefaultAnimated(defaultLoginStripeHeight, defaultTextBoxesHeight,defaultSignUpStripeHeight);
    })

    //Login Button Click
    $("#loginButtonStripe").click(function(){
        /*
        http://localhost:4444
        */
        $.post("http://localhost:8080/authentication/login", {username: $("#usernameInput").val(), password: $("#passwordInput").val()})
        .done(function(){
            //Nu stiu exact aici cum vine faza, dar for now...
            // store in local storage the user info like, username, email, phase, role
            window.location = "ADD HOME LINK HERE";
            
        })
        .fail(function(error){
            $("input[type=text]").val = error.responseText;
            // could be an alert thing
        });
    });

    /*
    Asta e un exemplu de sintaxa. Imi trebuie mie, ca-s prost.

    $(".clickableStripe").click(function() { 
        $(this).animate({
            height: "-=10",
        }, 200);
        $(this).animate({
            height: "+=10",
        },250);
    });
    */
})