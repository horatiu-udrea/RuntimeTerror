import { HOST, PORT } from "../Globuls.js"
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
                else if (valueOfElement.bidResult == 1) bidColor = "green"


                valueOfElement = valueOfElement.paper;
                code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class='title'><div class='bid' style='background-color:"+bidColor+"'></div><div class='titleContainer'>"+valueOfElement.name+"</div></div><div class='content' style = 'display:none'>"+valueOfElement.abstract+"<button class='upButton'>UP</button> <button class='middleButton'>MIDDLE</button> <button class='downButton'>DOWN</button> </div>"+"</li>";
                $("#list").html(code);
            });
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

    if(bid == 3) $(listItem).find("div.bid").css("background-color", "red"); 
    else if(bid == 2) $(listItem).find("div.bid").css("background-color", "yellow");
    else $(listItem).find("div.bid").css("background-color", "green"); 
}

$(document).ready(function () {
    
    refreshList();

    $("ul").on("click", "li", function (e) { 
        e.preventDefault();
        
        let content = $(this).children(':nth-child(2)'); 
        if(content.css("display") == "none")
            content.css("display", "block");
        else
            content.css("display", "none");
    });
    $("ul").on("click", "div.content", function (e) { 
        e.stopPropagation();
    });

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

