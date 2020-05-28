import { HOST, PORT } from "../Globuls.js"

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
                code += "<li value = '"+value.paperId+"'>"+"<div class='title'>"+value.name+"</div><div class='content' style = 'display:none'><div class='pdfDisplay'><iframe src='http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf' width='50%' height='300px'></iframe></div><div class ='textInput'><textarea class='recommendationInput' rows='10' cols='50'></textarea></div><div class='buttons'><button class='upButton'>Submit</button><label> Grade: </label><select class='gradePuicker' name='Grade'><option value = 1>1</option><option value = 2>2</option><option value = 3>3</option><option value = 4>4</option><option value = 5>5</option><option value = 6>6</option><option value = 7>7</option></select></div></div>"+"</li>";
                $("#list").html(code);
            });
        }
    }); 
    

    $("#list").on("click", "li" , function (e) { 
        e.preventDefault();
        
        let content = $(this).children(':nth-child(2)'); 
        if(content.css("display") == "none")
            content.css("display", "block");
        else
            content.css("display", "none");
    });

    $("#list").on("click", "li > >" , function (e) { e.stopPropagation() });

    $("#list").on("click", ".upButton" ,function (e) { 
        e.preventDefault();
        let button = this;

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


});

