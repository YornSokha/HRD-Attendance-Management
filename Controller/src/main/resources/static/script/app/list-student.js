$(document).ready(function () {
    $('#filter_select_academic').change(function () {
        let id = $(this).children("option:selected").val();
        getDataToSelection(id, "/api/academic/", "#filter_select_generation");

    });
    $('#filter_select_generation').change(function () {
        let id = $(this).children("option:selected").val();
        getDataToSelection(id, "/api/generation/", "#filter_select_course");
    });
    $('#filter_select_course').change(function () {
        let id = $(this).children("option:selected").val();
        getClassroomFilter(id, "/api/classroom/", "#filter_select_classroom");
    });

    $('#filter_select_classroom').change(function () {
        let id = $(this).children("option:selected").val();
    });

    $('#select_limit').change(function(){
        let limit = $(this).children("option:selected").val();
        $('#limitStatic').val(limit);
    })
});

function getDataToSelection(id, url, idName) {
    $.ajax({
        url: baseHttp + url + id,
        type: "GET",
        success: function (response) {
            $(idName).empty();
            $(idName).append(`<option selected>All</option>`);
            for (let obj of response.data) {
                $(idName).append(`<option value="${obj.id}">${obj.name}</option>`);
            }
        },
        error: function (error) {
            console.log("Error");
        }
    })
}

function getClassroomFilter(id, url, idName) {
    $.ajax({
        url: baseHttp + url + id,
        type: "GET",
        success: function (response) {
            $(idName).empty();
            $(idName).append(`<option selected>All</option>`);
            for (let obj of response.data) {
                $(idName).append(`<option value="${obj.id}">${obj.className.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}