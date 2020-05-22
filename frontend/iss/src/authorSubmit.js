import {Globals} from "./Globals"

$("#submitProposal").click(function(){
    
    $.post(Globals.host + Globals.portURL + "/paper", {
        name: $("#proposalName").val(),
        field: $("#proposalFiels").val(),
        keywords: $("#proposalKeywords").val(),
        topics: $("#proposalTopics").val(),
        authors: $("#proposalAuthors").val(),
        description: $('#proposalDescription').val()
    })
    .done(function(){
        //Nu stiu exact aici cum vine faza, dar for now...
        window.location = "ADD HOME LINK HERE";
    })
    .fail(function(error){
        $("input[type=text]").val = error.responseText;
    });
});