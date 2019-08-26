const baseHttp = window.location.origin

$( document ).ready(function() {
    $('#select_generation').change(function () {
        $('#required_select_generation').text('');
        var id=$(this).children("option:selected").val();
        getGeneration(id);
    });
    $('#select_academic').change(function () {
        $('#required_select_academic').text('');
        var id=$(this).children("option:selected").val();
        getAcademic(id);
    });
    (function() {
        "use strict";
        window.addEventListener("load", function() {
            let form = document.getElementById("has_select");
            form.addEventListener("submit", function(event) {
                let select_academic = $('#select_academic').val();
                let select_generation = $('#select_generation').val();
                let select_course = $('#select_course').val();
                let select_classname = $('#select_classname').val();

                /*if not select classname alert message*/
                if (select_classname == "0" || select_classname == null){
                    $('#required_select_classname').text('* Please choose classname');
                    event.preventDefault();
                    event.stopPropagation();
                }


                /*if not select alert message*/
                if (select_academic == "0" || select_academic == null){
                    $('#required_select_academic').text('* Please choose academic');
                    event.preventDefault();
                    event.stopPropagation();
                }else{
                    /*if not select generation alert message*/
                    if (select_generation == "0" || select_generation == null){
                        $('#required_select_generation').text('* Please choose generation');
                        event.preventDefault();
                        event.stopPropagation();
                    }else{
                        /*if not select course alert message*/
                        if (select_course == "0" || select_course == null){
                            $('#required_select_course').text('* Please choose course');
                            event.preventDefault();
                            event.stopPropagation();
                        }
                    }
                }

            }, false);
        }, false);
    }());
    $('#select_course').on('change', (event)=>{
        $('#required_select_course').text('');
    })
    $('#select_classname').on('change', (event)=>{
        $('#required_select_classname').text('');
    })

});

function getGeneration(id) {
    $.ajax({
        url: baseHttp + "/api/generation/" + id,
        type: "GET",
        success: function (response) {
            $('.sc.custom-select.sub-title-1.withEvent').empty();
            $('.sc.custom-select.sub-title-1.withEvent').append(`<option selected value="0">select course</option>`);
            for (let obj of response.data) {
                $('.sc.custom-select.sub-title-1.withEvent').append(`<option value="${obj.id}">${obj.name}</option>`);
            }
        },
        error: function (error) {
            console.log("Error");
        }
    })
}
function getAcademic(id) {
    $.ajax({
        url: baseHttp + "/api/academic/" + id,
        type: "GET",
        success: function (response) {
            $('#select_generation').empty();
            $('#select_generation').append(`<option selected value="0">select course</option>`);
            for (let obj of response.data) {
                $('#select_generation').append(`<option value="${obj.id}">${obj.name}</option>`);
            }
        },
        error: function (error) {
            console.log("Error");
        }
    })
}
