$(document).ready(function () {
    let date;
    let month;
    let year;

    loadMessageContainer();

    $('.date').datepicker({
        autoclose: true,
        minViewMode: 1,
        format: 'yyyy-mm'
    });

    $("#date_picker").change(function () {
        date = $("#date_picker").val();
        year = parseInt(date.split('-')[0]);
        month = parseInt(date.split('-')[1]);
    });

    $("#btnPrint").click(function () {
        if(allSelected())
            loadTableContainer();

        let classroomId = $("#select_classroom").val();
        resetReportTable();
        generateDateOnTable(month, year);
        generateRows(classroomId, date);
    });

    $("#btnExportToPDF").click(function() {
    });

    $("#btnExportToExcel").click(function () {
        let classroomId = $("#select_classroom").val();
        exportToExcelFormat(classroomId, date);
    });


});

function resetReportTable() {
    $('.week').remove();
    $('#day_names').text('');
    $('#day_numbers').text('');
    $('#day_shifts').text('');
    $('#report_body').text('');
}

function getDayName(day) {
    switch (day) {
        case 0:
            return 'Sun';
        case 1:
            return 'Mon';
        case 2:
            return 'Tue';
        case 3:
            return 'Wed';
        case 4:
            return 'Thu';
        case 5:
            return 'Fri';
        case 6:
            return 'Sat';
        default:
            return '';
    }
}

function getDaysInMonth(month, year) {
    month = month - 1;
    // Since no month has fewer than 28 days
    const date = new Date(year, month, 1);
    const days = [];
    while (date.getMonth() === month) {
        days.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }
    return days;
}

function generateDateOnTable(month, year) {
    let days = getDaysInMonth(month, year);
    let monthLength = days.length;
    let monthWeek = Math.floor(monthLength / 7);
    let monthSpareDay = monthLength % 7;
    let weeks = '';

    let i = 0;
    let j = 0;

    if (monthSpareDay > 0) monthWeek++;

    while (i < monthWeek) {
        let col = 0;
        let dayPerWeek = 0;
        while (j < monthLength) {
            let dayName = getDayName(days[j].getDay());
            if (dayName !== 'Sat' && dayName !== 'Sun') {
                col += 2;
                dayPerWeek++;
            }

            j++;

            if (dayPerWeek === 5)
                break;
        }

        if (col > 0)
            weeks += `<th colspan='${col}' class="align-middle text-nowrap week">Week ${i + 1}</th>`;
        i++;
    }

    let dayNames = '';
    let dayNumbers = '';
    let dayShifts = '';

    let k = 0;
    while (k < monthLength) {
        let dayName = getDayName(days[k].getDay());
        let dayNumber = days[k].getDate().toLocaleString('en-US',
            {minimumIntegerDigits: 2, useGrouping: false});

        if (dayName !== 'Sat' && dayName !== 'Sun') {
            dayNames += `<th colspan="2">${dayName}</th>`;
            dayNumbers += `<th colspan="2">${dayNumber}</th>`;

            dayShifts += `<th colspan="1" class="sc col-dat cell fixed-width" day='${dayNumber}_AM'>M</th>`;
            dayShifts += `<th colspan="1" class="sc col-dat cell fixed-width" day='${dayNumber}_PM'>A</th>`;
        }
        k++;
    }

    $('#report_header th:nth-child(3)').after(weeks);
    $('#day_names').append(dayNames);
    $('#day_names').append(`
        <th rowspan="3" id="m_s" class="sc align-middle col-dat bg-grey text-white calculate-dat total-missed">Missed Scan</th>
        <th rowspan="3" id="g_o" class="sc align-middle col-dat bg-orange text-white calculate-dat total-go-outside">Go outside</th>
        <th rowspan="3" id="l" class="sc align-middle col-dat bg-yellow text-white calculate-dat total-late">Late</th>
        <th rowspan="3" id="p" class="sc align-middle col-dat bg-blue text-white calculate-dat total-permission">Permission</th>
        <th rowspan="4" id="a" class="sc align-middle col-dat bg-red text-white calculate-dat total-absent">Absent</th>
        <th rowspan="4" id="t_att" class="align-middle col-dat calculate-dat total-attendance">Total Attendance</th>
        <th rowspan="4" id="atd_scr" class="align-middle col-dat calculate-dat attendance-score">Attendance Score</th>
        <th rowspan="4" id="act_scr" class="align-middle col-dat calculate-dat activity-score">Activity Score</th>
        <th rowspan="4" id="att_scr" class="align-middle col-dat calculate-dat attitude-score">Attitude Score</th>
        <th rowspan="4" id="t_sfk_scr" class="align-middle col-dat calculate-dat total-softskill-score">Total Softskill Score</th>
        <th rowspan="4" id="is_p" class="align-middle col-dat calculate-dat is-perfect-attendance">Perfect Attendance</th>
        <th rowspan="4" id="stat" class="align-middle col-dat calculate-dat current-status">Status</th>
    `);
    $('#day_numbers').append(dayNumbers);
    $('#day_shifts').append(dayShifts);
}

function generateRows(classroomId, date) {
    $.ajax({
        url: baseHttp + '/api/report/' + classroomId + '/classenroll',
        type: 'GET',
        success: function (response) {
            setReportRows(response);
            setStudentList(response);
            generateReportList(classroomId, date, response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function setReportRows(response) {
    let rows = '';
    // let cols = $('thead').find('tr > .col-dat').length;
    const length = response['data'].length;

    const userDetail = $('thead > tr > th.user-detail');
    const userDetailLength = userDetail.length;
    const dayShifts = $('#day_shifts').find('th.col-dat');
    const dayShiftsLength = dayShifts.length;
    const calculateDat = $('thead > tr > th.calculate-dat');
    const calculateDatLength = calculateDat.length;

    for (let i = 0; i < length; i++) {
        let colCounter = 0;
        let row = `<tr id='${i}'>`;

        for (let j = 0; j < userDetailLength; j++) {
            const idVal = userDetail[j].getAttribute('id');
            row += `<td id='r${i}_c${colCounter}' class="text-nowrap" val='${idVal}'></td>`;
            colCounter++;
        }

        for (let k = 0; k < dayShiftsLength; k++) {
            const dayShift = dayShifts[k].getAttribute('day');
            const day = dayShift.split('_')[0];
            const shift = dayShift.split('_')[1];

            row += `<td id='r${i}_c${colCounter}' class="sc cell" day='${day}' shift='${shift}'></td>`;
            colCounter++;
        }

        for (let j = 0; j < calculateDatLength; j++) {
            const idVal = calculateDat[j].getAttribute('id');
            row += `<td id='r${i}_c${colCounter}' type='${idVal}'></td>`;
            colCounter++;
        }

        row += '</tr>';

        rows += row;
    }

    $('#report_body').append(rows);
}

function setStudentList(response) {
    const studentList = response['data'];
    const studentListLength = studentList.length;
    for (let i = 0; i < studentListLength; i++) {
        const enrollId = studentList[i].id;
        const fullName = studentList[i].user.fullName;
        const gender = studentList[i].user.gender.toUpperCase();

        $(`#r${i}_c0`).html(i + 1);
        $(`tr[id='${i}']`).attr('enroll-id', enrollId);
        $(`#r${i}_c1`).html(fullName);
        $(`#r${i}_c2`).html(gender);
    }
}

function generateReportList(classroomId, date, responseOne) {
    $.ajax({
        url: baseHttp + '/api/report/' + classroomId + '/classroom/' + date + '/date',
        type: 'GET',
        success: function (responseTwo) {
            setAttendanceDataOnCell(responseTwo);
            calculateReportList(responseOne);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function setAttendanceDataOnCell(response) {
    const attendanceList = response['data'];
    const listLength = attendanceList.length;

    for (let i = 0; i < listLength; i++) {
        let attendanceData = attendanceList[i];
        const enrollId = attendanceData.classenroll.id;
        const leaveStatus = attendanceData.leaveStatus;
        const dayFrom = attendanceData.dateFrom.split('-')[2];

        if (leaveStatus === 'g' || leaveStatus === 'l' || leaveStatus === 'le') {
            let badge = '';
            const timeShift = attendanceData.arriveTime.split(':')[0] < 13 ? 'AM' : 'PM';

            if (leaveStatus === 'g')
                badge = '<span class="sc badge badge-round badge-info g">G</span>';
            else if (leaveStatus === 'l')
                badge = '<span class="sc badge badge-round badge-warning l">L</span>';
            else if (leaveStatus === 'le')
                badge = '<span class="sc badge badge-round badge-dark le">LE</span>';

            $(`tr[enroll-id='${enrollId}'] > td[day='${dayFrom}'][shift='${timeShift}']`).html(badge);

        } else if (leaveStatus === 'p') {
            const amPm = attendanceData.amPm.toUpperCase();
            const permissionCount = attendanceData.permissionCount;
            const badge = '<span class="sc badge badge-round badge-primary p">P</span>';

            let cellData = null;
            cellData = $(`tr[enroll-id='${enrollId}'] > td[day='${dayFrom}'][shift='${amPm}']`).attr('id').split('_');

            const startRow = cellData[0].replace('r', '');
            const startCol = cellData[1].replace('c', '');
            const endCol = parseInt(startCol) + permissionCount;

            for (let j = startCol; j < endCol; j++) {
                $(`#r${startRow}_c${j}`).html(badge);
            }
        }
    }
}

function calculateReportList(response) {
    const studentList = response['data'];
    const rowsLength = studentList.length;
    const userDetailLength = $('thead > tr > th.user-detail').length;
    const dayShiftsLength = $('#day_shifts').find('th.col-dat').length;
    const startCol = userDetailLength + dayShiftsLength;
    const calculateDat = $('thead > tr > th.calculate-dat');
    const calculateDatLength = calculateDat.length;
    const endCol = startCol + calculateDatLength;

    for (let i = 0; i < rowsLength; i++) {
        for (let j = startCol; j < endCol; j++) {
            let result;
            const type = $(`#r${i}_c${j}`).attr('type');
            const enrollId = studentList[i].id;

            if (type === 'm_s') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.m`).length;
            }
            else if (type === 'g_o') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.g`).length;
            }
            else if (type === 'l') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.l`).length;
            }
            else if (type === 'le') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.le`).length;
            }
            else if (type === 'p') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.p`).length;
            }
            else if (type === 'a') {
                result = $(`tr[enroll-id='${enrollId}'] > td > span.a`).length;
            }
            else if(type === 't_att') {
                const m_s = $(`tr[enroll-id='${enrollId}'] > td > span.m`).length * 0.1;
                const g_o = $(`tr[enroll-id='${enrollId}'] > td > span.g`).length * 0.25;
                const l = $(`tr[enroll-id='${enrollId}'] > td > span.l`).length * 0.25;
                const p = $(`tr[enroll-id='${enrollId}'] > td > span.p`).length * 0.5;
                const a = $(`tr[enroll-id='${enrollId}'] > td > span.a`).length * 1;

                result = m_s + g_o + l + p + a;
            }
            else if(type === 'atd_scr') {
                const m_s = $(`tr[enroll-id='${enrollId}'] > td > span.m`).length * 0.1;
                const g_o = $(`tr[enroll-id='${enrollId}'] > td > span.g`).length * 0.25;
                const l = $(`tr[enroll-id='${enrollId}'] > td > span.l`).length * 0.25;
                const p = $(`tr[enroll-id='${enrollId}'] > td > span.p`).length * 0.5;
                const a = $(`tr[enroll-id='${enrollId}'] > td > span.a`).length * 1;

                result = 4 - (m_s + g_o + l + p + a);
            }
            else if (type === 'act_scr') {
                result = 3;
            }
            else if (type === 'att_scr') {
                result = 3;
            }
            else if (type === 't_sfk_scr') {
                const atd_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='atd_scr']`).html());
                const act_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='act_scr']`).html());
                const att_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='att_scr']`).html());
                result = atd_scr + act_scr + att_scr;
            }
            else if (type === 'is_p') {
                const t_sfk_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='t_sfk_scr']`).html());
                result = t_sfk_scr >= 10 ? "PA" : "-";
            }
            else if (type === 'stat') {
                const atd_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='atd_scr']`).html());
                const t_sfk_scr = parseFloat($(`tr[enroll-id='${enrollId}'] > td[type='t_sfk_scr']`).html());
                result = atd_scr < 2.5 || t_sfk_scr < 5  ? "WARNING" : "-";
            }

            $(`#r${i}_c${j}`).html(result);
        }
    }
}

function allSelected() {
    let isAcademicSelected = $("#select_academic").val() !== "0" || "-1";
    let isGenerationSelected = $("#select_generation").val() !== "0" || "-1";
    let isCourseSelected = $("#select_course").val() !== "0" || "-1";
    let isClassroomSelected = $("#select_classroom").val() !== "0" || "-1";
    let isMonthNotNull = !isEmpty($("#date_picker").val());

    return isAcademicSelected && isGenerationSelected && isCourseSelected && isClassroomSelected && isMonthNotNull;
}

function loadMessageContainer() {
    $("#table-container").html("");
    $("#table-container").append(`                
                <div class="sc d-flex col-12 bg-white round-2 mt-2em shadow-1" style="height: 300px;">
                    <h5 class="sc d-flex w-100 align-items-center justify-content-center h5 text-storm-gray">please fill the missing fields</h5>
                </div>`);
}

function loadTableContainer() {
    $("#table-container").html("");
    $("#table-container").append(`
                <div class="sc col-12 my-2em">
                    <div class="table-responsive">
                        <table class="sc table table-bordered text-center bg-white">
                            <thead class="thead-light">
                            <tr id="report_header">
                                <th class="align-middle user-detail" id="no" rowspan="4" th:text="#{sc.id}">No</th>
                                <th class="align-middle user-detail" id="fullname" rowspan="4" th:text="#{sc.FullName}">Fullname</th>
                                <th class="align-middle user-detail" id="gender" rowspan="4" th:text="#{sc.Gender}">Gender</th>

                                <!-- Week 1-5  -->

                                <th class="align-middle" colspan="5" th:text="#{sc.Total}">Total</th>
                                <th colspan="7" th:text="#{sc.Result}">Result</th>
                            </tr>

                            <tr id="day_names">
                                <!-- Day Name -->
                                <!-- month - 8 -->
                                <!-- colspan = 2 -->

                                <th class="sc align-middle col-dat bg-grey text-white calculate-dat total-missed" id="m_s" rowspan="3" th:text="#{sc.MissedScan}">Missed Scan</th>
                                <th class="sc align-middle col-dat bg-orange text-white calculate-dat total-go-outside" id="g_o" rowspan="3" th:text="#{sc.GoOutside}">Go outside</th>
                                <th class="sc align-middle col-dat bg-yellow text-white calculate-dat total-late" id="l" rowspan="3" th:text="#{sc.Late}">Late</th>
                                <th class="sc align-middle col-dat bg-blue text-white calculate-dat total-permission" id="p" rowspan="3" th:text="#{sc.Permission}">Permission</th>
                                <th class="sc align-middle col-dat bg-red text-white calculate-dat total-absent" id="a" rowspan="4" th:text="#{sc.Absent}">Absent</th>
                                <th class="align-middle col-dat calculate-dat total-attendance" id="t_att" rowspan="4" th:text="#{sc.TotalAttendance}">Total Attendance</th>
                                <th class="align-middle col-dat calculate-dat attendance-score" id="atd_scr" rowspan="4" th:text="#{sc.AttitudeScore}">Attendance Score</th>
                                <th class="align-middle col-dat calculate-dat activity-score" id="act_scr" rowspan="4" th:text="#{sc.ActivityScore}">Activity Score</th>
                                <th class="align-middle col-dat calculate-dat attitude-score" id="att_scr" rowspan="4" th:text="#{sc.AttitudeScore}">Attitude Score</th>
                                <th class="align-middle col-dat calculate-dat total-softskill-score" id="t_sfk_scr" rowspan="4" th:text="#{sc.TotalSoftSkillScore}">Total Softskill Score</th>
                                <th class="align-middle col-dat calculate-dat is-perfect-attendance" id="is_p" rowspan="4" th:text="#{sc.PerfectAttendance}">Perfect Attendance</th>
                                <th class="align-middle col-dat calculate-dat current-status" id="stat" rowspan="4" th:text="#{sc.Status}">Status</th>
                            </tr>

                            <tr id="day_numbers">
                                <!-- Day Number -->
                                <!-- month - 8 -->
                                <!-- colspan = 2 -->
                            </tr>

                            <tr id="day_shifts">
                                <!-- Morning and Afternoon -->
                                <!-- (month - 8) * 2 -->
                                <!-- colspan = 1 -->
                            </tr>
                            </thead>
                            <tbody id="report_body">
                            <!-- Data -->
                            <!-- (15 + (month - 8) * 2) * 20 rows -->
                            </tbody>
                        </table>
                    </div>
                </div>`);
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}