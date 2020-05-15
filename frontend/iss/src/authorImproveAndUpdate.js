import { Globals } from "./Globals"

$(document).ready(function () {

    function addItem(proposalTitle) {
        var ul = document.getElementById("authorProposals");
        var candidate = document.getElementById("proposalTitle");
        var li = document.createElement("li");
        li.setAttribute('id', proposalTitle);
        li.appendChild(document.createTextNode(proposalTitle));
        ul.appendChild(li);
    }

    function addAllProposals(data) {
        for (let i in data) {
            addItem(i.name);
        }

    }

    $.get(Globals.host + Globals.portURL + "/paper", function (data) {
        addAllProposals(data);
    });


});