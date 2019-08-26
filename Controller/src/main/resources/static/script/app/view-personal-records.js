const baseHttp = window.location.origin;

$(document).ready(function () {
    let date;
    let month;
    let year;

    $('.date').datepicker({
        autoclose: true,
        minViewMode: 1,
        format: 'yyyy-mm'
    });

    $("#date_picker").change(function () {
        date = $("#date_picker").val();
        year = parseInt(date.split('-')[0]);
        month = parseInt(date.split('-')[1]);

        let courseId = $('#select_course').val();
        let userId = $('#user_id').val();
        setTotalAttendance(courseId, userId, date);
    });

    $('#select_academic').change(function () {
        let userId = $('#user_id').val();
        let academicId = $(this).children("option:selected").val();
        getGeneration(academicId, userId);
    });

    $('#select_generation').change(function () {
        let userId = $('#user_id').val();
        let generationId = $(this).children("option:selected").val();
        getCourse(generationId, userId);
    });

    $('.attendance-operation').click((e) => {
        let currentAttendanceId = $(e.target).data('id');
        clearModal();
        setAttendanceOnModal(currentAttendanceId);
        $('.modal').modal('show');
    });
});

function setTotalAttendance(courseId, userId, date) {
    $.ajax({
        url: baseHttp + '/api/student/' + courseId + '/courseId/' + userId + '/userId/' + date + '/date/all/leaveType',
        type: "GET",
        success: function (response) {
            let attendanceData = response['data'];

            $('#total_go_outside').html(attendanceData.totalGoOutisde);
            $('#total_late').html(attendanceData.totalLate);
            $('#total_permission').html(attendanceData.totalPermission);
            $('#total_absent').html(attendanceData.totalAbsent);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function getGeneration(academicId, userId) {
    $.ajax({
        url: baseHttp + "/api/academic/" + academicId + "/student/" + userId,
        type: "GET",
        success: function (response) {
            $('#select_generation').empty();
            $('#select_generation').append(`<option selected value="0">choose generation</option>`);
            for (let academic of response.data) {
                $('#select_generation').append(`<option value="${academic.id}">${academic.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function getCourse(generationId, userId) {
    $.ajax({
        url: baseHttp + "/api/generation/" + generationId + "/student/" + userId,
        type: "GET",
        success: function (response) {
            $('#select_course').empty();
            $('#select_course').append(`<option selected value="0">choose course</option>`);
            for (let generation of response.data) {
                $('#select_course').append(`<option value="${generation.id}">${generation.name}</option>`);
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
}