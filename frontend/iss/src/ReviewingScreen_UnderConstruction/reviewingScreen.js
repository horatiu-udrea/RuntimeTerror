import { HOST, PORT } from "../Globuls.js"

function fillList(){
    code = ""
    for(i = 0; i < 40; i ++)
        code += "<li value = '"+i+"'>"+"<div class='title'>EWRRT"+i+"</div><div class='content' style = 'display:none'><div><iframe src='http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf' width='50%' height='300px'></iframe></div><div><textarea rows='10' cols='50'></textarea></div><div><button class='upButton'>UP</button><label>Grade: </label>            <select class='gradePuicker' name='Grade'><option value = '1'>1</option><option value = '2'>2</option><option value = '3'>3</option><option value = '4'>4</option><option value = '5'>5</option><option value = '6'>6</option><option value = '7'>7</option></select></div></div>"+"</li>";
    $("#list").html(code);
}

$(document).ready(function () {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/decision",
        dataType: "json",

        complete: function(data){
            code = ""
            $.each(data, function (indexInArray, valueOfElement) { 
                code += "<li value = '"+valueOfElement.paperId+"'>"+"<div class='title'>EWRRT"+i+"</div><div class='content' style = 'display:none'><div class='pdfDisplay'><iframe src='http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf' width='50%' height='300px'></iframe></div><div class ='textInput'><textarea class='recommendationInput' rows='10' cols='50'></textarea></div><div class='buttons'><button class='upButton'>UP</button><label>Grade: </label><select class='gradePuicker' name='Grade'><option value = '1'>1</option><option value = '2'>2</option><option value = '3'>3</option><option value = '4'>4</option><option value = '5'>5</option><option value = '6'>6</option><option value = '7'>7</option></select></div></div>"+"</li>";
                $("#list").html(code);
            });
        }
    }); //TODO add a display for previous review comments (after paper is reviewed?)
    

    $("li").click(function (e) { 
        e.preventDefault();
        
        let content = $(this).children(':nth-child(2)'); 
        if(content.css("display") == "none")
            content.css("display", "block");
        else
            content.css("display", "none");
    });

    $(".content").click(function (e) { e.stopPropagation(); });

    $(".upButton").click(function (e) { 
        e.preventDefault();
        
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: HOST + PORT + "/paper/review",
            dataType: "json",
            data: JSON.stringify({paperId= $(this).parent().parent().parent().val(), recommendation= $(this).parent().siblings('.textInput').children('.recommendationInput').val(), qualifier= $(this).siblings('.gradePicker').val()}),

            complete: function(data){}
        });

        //I did not test this yet....
        e.stopPropagation();
    });
});

