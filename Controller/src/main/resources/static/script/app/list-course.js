const baseHttp = window.location.origin

$( document ).ready(function() {
    $('#select_academic').change(function () {
        var id=$(this).children("option:selected").val();
        getGeneration(id);
    });
    $('#select_limit').change(function(){
        let limit = $(this).children("option:selected").val();
        $('#limitStatic').val(limit);
    })
})
function getGeneration(id) {
    $.ajax({
        url: baseHttp + "/api/academic/" + id,
        type: "GET",
        success: function (response) {
            $('#filter_select_generation').empty();
            $('#filter_select_generation').append(`<option selected>All</option>`);
            for (let obj of response.data) {
                $('#filter_select_generation').append(`<option value="${obj.id}">${obj.name}</option>`);
            }
        },
        error: function (error) {
            console.log("Error");
        }
    })
}