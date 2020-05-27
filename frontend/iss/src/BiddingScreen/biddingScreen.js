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
                valueOfElement = valueOfElement.paper;
                code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class='title'>"+valueOfElement.name+"</div><div class='content' style = 'display:none'>"+valueOfElement.abstract+"<button class='upButton'>UP</button> <button class='middleButton'>MIDDLE</button> <button class='downButton'>DOWN</button> </div>"+"</li>";
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
        data: JSON.stringify({paperId: listItem.paperId, bidResult: bid}),

        complete: function(data){listItem.remove();}
    });
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

        bidOnPaper($(this).parents("li"), 1);
    });

    $("ul").on("click", ".middleButton", function (e) { 
        e.preventDefault();
        
        bidOnPaper($(this).parents("li"), 2);
    });
    
    $("ul").on("click", ".downButton", function (e) { 
        e.preventDefault();
        
        bidOnPaper($(this).parents("li"), 3);
    });
});

