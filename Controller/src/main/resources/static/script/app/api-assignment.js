$(document).ready(function () {
    $('#assign_select_generation').change(function () {
        let id = $(this).children("option:selected").val();
        getCourse(id);
    });
    $('#assign_select_academic').change(function () {
        let id = $(this).children("option:selected").val();
        getGeneration(id);
    });
    $('#assign_select_course').change(function () {
        let id = $(this).children("option:selected").val();
        getClassroom(id);
    });
});

function getCourse(id) {
    $.ajax({
        url: baseHttp + "/api/generation/" + id,
        type: "GET",
        success: function (response) {
            $('#assign_select_course').empty();
            $('#assign_select_course').append(`<option selected value="0" th:text="#{sc.choose_course}">choose course</option>`);
            for (let generation of response.data) {
                $('#assign_select_course').append(`<option value="${generation.id}">${generation.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function getGeneration(id) {
    $.ajax({
        url: baseHttp + "/api/academic/" + id,
        type: "GET",
        success: function (response) {
            $('#assign_select_generation').empty();
            $('#assign_select_generation').append(`<option selected value="0" th:text="#{sc.choose_generation}">choose generation</option>`);
            for (let academic of response.data) {
                $('#assign_select_generation').append(`<option value="${academic.id}">${academic.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function getClassroom(id) {
    $.ajax({
        url: baseHttp + "/api/classroom/" + id,
        type: "GET",
        success: function (response) {
            $('#assign_select_classroom').empty();
            $('#assign_select_classroom').append(`<option selected value="0" th:text="#{sc.choose_classroom}">choose classroom</option>`);
            for (let classroom of response.data) {
                $('#assign_select_classroom').append(`<option value="${classroom.id}">${classroom.className.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}
