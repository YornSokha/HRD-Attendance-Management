const baseHttp = window.location.origin;
// const baseHttp = 'http://localhost:8081';

$(document).ready(function () {
    let currentId;
    let currentRejectUrl;

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
            let form = $('#form-admin-reject-request');
            if (result['value'] === true)
                form.attr('action', currentRejectUrl + currentId).submit();
        })
    });

    $('.attendance-operation').click((e)=>{
        currentId = $(e.currentTarget).data('id');
        let url = $(e.currentTarget).data('url') + currentId;
        let form = $('#form-admin-confirm-request');
        currentRejectUrl = $(e.currentTarget).data('reject-url');
        form.attr('action', url);

        clearModal();
        setAttendanceOnModal(currentId);
        $('.modal').modal('show');
    });
});