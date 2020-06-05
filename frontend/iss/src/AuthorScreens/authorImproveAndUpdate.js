import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

$(document).ready(function () {
    let phase = localStorage.getItem("phase");
    if (phase == 1) {
        document.getElementById("improveProposal").style.visibility = "hidden";
    } else {
        if (phase == 2) {
            document.getElementById("addProposal").style.visibility = "hidden";
            document.getElementById("improveProposal").style.visibility = "hidden";
            document.getElementById("uploadProposal").style.visibility = "hidden";
        }
        if (phase == 3) {
            document.getElementById("improveProposal").style.visibility = "visible";
            document.getElementById("uploadProposal").innerHTML = "Upload Doc for presentation";
            document.getElementById("addProposal").style.visibility = "hidden";
        }
    }


    let data = []
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/section/details",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            if (dataPapers.statusText == "OK") {
                console.log(dataPapers.responseJSON);
                if (dataPapers.responseJSON.length == 0) {
                    document.getElementById("proposalTitle").value = "Sorry, Your paper was not selected...";
                    document.getElementById("improveProposal").style.visibility = "hidden";
                    document.getElementById("uploadProposal").style.visibility = "hidden";
                } else {
                    data = dataPapers.responseJSON;
                    addItem(data.name, data.paperId);
                    addSectionDetails(dataPapers.responseJSON);

                }
            } else {
                alert("Sorry, you were not chosen to present in any section...");
                var ul = document.getElementById("authorProposals");
                var candidate = document.getElementById("proposalTitle");
                var li = document.createElement("li");
                li.setAttribute('id', -1);
                li.setAttribute('class', "proposalTitle");
                li.setAttribute('style', "font-size: x-large; text-align:center; color: rgb(255, 214, 29); font-family: 'Arial Black', Gadget, sans-serif; list-style: none;");
                li.appendChild(document.createTextNode("You were not chosen to present in any section"));
                ul.appendChild(li);
                    document.getElementById("improveProposal").style.visibility = "hidden";
                    document.getElementById("uploadProposal").style.visibility = "hidden";
            }
        }
    });
    function addSectionDetails(data) {
        let toWrite = " Name of the Section : " + data.name + "<br>" +
            "Start Date: " + data.startTime +
            "<br>End Date: " + data.endTime +
            "<br>Section Chair: " + data.sessionChair +
            "<br>Room name : " + data.roomName + "<br><br>";

        document.getElementById("SectionDetails").innerHTML = toWrite;
    }

    function addItem(proposalTitle, id) {
        localStorage.setItem("selectedProposal", proposalTitle);
        keepInStore(id);
        var ul = document.getElementById("authorProposals");
        var candidate = document.getElementById("proposalTitle");
        var li = document.createElement("li");
        li.setAttribute('id', id);
        li.setAttribute('class', "proposalTitle");
        li.setAttribute('style', "font-size: x-large;  color: blue; font-family: 'Arial Black', Gadget, sans-serif; list-style: none; margin-left: -8%;");
        li.appendChild(document.createTextNode(proposalTitle));
        ul.appendChild(li);

        li.addEventListener('click', function (event) {
            try {
                document.getElementById(previousID).style = "font-size: x-large; text-align:center; color: blue; font-family: 'Arial Black', Gadget, sans-serif; list-style: none;margin-left: -8%;"
            } catch {
                console.log("nothing to undo");
            }
            if (event.target && event.target.getAttribute("class") == "proposalTitle") {
                document.getElementById(event.target.id).style = "font-size: x-large; text-align:center; color: green   ; font-family: 'Arial Black', Gadget, sans-serif; list-style: none;margin-left: -8%;"
                keepInStore(event.target.id);
                previousID = event.target.id;
            }
        })
    }

    var previousID = -1;

    $("#addProposal").click(function () {
        location.href = "./authorSubmit.html";
    });
    $("#improveProposal").click(function () {
        location.href = "./authorImprove.html";
    });
    $("#uploadProposal").click(function () {
        location.href = "./authorUpload.html";
    });
    function keepInStore(title) {
        window.localStorage.setItem("selectedProposalId", title);
    }
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