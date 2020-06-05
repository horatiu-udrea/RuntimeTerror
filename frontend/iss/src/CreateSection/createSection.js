import { HOST, PORT } from "../Globuls.js";
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});


$(document).ready(function () {

    let paperDetails = [];
    function fillChairs(data) {
        let toSet = "";
        console.log(data);
        data.forEach(element => {
            toSet += "<option value=\"" + element.id + "\">" + element.name + "</option>"
        });;
        document.getElementById("chairs").innerHTML += toSet;
    }
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/member",
        dataType: "json",
        complete: function (data) { //getPapers
            if (data.statusText == "OK") {
                let inSelect = [];

                data.responseJSON.forEach(element => {
                    inSelect.push({ name: element.name, id: element.userId });

                });
                fillChairs(inSelect);
            }
        }
    })
    $.ajax({

        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/remaining",
        dataType: "json",
        complete: function (data) {
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
                fillPapersAndDetails(inSelect);
            }
        }
    })
    function fillPapersAndDetails(inSelect) {
        let toSet = "";
        inSelect.forEach(element => {
            toSet += "<option value=\"" + element.id + "\">" + element.name + "</option>"
        });;
        document.getElementById("papers").innerHTML += toSet;

    }
    document.getElementById("papers").onchange = function () {
        let inDetails = "";
        paperDetails.forEach(element => {
            if (element.paperId == this.value) {
                inDetails = element.value;
            }
        });
        document.getElementById("paperDetails").innerHTML = inDetails;
        $.ajax({

            type: "GET",
            contentType: "application/json",
            url: HOST + PORT + "/paper/remaining/" + this.value,
            dataType: "json",
            complete: function (data) { //getPapers
                if (data.statusText == "OK") {
                    let inSelect = [];

                    data.responseJSON.forEach(element => {
                        inSelect.push({ name: element.name, id: element.id });


                    });
                    let toSet = "";
                    inSelect.forEach(element => {
                        toSet += "<option value=\"" + element.id + "\">" + element.name + "</option>"
                    });;
                    document.getElementById("author").innerHTML += toSet;
                }
            }
        })
    }

    $("#createConference").click(function (e) {
        let bidding = $("#conferenceStartDate").val().replace(/-/g, "/").split("/").reverse();
        let start = bidding[0] + "/" + bidding[1] + "/" + bidding[2];
        let endformat = $("#conferenceEndDate").val().replace(/-/g, "/").split("/").reverse();
        let end = endformat[0] + "/" + endformat[1] + "/" + endformat[2];
        // console.log(document.getElementById("author").value)
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/section",
            dataType: "json",
            data: JSON.stringify({
                sessionChairId: document.getElementById("chairs").value,
                userId: document.getElementById("author").value,
                name: $("#conferenceName").val(),
                startTime: start,
                endTime: end,
                roomName: document.getElementById("roomName").value,
                paperId: document.getElementById("papers").value,

            }),
            complete: function (data) {
                
                if (data.statusText == "OK") {
                    alert("successfully created Section");
                    window.location = "../../dist/index.html";
                } else {
                    alert("conference was not created");
                }

            }

        });

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
});