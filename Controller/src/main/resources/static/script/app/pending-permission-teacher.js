const baseHttp = window.location.origin;
// const baseHttp = 'http://localhost:8081';

$(document).ready(function () {
    let currentAttendanceId;
    $('#reject-request').click((e) => {
        Swal.fire({
            title: 'Are you sure to reject permission?',
            text: "This cannot be undone!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, reject it!'
        }).then((result) => {
            let form = $('#form-teacher-reject-request');
            if (result['value'] === true)
                form.attr('action', '/attendance/teacher-reject/' + currentAttendanceId).submit();
        });
    });

    $('.attendance-operation').click((e) => {
        currentAttendanceId = $(e.currentTarget).data('id');
        let form = $('#form-teacher-confirm-request');
        form.attr('action', '/attendance/teacher-approve/' + currentAttendanceId);

        clearModal();
        setAttendanceOnModal(currentAttendanceId);
        $('.modal').modal('show');
    });
});
