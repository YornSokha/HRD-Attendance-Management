const baseHttp = window.location.origin;

$(document).ready(function () {
    $('#select_generation').change(function () {
        let id = $(this).children("option:selected").val();
        getCourse(id);
    });
    $('#select_academic').change(function () {
        let id = $(this).children("option:selected").val();
        getGeneration(id);
    });
    $('#select_course').change(function () {
        let id = $(this).children("option:selected").val();
        getClassroom(id);
    });
});

function getCourse(id) {
    $.ajax({
        url: baseHttp + "/api/generation/" + id,
        type: "GET",
        success: function (response) {
            $('#select_course').empty().append(`<option selected value="0" th:text="#{sc.choose_course}">choose course</option>`);
            for (let generation of response.data) {
                $('#select_course').append(`<option value="${generation.id}">${generation.name}</option>`);
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
            $('#select_generation').empty().append(`<option selected value="0" th:text="#{sc.choose_generation}">choose generation</option>`);
            for (let academic of response.data) {
                $('#select_generation').append(`<option value="${academic.id}">${academic.name}</option>`);
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
            $('#select_classroom').empty().append(`<option selected value="0" th:text="#{sc.choose_classroom}">choose classroom</option>`);
            for (let classroom of response.data) {
                $('#select_classroom').append(`<option value="${classroom.id}">${classroom.className.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}
