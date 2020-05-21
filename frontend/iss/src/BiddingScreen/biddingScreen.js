import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {
    $.get(HOST + PORT + "paper/bid", function (data) {
        code = ""
        $.each(data, function (indexInArray, valueOfElement) { 
            code += "<li value = '"+indexInArray+"'>"+"<div class='title'>"+valueOfElement.name+"</div><div class='content' style = 'display:none'>"+valueOfElement.abstract+"<button class='upButton'>UP</button> <button class='middleButton'>MIDDLE</button> <button class='downButton'>DOWN</button> </div>"+"</li>";
            $("#list").html(code);
        });
            
    });

    $("li").click(function (e) { 
        e.preventDefault();
        
        let content = $(this).children(':nth-child(2)'); 
        if(content.css("display") == "none")
            content.css("display", "block");
        else
            content.css("display", "none");
    });

    $(".upButton").click(function (e) { 
        e.preventDefault();
        
        $.put(HOST + PORT + "paper/bid", {paperId = $(this).parent().parent().val(), bidResult = 1}, function(){
            $(this).parent().parent().css("display","none");
        });

        e.stopPropagation();
    });

    $(".middleButton").click(function (e) { 
        e.preventDefault();
        
        $.put(HOST + PORT + "paper/bid", {paperId = $(this).parent().parent().val(), bidResult = 0}, function(){
            $(this).parent().parent().css("display","none");
        });

        e.stopPropagation();
    });

    
    $(".downButton").click(function (e) { 
        e.preventDefault();
        
        $.put(HOST + PORT + "paper/bid", {paperId = $(this).parent().parent().val(), bidResult = -1}, function(){
            $(this).parent().parent().css("display","none");
        });

        e.stopPropagation();
    });
});

