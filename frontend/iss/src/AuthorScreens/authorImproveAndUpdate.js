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
            document.getElementById("improveProposal").style.visibility = "hidden";
            document.getElementById("uploadProposal").value = "Upload Doc for presentation"
        }
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/conference",
        dataType: "json",

        complete: function (dataConference, statusText) {
            if (dataConference.statusText == "OK") {
                console.log(dataConference)
                let phase = dataConference.responseJSON.currentPhase;
                if (phase == 2) {
                    // TODO :  enable or diable buttons by phase
                }
            } else {

            }
        }
    });
    let data = []
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            if (dataPapers.statusText == "OK") {
                if (dataPapers.responseJSON.length == 0) {
                    document.getElementById("proposalTitle").value = "There are no papers to be improved or updated";
                    document.getElementById("improveProposal").style.visibility = "hidden";
                    document.getElementById("uploadProposal").style.visibility = "hidden";
                } else {
                    data = dataPapers.responseJSON;
                    addAllProposals(data);

                }
            } else {
                alert("can not get papers for this author");
            }
        }
    });

    function addItem(proposalTitle, id) {
        var ul = document.getElementById("authorProposals");
        var candidate = document.getElementById("proposalTitle");
        var li = document.createElement("li");
        li.setAttribute('id', id);
        li.setAttribute('class', "proposalTitle");
        li.appendChild(document.createTextNode(proposalTitle));
        ul.appendChild(li);
    }

    function addAllProposals(data) {
        for (let i in data) {
            console.log(data[i]);
            addItem(data[i].name, data[i].paperId);
        }

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
        window.localStorage.setItem("selectedProposal", title);
    }
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
    document.addEventListener('click', function (event) {
        try {
            document.getElementById(previousID).style = "font-family:'Times New Roman'; font-size:12px; color:black"
        } catch {
            console.log("nothing to undo");
        }
        if (event.target && event.target.getAttribute("class") == "proposalTitle") {
            document.getElementById(event.target.id).style = "font-family:'Courier New'; font-size:30px; color:blue"
            keepInStore(event.target.id);
            previousID = event.target.id;
        }
    })
});