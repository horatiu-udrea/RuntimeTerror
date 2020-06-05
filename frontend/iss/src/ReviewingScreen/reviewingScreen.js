import { HOST, PORT } from "../Globuls.js"

$.ajaxSetup({
    crossDomain: true,
    xhrFields: {
        withCredentials: true
    }
});

var imageArray = ["#background4","#background1","#background2","#background3","#background4"];
var currentImage = 1;

function nextBackgroundImage(){
    currentImage += 1;

    $(imageArray[currentImage - 1]).fadeOut(1000);
    $(imageArray[currentImage]).fadeIn(1000);

    if(currentImage == imageArray.length -1){
        currentImage = 0;
    }
}

$(document).ready(function () {
    $("#background2").fadeOut(0);
    $("#background3").fadeOut(0);
    $("#background4").fadeOut(0);
    setInterval(nextBackgroundImage, 5000);


    var papers = [];
    var reviews = [];

    $.ajax({ 
        type: "GET",
        contentType: "application/json",
        url: HOST + PORT + "/paper/review",
        dataType: "json",

        complete: function(data){
            let code = "";
            $.each(data.responseJSON, function (indexInArray, valueOfElemen) {
                let value = valueOfElemen.paperDTO;
                papers.push(value);
                reviews.push(valueOfElemen);
                //Status 4 = conflicting
                    let li = document.createElement("li");
                    li.innerHTML = "<div class='title'>"+value.name+"</div><div class='content' height='0'><div class='pdfDisplay'><iframe src='"+HOST+PORT+"/document/"+value.documentPath+"' width='50%' scrolling='no'></iframe></div><div class ='textInput'><textarea class='recommendationInput' rows='10' cols='50'></textarea></div><div class='buttons'><button class='upButton'>Submit</button><select class='gradePuicker' name='Grade'><option value = 0 disabled selected>Grade</option><option value = 1>1</option><option value = 2>2</option><option value = 3>3</option><option value = 4>4</option><option value = 5>5</option><option value = 6>6</option><option value = 7>7</option></select></div></div>";
                    li.setAttribute("value", value.paperId)

                    if(valueOfElemen.qualifier == 0){
                        $(li).find("div.content").css("border-bottom","solid yellow 1px");
                        $(li).find("div.title").css("border-bottom","solid yellow 1px");
                    }
                    else{
                        $(li).find("div.content").css("border-bottom","solid lime 1px");
                        $(li).find("div.title").css("border-bottom","solid lime 1px");
                    }

                    if(value.status == 4){
                        $(li).find("div.content").css("border-bottom","solid red 1px");
                        $(li).find("div.title").css("border-bottom","solid red 1px");
                    }

                document.getElementById("list").appendChild(li);
            });
        }
    }); 
    
    
    $("#list").on("click", "li" , function (e) { 
        e.preventDefault();
        
        let content = $(this).find('.content');
        let iframe = $(this).find('iframe');
        let textarea = $(this).find('textarea');
        let button = $(this).find('button');
        let select = $(this).find('select');
        let label = $(this).find('label');

        console.log(content.css("height"));

        if(content.css("height") == "0px"){
            content.css("height", "350"); //I need to be beaten with a stick for the mess I've made : /
            iframe.css("height", "200");
            textarea.css("height", "150");
            select.css("height", "initial");
            select.css("opacity", "1");
            label.css("opacity", "1");
            button.css("height", "initial");
            button.css("opacity", "1");
        }
        else{
            content.css("height", "0");
            iframe.css("height", "0");
            textarea.css("height", "0");
            select.css("height", "0");
            button.css("height", "0");
            button.css("opacity", "0");
            select.css("opacity", "0");
            label.css("opacity", "0");
        }
    });
    

    $("#list").on("click", "li > >" , function (e) { e.stopPropagation() });

    $("#list").on("click", ".upButton" ,function (e) { 
        e.preventDefault();
        let button = this;

        if ($(this).parents("li").find("select").val() == 0) return;

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: HOST + PORT + "/paper/review",
            dataType: "json",
            data: JSON.stringify({ 
                                recommendation: $(this).parents("li").find('.recommendationInput').val(), 
                                paperId: $(this).parents("li").val(),
                                qualifier: $(this).parents("li").find("select").val()
                            }),

            complete: function(data){
                    let code = "";
                    
                    reviews.forEach(item => {
                        console.log(item.paperDTO.paperId);
                        console.log($(button).parents("li").val())
                        console.log("_")
                        if(item.paperDTO.paperId == $(button).parents("li").val()){
                            item.otherReviews.forEach(review =>{
                                code += "<li><p>"+review.name+"</p><p>"+review.recommendation+"</p><p>"+review.qualifier+"</p></li>";   
                            })
                            $("#comments").html(code);
                            $("#commentsDiv").css("display", "block");
                            return;
                        }
                    });
            }
        });
    });

    $("#commentsDiv").click(function () {
        $("#commentsDiv").css("display", "none");
    })

    $.ajax({
        type: "get",
        url: HOST + PORT + "/authentication",
        contentType: "application/json",
        
        complete: function (data) {
            $("#username").text(data.responseJSON.name)
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

    $("#back").click(function () {
        
        window.location = "../../dist/index.html";

    });
});

