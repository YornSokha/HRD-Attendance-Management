// const baseHttp = window.location.origin;

function clearModal() {
    $(".attendance-data").val("");
}

function showAllGroup() {
    $('#leave_date_group').show();
    $('#leave_time_group').show();
    $('#arrive_time_group').show();
    $('#from_date_group').show();
    $('#to_date_group').show();
    $('#duration_group').show();
}

function setAttendanceOnModal(id) {
    $.ajax({
        url: baseHttp + '/api/attendance/' + id,
        type: "GET",
        success: function (response) {
            let attendanceData = response['data'];
            let leaveStatus = attendanceData.leaveStatus;

            $("#permission_type").val(leaveStatus);
            showAllGroup();
            setTypeAttendance(leaveStatus);

            let requestedAt = new Date(attendanceData.createdAt).toISOString().split('T')[0];
            $("#requested_date").val(requestedAt);
            $("#leave_date").val(attendanceData.dateFrom);
            $("#leave_time").val(attendanceData.leaveTime);
            $("#arrive_time").val(attendanceData.arriveTime);
            $("#from_date").val(attendanceData.dateFrom);
            $("#to_date").val(attendanceData.dateTo);
            $("#duration").val(attendanceData.duration);
            $("#reason").val(attendanceData.reason);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function setTypeAttendance(type) {
    switch(type) {
        case 'l':
            $("#permission_type").val("Late");
            $('#leave_date_group').hide();
            $('#leave_time_group').hide();
            $('#from_date_group').hide();
            $('#to_date_group').hide();
            break;

        case 'le':
            $("#permission_type").val("Leave Early");
            $('#arrive_time_group').hide();
            $('#from_date_group').hide();
            $('#to_date_group').hide();
            $('#duration_group').hide();
            break;

        case 'g':
            $("#permission_type").val("Go Outside");
            $('#from_date_group').hide();
            $('#to_date_group').hide();
            $('#duration_group').hide();
            break;

        case 'p':
            $("#permission_type").val("Permission");
            $('#leave_time_group').hide();
            $('#arrive_time_group').hide();
            $('#duration_group').hide();
            break;

        default:
            $("#permission_type").val("");
            $('#leave_date_group').show();
            $('#leave_time_group').show();
            $('#arrive_time_group').show();
            $('#from_date_group').show();
            $('#to_date_group').show();
            $('#duration_group').show();
            break;
    }
}