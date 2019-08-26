$(document).ready(function () {
    $("#leaveStatus").change(function () {
        let type = $("#leaveStatus").val();
        appendPermissionView(type);
        appendCustomDatePicker(type);
    });
});

function appendPermissionView(type) {
    let elements = "";
    if (type == 'l')
        elements = `<div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_request">Request Date</label>
                        <input class="sc form-control sub-title-1 date" id="date_from" name="date_from" type="text" aria-describedby="date_request_help" required>
                    </div>
                    <input type="hidden" name="dateTo" id="dateTo">

                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_leave_time">Arrive Time</label>
                        <div class="input-group date" id="datetimepicker3" data-target-input="nearest">
                        <input type="text" class="form-control datetimepicker-input" id="arrive_time" name="arrive_time" data-target="#datetimepicker3" required/>
                        <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
                            <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
                        </div>
                </div>
                    </div>
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="text_duration">Duration</label>
                        <input class="sc form-control sub-title-1" id="duration" name="duration" type="text" aria-describedby="duration_help" placeholder="duration(minutes)" required>
                    </div>

                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Reason</label>
                        <textarea class="sc form-control sub-title-1" id="_reason" name="_reason" rows="5" required style="resize: none;"></textarea>
                    </div>

                    <div class="sc col-12 pt-1em">
                        <button class="sc btn btn-primary btn-block text-button text-nowrap">Submit</button>
                    </div> `;
    else if (type == 'g') {
        elements = `<div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_request">Request Date</label>
                        <input class="sc form-control sub-title-1 date" id="date_from" name="date_from" type="text" aria-describedby="date_request_help" required>
                    </div>
                    <input type="hidden" name="dateTo" id="dateTo">
                    
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Leave Time</label>
                        <div class="input-group date" id="datetimepicker4" data-target-input="nearest">
                            <input type="text" class="sc form-control sub-title-1 datetimepicker-input" id="leave_time" name="leave_time" data-target="#datetimepicker4" required/>
                            <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
                            </div>
                        </div>
                    </div>                    
                    
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Arrive Time</label>
                        <div class="input-group date" id="datetimepicker3" data-target-input="nearest">
                            <input type="text" class="sc form-control sub-title-1 datetimepicker-input" id="arrive_time" name="arrive_time" data-target="#datetimepicker3" required/>
                            <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
                            </div>
                        </div>   
                    </div>                                                   

                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Reason</label>
                        <textarea class="sc form-control sub-title-1" id="_reason" name="_reason" rows="5" required style="resize: none;"></textarea>
                    </div>
                    <div class="sc col-12 pt-1em">
                        <button class="sc btn btn-primary btn-block text-button text-nowrap">Submit</button>
                    </div>`;
    } else if (type == 'le') {
        elements = `<div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_request">Request Date</label>
                        <input class="sc form-control sub-title-1 date" id="date_from" name="date_from" type="text" aria-describedby="date_request_help" required>
                    </div>
                    
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_leave_time">Leave Time</label>                   
                        <div class="input-group date" id="datetimepicker4" data-target-input="nearest">
                            <input type="text" class="sc form-control sub-title-1 datetimepicker-input" id="leave_time" name="leave_time" data-target="#datetimepicker4" required/>
                            <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
                                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
                            </div>
                        </div>
                        </div>                     
                        <!--<input class="sc form-control sub-title-1" id="leave_time" name="leave_time" type="time" required>-->
  
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Reason</label>
                        <textarea class="sc form-control sub-title-1" id="_reason" name="_reason" rows="5" required style="resize: none;"></textarea>
                    </div><div class="sc col-12 pt-1em">
                        <button class="sc btn btn-primary btn-block text-button text-nowrap">Submit</button>
                    </div>`;
    } else if (type == 'p') {
        elements = `<div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_request">From Date</label>
                        <input class="sc form-control sub-title-1" id="date_from" name="date_from" type="date" aria-describedby="date_request_help" readonly required>
                    </div>

                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1" for="date_to">To Date</label>
                        <input class="sc form-control sub-title-1" id="date_to" name="date_to" type="date" aria-describedby="date_to_help" readonly required>
                    </div>                 
                    <div class="sc col-12 pt-1em">
                        <label class="sc sub-title-1">Reason</label>
                        <textarea class="sc form-control sub-title-1" id="_reason" name="_reason" rows="5" required style="resize: none;"></textarea>
                    </div><div class="sc col-12 pt-1em">
                        <button class="sc btn btn-primary btn-block text-button text-nowrap">Submit</button>
                    </div>`;
    }

    $('#permission_container > div').not(':nth-child(1) , :nth-child(2)').remove();//not(:nth-child(1),:nth-child(2))
    $("#permission_container > div:nth-child(2)").after(elements);
    if (type !== 'p') {
        pickmeup('.date', {
            format: 'Y-m-d'
        });
        $(function () {
            $('#datetimepicker3').datetimepicker({
                format: 'LT'
            });
        });
        $(function () {
            $('#datetimepicker4').datetimepicker({
                format: 'LT'
            });
        });

    }
}


function appendCustomDatePicker(type) {
    let elements = "";

    if (type == 'p') {
        elements = `<div class="sc bg-white round-1 shadow-1 px-2em py-2em" id="datepicker_container">
                        <div class="row">
                            <div class="col-12">
                                <input class="form-control" id="date" name="date" type="text" hidden>
                            </div>
                            <div class="col-12">
                                <h3 class="sc h3 font-weight-bold text-silver py-sm" id="monthAndYear" style="display: block;"></h3>
                            </div>
                            <div class="col-12">
                                <div class="h-100" id="datepicker" style="display: block;">

                                    <table class="table table-bordered mb-0 h-100" id="calendar">
                                        <thead>
                                            <tr class="text-center">
                                                <td colspan="2">Sun</td>
                                                <td colspan="2">Mon</td>
                                                <td colspan="2">Tue</td>
                                                <td colspan="2">Wed</td>
                                                <td colspan="2">Thu</td>
                                                <td colspan="2">Fri</td>
                                                <td colspan="2">Sat</td>
                                            </tr>
                                            <tr class="text-center">
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                                <td colspan="1">M</td>
                                                <td colspan="1">A</td>
                                            </tr>
                                        </thead>

                                        <tbody id="calendar-body">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="sc row pt-1em">
                            <div class="col-6">
                                <button class="btn btn-outline-primary btn-block" id="previous" onclick="previous()">Previous</button>
                            </div>

                            <div class="col-6">
                                <button class="btn btn-outline-primary btn-block" id="next" onclick="next()">Next</button>
                            </div>
                        </div>
                    </div>`;
    } else {
        elements = `<div class="sc d-flex bg-white round-1 shadow-1" id="datepicker_container">
                        <h5 class="sc d-flex w-100 align-items-center justify-content-center h5 text-storm-grey">please fill the missing fields</h5>
                    </div>`;
    }

    $('#right_container').empty();
    $('#right_container').append(elements);

    let extra_height = 28;
    let height;

    if (type == 'p') {
        extra_height = -28;
        loadCalender();
    }

    height = $("#permission_container").height()
    $("#datepicker_container").height(height + extra_height);
}

// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    "use strict";
    window.addEventListener("load", function () {
        let form = document.getElementById("permission");
        form.addEventListener("submit", function (event) {
             verticalBgColor(15000);
            let permissionType = document.getElementById("leaveStatus").value;
            // let dateFrom = $('#date_from').val().split('-')
            let dateFrom = $('#date_from').val();
            $('#reason').val($('#_reason').val());
            if (permissionType === 'l') {
                setLatePermission(dateFrom);
            } else if (permissionType === 'g') {
                setGoOutsidePermission(dateFrom);
            } else if (permissionType === 'le') {
                setLeaveEarlyPermission(dateFrom);
            } else if (permissionType === 'p') {
                setTakeLeavePermission(dateFrom);
            }
            // event.preventDefault();
            // event.stopPropagation();
        }, false);
    }, false);
}());

const showLoading = function () {

    let timerInterval
    Swal.fire({
        title: 'Requesting permission!',
        allowEscapeKey: false,
        allowOutsideClick: false,
        timer: 4000,
        onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
            }, 100)
        },
        onClose: () => {
            clearInterval(timerInterval)
        }
    }).then((result) => {
        swal.fire({
            title: 'Finished!',
            type: 'success',
            timer: 2000,
            showConfirmButton: false
        })
    });
};

function setLatePermission(dateFrom) {
    let arriveTime = $('#arrive_time').val(); // it automatically convert time to 24 format
    setCommonData(dateFrom, dateFrom);
    let time = splitTime(arriveTime);
    $('#arriveTime').val(time['time']);
    $('#amPm').val(time['ampm'])
}

function setGoOutsidePermission(dateFrom) {
    let arriveTime = $('#arrive_time').val(); // it automatically convert time to 24 format
    let leaveTime = $('#leave_time').val();
    setCommonData(dateFrom, dateFrom);
    let aTime = splitTime(arriveTime);
    $('#arriveTime').val(aTime['time']);
    let lTime = splitTime(leaveTime);
    $('#leaveTime').val(lTime['time']);
    $('#amPm').val(aTime['ampm'])
}

function setCommonData(date1, date2) {
    $('#dateFrom').val(date1);
    $('#dateTo').val(date2);
}

function setLeaveEarlyPermission(dateFrom) {
    let leaveTime = $('#leave_time').val();
    setCommonData(dateFrom, dateFrom);
    let lTime = splitTime(leaveTime);
    $('#leaveTime').val(lTime['time']);
    $('#amPm').val(lTime['ampm'])
}

function setTakeLeavePermission(dateFrom) {
    setCommonData(dateFrom, $('#date_to').val());
    let permissionCount = $('input[type="checkbox"]:checked').length;
    $('#permissionCount').val(permissionCount);
}

function convertTimeTo12Format(time24) {
    let ts = time24;
    let H = +ts.substr(0, 2);
    let h = (H % 12) || 12;
    h = (h < 10) ? ("0" + h) : h;  // leading 0 at the left for 1 digit hours
    let ampm = H < 12 ? "AM" : "PM";
    let time = [];
    time['time'] = h + ts.substr(2, 3);
    time['ampm'] = ampm;
    return time;
}

function splitTime(t) {
    let time = [];
    if (t.length === 7) {
        time['time'] = t.substr(0, 4);
        time['ampm'] = t.substr(5, 2);
    } else if (t.length === 8) {
        time['time'] = t.substr(0, 5);
        time['ampm'] = t.substr(6, 2);
    }
    return time;
}