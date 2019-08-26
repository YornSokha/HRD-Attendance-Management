let currentSelectedRowId;
$(document).ready(()=>{
    $('.assign_student').on('click', (e) => {
        currentSelectedRowId = $(e.currentTarget).data('row-id');
        $('#user_id').val($(e.currentTarget).data('id'));
        $('.modal').modal('show')
    });
});


$(document).ready(function () {
    $('#enroll').click((e) => {
        let userId = $('#user_id').val();
        let classroomId = $('#select_classroom').val();
        let classenroll = {
            'classroom' : {
                'id' : classroomId
            },
            'user' : {
                'id': userId
            }
        };
        if (classroomId === null) {
            toastr.warning('Please choose classroom');
            return;
        }
        verticalBgColor(200);
        $.ajax({
            contentType : 'application/json; charset=utf-8',
            url: baseHttp + "/api/classenroll/enroll",
            dataType: 'json',
            type: 'POST',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            cache: false, // Force requested pages not to be cached by the browser
            processData: false, // Avoid making query string instead of JSON
            data: JSON.stringify(classenroll),
            success: function (response) {
                if (response.status === true) {
                    toastr.success('Student has been added to classroom');
                    //Update status icon
                    let td = $('#' + currentSelectedRowId).children().eq(7);
                    let image = td.children().children();
                    image.addClass('fa-check-circle text-success').removeClass('fa-exclamation-circle text-danger');
                } else {
                    toastr.warning('Student has been already existed!');
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