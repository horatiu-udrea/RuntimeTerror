import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

function refreshList() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/bid",
        dataType: "json",
    
        complete: function(data){
            let code = "";
            $.each(data.responseJSON, function (indexInArray, valueOfElement) { 
                let bidColor = "yellow";
                if(valueOfElement.bidResult == 3) bidColor = "red"
                else if (valueOfElement.bidResult == 1) bidColor = "lime"

                valueOfElement = valueOfElement.paper;
                code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class=container><div class='banner' style='--glowColor:"+bidColor+"' ><h2 style='position:absolute; top:35%; left:50%; transform: translate(-50%, -50%);'>"+valueOfElement.name+"</h2></div><div class='content'><p class='text'>"+valueOfElement.abstract+" </p><div class='buttonContainer'><button class='button upButton'>Yes</button><button class='button middleButton'>Maybe</button><button class='button downButton'>No</button></div></div></div>" 
            });
            
            $("#list").html(code);
        }
    });
}
 
function bidOnPaper(listItem, bid) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: HOST + PORT + "/paper/bid",
        dataType: "json",
        data: JSON.stringify({paperId: listItem.value, bidResult: bid}),
    });

    if(bid == 3) $(listItem).find("div.banner").css("cssText","--glowColor: red"); 
    else if(bid == 2) $(listItem).find("div.banner").css("cssText","--glowColor: yellow");
    else $(listItem).find("div.banner").css("cssText","--glowColor: lime");
}

$(document).ready(function () {
    
    refreshList();

    $("ul").on("click", ".upButton", function (e) { 
        e.preventDefault();

        bidOnPaper($(this).parents("li")[0], 1);
    });

    $("ul").on("click", ".middleButton", function (e) { 
        e.preventDefault();
        
        bidOnPaper($(this).parents("li")[0], 2);
    });
    
    $("ul").on("click", ".downButton", function (e) { 
        e.preventDefault();
        
        bidOnPaper($(this).parents("li")[0], 3);
    });
});

