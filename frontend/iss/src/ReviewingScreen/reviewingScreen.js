import { HOST, PORT } from "../Globuls.js"

$(document).ready(function () {

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
                code += "<li value = '"+value.paperId+"'>"+"<div class='title'>EWRRT"+indexInArray+"</div><div class='content' style = 'display:none'><div class='pdfDisplay'><iframe src='http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf' width='50%' height='300px'></iframe></div><div class ='textInput'><textarea class='recommendationInput' rows='10' cols='50'></textarea></div><div class='buttons'><button class='upButton'>UP</button><label>Grade: </label><select class='gradePuicker' name='Grade'><option value = 1>1</option><option value = 2>2</option><option value = 3>3</option><option value = 4>4</option><option value = 5>5</option><option value = 6>6</option><option value = 7>7</option></select></div></div>"+"</li>";
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
                        if(item.paperDTO.id == $(this).parents("li").val()){
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

