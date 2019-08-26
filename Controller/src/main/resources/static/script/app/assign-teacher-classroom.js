let currentSelectedRowId;
$(document).ready(()=>{
    $('.assign-teacher').on('click', (e) => {
        currentSelectedRowId = $(e.currentTarget).data('row-id');
        $('#user_id').val($(e.currentTarget).data('id'));
        $('.modal').modal('show')
    });
});


$(document).ready(function () {
    $('#enroll').click((e) => {
        let userId = $('#user_id').val();
        let classroomId = $('#assign_select_classroom').val();
        let classTeacherToggle = $('#is_class_instructor');
        let classTeacher = classTeacherToggle.is(':checked');
        var teacherAssignment = {
            'classroom' : {
                'id' : classroomId
            },
            'user' : {
                'id': userId
            },
            'classTeacher' : classTeacher,
        };
        if (classroomId === null) {
            toastr.warning('Please choose classroom');
            return;
        }
        $.ajax({
            contentType : 'application/json; charset=utf-8',
            url: baseHttp + "/api/teacher-assignment/assign",
            dataType: 'json',
            type: 'POST',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            cache: false, // Force requested pages not to be cached by the browser
            processData: false, // Avoid making query string instead of JSON
            data: JSON.stringify(teacherAssignment),
            success: function (response) {
                if (response.status === true) {
                    toastr.success('Teacher has been assigned to classroom');
                } else {
                    toastr.warning('Teacher has been already existed!');
                }
                setTimeout(function () {
                    $('.modal').modal('hide')
                }, 1500);
            },
            error: function (error) {
                console.log(error);
            }
        });
    });
});