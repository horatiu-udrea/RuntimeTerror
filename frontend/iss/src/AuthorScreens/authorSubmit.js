import {PORT,HOST} from "../Globuls.js"

$(document).ready(function () {   
    $("#submitProposal").click(function(){
        
        $.post(HOST + PORT + "/paper", {
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
});