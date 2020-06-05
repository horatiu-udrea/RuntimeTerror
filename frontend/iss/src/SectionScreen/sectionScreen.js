import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

$(document).ready(function () {
    let reviewers = [];
    let paperDetails = [];

    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/section",
        dataType: "json",
        complete: function (data) { //getPapers
            if (data.statusText == "OK") {
                let inSelect = [];

                data.responseJSON.forEach(element => {
                    inSelect.push({ name: element.name, id: element.sectionId });
                   
                    paperDetails.push({
                        sectionId: element.sectionId, value: ("Name " + element.name +
                            "<br>     Start time " + element.startTime +
                            "<br>     End time " + element.endTime +
                            "<br>     Room Name " + element.roomName +
                            
                            + "<br>")
                    })
                });
                fillSections();
            }
        }
    })
    function fillSections() {
        let spanText = "";
        paperDetails.forEach(element => {
            spanText += "<input type='checkbox' id='" + element.sectionId + "'class='reviewersCheckers' name ='" + element.value + "'> <label for='" + element.value + "'>" + element.value + "</label><br>";
        });
        document.getElementById("sections").innerHTML = spanText;
    }

    $("#assign").click(function () {
        let checkedReviewers = [];
        let arr = document.getElementsByClassName("reviewersCheckers");
        for (let i = 0; i < arr.length; i += 1) {
            if (arr[i].checked) {
                checkedReviewers.push(arr[i].id);
            }
        }
        console.log(checkedReviewers)
        checkedReviewers.forEach(element => {
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: HOST + PORT + "/section/choice",
                data: JSON.stringify({ sectionId: element }),
                dataType: "json",
                complete: function (data) {
                    if (data.statusText == "OK") {
                        localStorage.setItem("choosed", localStorage.getItem("user"));
                        window.location = "../../dist/index.html";
                    } else {
                        console.log("fail");
                    }
                }
            
            })
    });
    // console.log(checkedReviewers)
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
})
});