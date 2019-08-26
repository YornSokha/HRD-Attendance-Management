const baseHttp = window.location.origin

$( document ).ready(function() {
    $('#select_generation').change(function () {
        var id=$(this).children("option:selected").val();
        getCourse(id);
    });
    $('#select_academic').change(function () {
        var id=$(this).children("option:selected").val();
        getGeneration(id);
    });
    $('#select_limit').change(function(){
        let limit = $(this).children("option:selected").val();
        $('#limitStatic').val(limit);
    })
    function getCourse(id) {
        $.ajax({
            url: baseHttp + "/api/generation/" + id,
            type: "GET",
            success: function (response) {
                $('#select_course').empty();
                $('#select_course').append(`<option selected>Choose course</option>`);
                for (let obj of response.data) {
                    $('#select_course').append(`<option value="${obj.id}">${obj.name}</option>`);
                }
            },
            error: function (error) {
                console.log("Error");
            }
        })
    }
    function getGeneration(id) {
        $.ajax({
            url: baseHttp + "/api/academic/" + id,
            type: "GET",
            success: function (response) {
                $('#select_generation').empty();
                $('#select_generation').append(`<option selected">Choose generation</option>`);
                for (let obj of response.data) {
                    $('#select_generation').append(`<option value="${obj.id}">${obj.name}</option>`);
                }
            },
            error: function (error) {
                console.log("Error");
            }
        })
    }
});
