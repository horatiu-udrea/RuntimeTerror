import { HOST, PORT } from "../Globuls.js"
$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

$(document).ready(function () {

    let data = []
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/assign",
        dataType: "json",

        complete: function (dataPapers, statusText) {
            if (dataPapers.statusText == "OK") {
                if (dataPapers.responseJSON.length == 0) {
                    alert("no papers to be shown");
                } else {
                    dataPapers.responseJSON.forEach(element => {
                        if (element.status == 4) {
                            data.push(element);
                        }
                    });
                    if (data.length == 0) {
                        alert("there are no conflicting papers");
                    } else {
                        console.log(data);
                        addAllProposals(data);
                    }

                }
            } else {
                alert("can not get papers");
            }
        }
    });

// dont show conflicts, show all - let decision to be accept/ reject/ conflict

    function addDetailOnPaper(id) {

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: HOST + PORT + "/paper/assign",
            dataType: "json",

            complete: function (dataPapers, statusText) {
                if (dataPapers.statusText == "OK") {
                    dataPapers.responseJSON.forEach(element => {
                        if (element.paperId == id) {

                            addDetailItem(element);
                        }
                    });
                } else {
                    alert("can not get papers details");
                }
            }
        });
    }

    function addDetailItem(paper) {
        let name = paper.name;
        let field = paper.field;
        let keywords = paper.keywords;
        let topics = paper.topics;
        let abstract = paper.abstract;
        let authors = paper.authors;
        let formatedAuthors = "";

        authors.forEach(element => {
            formatedAuthors += "Author " + element.name + " (" + element.email + ") \n";
        });
        let toBePrinted = "Name of the proposal : " + name + "<br>" +
            "Fileds of interest: " + field +
            "<br>Keywords of the presentation: " + keywords +
            "<br>Topics on this proposal : " + topics +
            "<br>Description of the paper: " + abstract +
            "<br> Authors: " + formatedAuthors + "<br><br>";

        document.getElementById("paperDetails").innerHTML = toBePrinted;

    }

    function addItem(proposalTitle, id) {
        var ul = document.getElementById("conflictingDiscussion");
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
    $("#accept").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/paper/decision",
            dataType: "json",
            data: JSON.stringify({ paperId: localStorage.getItem("selectedProposal"), status: 2 }),
            complete: function (dataPapers, statusText) {
                if (dataPapers.statusText == "OK") {
                    location.reload();
                    alert("this paper was accepted");
                }
                else {
                    alert("you can not decide on this one");
                }
            }
        });
    });
    $("#reject").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/paper/decision",
            dataType: "json",
            data: JSON.stringify({ paperId: localStorage.getItem("selectedProposal"), status: 3 }),
            complete: function (dataPapers, statusText) {
                if (dataPapers.statusText == "OK") {
                    location.reload();
                    alert("this paper was rejected");
                }
                else {
                    alert("you can not decide on this one");
                }
            }
        });
    });
    $("#conflict").click(function () {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/paper/decision",
            dataType: "json",
            data: JSON.stringify({ paperId: localStorage.getItem("selectedProposal"), status: 4 }),
            complete: function (dataPapers, statusText) {
                if (dataPapers.statusText == "OK") {
                    location.reload();
                    alert("this paper was rejected");
                }
                else {
                    alert("you can not decide on this one");
                }
            }
        });
    });
    let previousID = -1;
    function keepInStore(title) {
        window.localStorage.setItem("selectedProposal", title);
    }
    document.addEventListener('click', function (event) {
        try {
            document.getElementById(previousID).style = "font-family:'Times New Roman'; color:black; text-decoration: none;"
            document.getElementById("accept").style.visibility = "hidden";
            document.getElementById("reject").style.visibility = "hidden";
            document.getElementById("conflict").style.visibility = "hidden";
            document.getElementById("deets").style.visibility="hidden";
            document.getElementById("paperDetails").innerHTML = "";
        } catch {
            console.log("nothing to undo");
            document.getElementById("accept").style.visibility = "hidden";
            document.getElementById("reject").style.visibility = "hidden";
            document.getElementById("conflict").style.visibility = "hidden";
            document.getElementById("deets").style.visibility="hidden";
            document.getElementById("paperDetails").innerHTML = "";
        }
        if (event.target && event.target.getAttribute("class") == "proposalTitle") {
            document.getElementById("deets").style.visibility="visible";
            document.getElementById(event.target.id).style = "font-family:'Courier New'; color:blue; text-decoration: underline;"
            document.getElementById("accept").style.visibility = "visible";
            document.getElementById("reject").style.visibility = "visible";
            document.getElementById("conflict").style.visibility = "visible";
            keepInStore(event.target.id);
            addDetailOnPaper(event.target.id);
            previousID = event.target.id;
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
});