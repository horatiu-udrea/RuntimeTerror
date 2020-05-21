
$(document).ready(function () {

    function addItem(proposalTitle) {
        var ul = document.getElementById("authorProposals");
        var candidate = document.getElementById("proposalTitle");
        var li = document.createElement("li");
        li.setAttribute('id', proposalTitle);
        li.setAttribute('class', "proposalTitle");
        li.appendChild(document.createTextNode(proposalTitle));
        ul.appendChild(li);
    }

    function addAllProposals(data) {
        for (let i in data) {
            console.log(data[i].name);
            addItem(data[i].name);
        }

    }
    var data = [{name: "hbd"}, {name: "aDVSHGD"}, {name:"dzf"}];
    var previousID = -1;
    addAllProposals(data);
    // $.get(Globals.host + Globals.portURL + "/paper", function (data) {
    //     addAllProposals(data);
    // });
    $("#improveProposal").click(function () {
        location.href = "./authorImprove.html";
    });
    $("#uploadProposal").click(function () {
        location.href = "./authorUpload.html";
    });
    function keepInStore(title){
        window.localStorage.setItem("selectedProposal", title);
    }

    document.addEventListener('click', function(event) {
        try{
        document.getElementById(previousID).style = "font-family:'Times New Roman'; font-size:12px; color:black"
        } catch {
            console.log("nothing to undo");
        }
        if(event.target && event.target.getAttribute("class") == "proposalTitle") {
            document.getElementById(event.target.id).style = "font-family:'Courier New'; font-size:30px; color:blue"
            keepInStore(event.target.id);
            previousID = event.target.id;
        }
    })
});