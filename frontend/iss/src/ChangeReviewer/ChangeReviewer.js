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

    function fillSelect(data) {
        let toSet = "";
        console.log(data);
        data.forEach(element => {
            toSet += "<option value=\"" + element.id + "\">" + element.name + "</option>"
        });;
        document.getElementById("papers").innerHTML += toSet;
    }
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/assign",
        dataType: "json",
        complete: function (data) { //getPapers
            if (data.statusText == "OK") {
                let inSelect = [];

                data.responseJSON.forEach(element => {
                    inSelect.push({ name: element.name, id: element.paperId });
                    let authors = () => {
                        let str = "";
                        element.authors.forEach(e => {
                            str += e.name + " " + e.email + "; ";
                        });
                        return str;
                    }
                    paperDetails.push({
                        paperId: element.paperId, value: ("<br>Name " + element.name +
                            "<br>Filed " + element.field +
                            "<br>keywords " + element.keywords +
                            "<br> topics " + element.topics +
                            "<br> abstract " + element.abstract +
                            "<br>status " + element.status +
                            "<br> authors " + authors()
                            + "<br>")
                    })
                });
                fillSelect(inSelect);
            }
        }
    })
    document.getElementById("papers").onchange = function () {
        let inDetails = "";
        paperDetails.forEach(element => {
            if (element.paperId == this.value) {
                inDetails = element.value;
            }
        });
        document.getElementById("paperDetails").innerHTML = inDetails;
    }
    function fillReviewers() {
        let spanText = "";
        reviewers.forEach(element => {
            spanText += "<input type='checkbox' id='" + element.rID + "'class='reviewersCheckers' name ='" + element.rName + "'> <label for='" + element.rName + "'>" + element.rName + "</label><br>";
        });
        document.getElementById("reviewers").innerHTML = spanText;
    }

    $.ajax({
        type: "GET",
        contentType: "/application/json",
        url: HOST + PORT + "/member",
        dataType: "json",

        complete: function (data) { //getPcMemebers
            console.log(data);
            if (data.statusText == "OK") {

                data.responseJSON.forEach(element => {
                    reviewers.push({ rID: element.userId, rName: element.name })
                });
            }
            console.log(reviewers)
            fillReviewers();
        }
    })
    $("#assign").click(function () {
        let checkedReviewers = [];
        let arr = document.getElementsByClassName("reviewersCheckers");
        for (let i = 0; i < arr.length; i += 1) {
            if (arr[i].checked) {
                checkedReviewers.push(arr[i].id);
            }
        }
        checkedReviewers.forEach(element => {
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: HOST + PORT + "/paper/assign",
                data: JSON.stringify({ userId: element, paperId: document.getElementById("papers").value }),
                dataType: "json",
                complete: function (data) {
                    if (data.statusText == "OK") {
                        console.log("ok");
                    } else {
                        console.log("fail");
                    }
                }

            })
        });
        // console.log(checkedReviewers)

    })
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