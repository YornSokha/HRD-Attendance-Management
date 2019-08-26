let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let selectYear = document.getElementById("year");
let selectMonth = document.getElementById("month");

let months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function loadCalender() {
    let monthAndYear = document.getElementById("monthAndYear");
    showCalendar(currentMonth, currentYear);
}

function next() {
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear, true);
}

function previous() {
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year, isNext) {
    let firstDay = (new Date(year, month)).getDay();
    let daysInMonth = 32 - new Date(year, month, 32).getDate();

    let tbl = document.getElementById("calendar-body"); // body of the calendar

    // clearing all previous cells
    tbl.innerHTML = "";

    // filing data about month and in the page via DOM.
    monthAndYear.innerHTML = months[month] + " " + year;

    // creating all cells
    let date = 1;
    let indexChkCount = 1;
    for (let i = 0; i < 6; i++) {
        // creates a table row
        let row = document.createElement("tr");

        //creating individual cells, filing them up with data.
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                let cell = document.createElement("td");
                let cellText = document.createTextNode("");
                cell.colSpan = "2";
                cell.appendChild(cellText);
                row.appendChild(cell);
            } else if (date > daysInMonth) {
                break;
            } else {
                let cell = document.createElement("td");
                cell.classList.add("text-center");
                let cellText = document.createTextNode(date);
                let dateDisplay = document.createElement("span");
                dateDisplay.style = "text-align:center;display:block";
                // dateDisplay.style.width = "67px"
                dateDisplay.appendChild(cellText);

                cell.appendChild(dateDisplay);

                if (j == 0) {
                    // cell.classList.add("bg-danger")
                    let style = document.createAttribute('style');
                    style.value = "background-color:#E0E3FF";
                    cell.setAttributeNode(style);
                    // checkSatNSun.push(cell.getElementsByTagName("span")[0].textContent);
                } else if (j == 6) {
                    let style = document.createAttribute('style');
                    style.value = "background-color:#E0E3FF";
                    cell.setAttributeNode(style);
                } else {
                    let data_month = document.createAttribute('data-month');
                    data_month.value = month;
                    let data_year = document.createAttribute('data-year');
                    data_year.value = year;
                    let data_date = document.createAttribute('data-date');
                    data_date.value = date;
                    cell.setAttributeNode(data_date);
                    cell.setAttributeNode(data_month);
                    cell.setAttributeNode(data_year);

                    // no select select date if less than today
                    if ((date >= today.getDate() - 7 && year === today.getFullYear() && month === today.getMonth()) || (isNext && year >= today.getFullYear() && month >= today.getMonth())) {
                        let morningChkbox = document.createElement("span");
                        morningChkbox.classList.add("span-custom");
                        let checkBoxM = document.createElement("input");
                        checkBoxM.type = "checkbox";

                        checkBoxM.value = "A";
                        checkBoxM.classList.add("hidden-box");
                        let indexChk = document.createAttribute('id');
                        indexChk.value = indexChkCount;
                        checkBoxM.setAttributeNode(indexChk);
                        let checkBoxMSibling = document.createElement("label");
                        checkBoxMSibling.classList.add("check--label");
                        let checkBoxMSiblingFor = document.createAttribute('for');
                        checkBoxMSiblingFor.value = indexChkCount++;
                        checkBoxMSibling.setAttributeNode(checkBoxMSiblingFor);
                        let childLabelM = document.createElement("span");
                        childLabelM.classList.add("check--label-box");
                        checkBoxMSibling.appendChild(childLabelM);


                        let afternoonChkbox = document.createElement("span");
                        afternoonChkbox.classList.add("span-custom");
                        let checkBoxA = document.createElement("input");
                        checkBoxA.type = "checkbox";
                        checkBoxA.value = "P";
                        checkBoxA.classList.add("hidden-box");
                        let indexChkA = document.createAttribute('id');
                        indexChkA.value = indexChkCount;
                        checkBoxA.setAttributeNode(indexChkA);
                        let checkBoxASibling = document.createElement("label");
                        checkBoxASibling.classList.add("check--label");
                        let checkBoxASiblingFor = document.createAttribute('for');
                        checkBoxASiblingFor.value = indexChkCount++;
                        checkBoxASibling.setAttributeNode(checkBoxASiblingFor);
                        let childLabelA = document.createElement("span");
                        childLabelA.classList.add("check--label-box");
                        checkBoxASibling.appendChild(childLabelA);

                        morningChkbox.appendChild(checkBoxM);
                        morningChkbox.appendChild(checkBoxMSibling);
                        afternoonChkbox.appendChild(checkBoxA);
                        afternoonChkbox.appendChild(checkBoxASibling);
                        cell.appendChild(morningChkbox);
                        cell.appendChild(afternoonChkbox);
                    } else {
                        let morningChkbox = document.createElement("span");
                        morningChkbox.classList.add("span-custom");
                        let checkBoxM = document.createElement("input");
                        checkBoxM.type = "checkbox";
                        checkBoxM.classList.add("hidden-box");
                        let checkBoxMSibling = document.createElement("label");
                        checkBoxMSibling.classList.add("check--label");
                        let checkBoxMSiblingFor = document.createAttribute('for');
                        checkBoxMSibling.setAttributeNode(checkBoxMSiblingFor);
                        let childLabelM = document.createElement("span");
                        childLabelM.classList.add("check--label-box-disable");
                        checkBoxMSibling.appendChild(childLabelM);


                        let afternoonChkbox = document.createElement("span");
                        afternoonChkbox.classList.add("span-custom");
                        let checkBoxA = document.createElement("input");
                        checkBoxA.type = "checkbox";
                        checkBoxA.classList.add("hidden-box");
                        let checkBoxASibling = document.createElement("label");
                        checkBoxASibling.classList.add("check--label");
                        let checkBoxASiblingFor = document.createAttribute('for');
                        checkBoxASibling.setAttributeNode(checkBoxASiblingFor);
                        let childLabelA = document.createElement("span");
                        childLabelA.classList.add("check--label-box-disable");
                        checkBoxASibling.appendChild(childLabelA);

                        morningChkbox.appendChild(checkBoxM);
                        morningChkbox.appendChild(checkBoxMSibling);
                        afternoonChkbox.appendChild(checkBoxA);
                        afternoonChkbox.appendChild(checkBoxASibling);
                        cell.appendChild(morningChkbox);
                        cell.appendChild(afternoonChkbox);
                    }
                    if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                        let style1 = document.createAttribute('style');
                        style1.value = 'background-color:#949FFF;display:block;color:white;border-radius: 10px;';
                        dateDisplay.setAttributeNode(style1)
                    }
                    // color today's date
                }
                cell.colSpan = "2";
                row.appendChild(cell);
                date++;
            }
        }
        tbl.appendChild(row);
        // appending each row into calendar body.
    }
}

$(document).on('focusin', '#date', function () {
    $('#datepicker').css({
        'position': 'absolute',
        'top': '37px',
        'z-index': '1',
        'display': 'block'
    });
})

function countChkBoxChecked() {
    return $("[type='checkbox']:checked").length;
}

function clearChecked() {
    resetVariables();
    resetCheckboxes();
    clearDateInput();
}


function resetCheckboxes() {
    $("input:checkbox").prop('checked', false);
}

function enableCheckbox() {
    $('input:checkbox').attr('disabled', false);
}

function resetVariables() {
    start = undefined;
    last = undefined;
    tmpLast = undefined;
    fromDate = '';
    toDate = '';
    enableCheckbox();
    clearDateInput();
}

function getChkBoxClass(e) {
    return parseInt($(e).attr('id'));
}

let start;
let last;
let tmpLast;
let fromDate = '';
let separator = '-';
let toDate = '';

function showDate(month, date, year) {

}

function getTdDataVal(e, time) {
    return $(e).parents('td').data(time);
}

function disableCheckbox(start, last) {
    for (let i = start; i < last; i++) {
        $('#' + i).attr('disabled', true);
    }
}

function clearDateInput() {
    $('#date').val('');
    $('#date_from').val(null);
    $('#date_to').val(null);
}

function getDateFormatted(e, d, m, y, dateDecreaseStatus) {
    let rDate;
    let rTime;

    if (!dateDecreaseStatus) {
        rDate = getTdDataVal(e, d);
        rTime = $(e).val();
    } else {
        rDate = $(e).val() == 'A' ? getTdDataVal(e, d) - 1 : getTdDataVal(e, d);
        rTime = $(e).val() == 'A' ? 'A' : 'P';
    }
    let month = getTdDataVal(e, m) + 1; // by default month is less than 1, so we need to add one more
    let monthUpdate = (month + 1) < 10 ? '0' + month : month;
    let dateUpdate = (rDate) < 10 ? '0' + rDate : rDate;
    return (getTdDataVal(e, y) + separator + monthUpdate + separator + dateUpdate + ' ' + rTime);
}

function closeDatePicker() {
    $('#datepicker').css('display', 'none');
}

$(document).on('change', 'input:checkbox', function () {
    // if first check
    if (countChkBoxChecked() === 1) {
        let isDisplay = true;
        if ($(this).prop('checked')) {
            start = getChkBoxClass(this);
            $('#' + start).parent().css({
                'background-color': '#949FFF',
                'display': 'inline',
                'border-radius': '10px'
            })
            fromDate = getDateFormatted(this, 'date', 'month', 'year', false);
            toDate = fromDate;
            // to disable previous checkbox
            if (start > 1 && (last != start + 1)) {
                disableCheckbox(1, start);
            }
        } else {
            //if uncheck first checkbox or left checkbox is disabled
            if (getChkBoxClass(this) === 1 || !$('#' + (getChkBoxClass(this) - 1)).prop('checked')) {
                clearChecked();
                isDisplay = !isDisplay;
                let tid = getChkBoxClass(this);
                clearPaint(tid, tid + 1);
            } else {
                toDate = getDateFormatted(this, 'date', 'month', 'year', true);
                paint(start, start);
            }
            let l = getChkBoxClass(this);
            clearPaint(start + 1, l);
        }
        if (isDisplay) {
            $('#date_from').val(fromDate.substring(0, fromDate.indexOf(' ')));
            $('#date_to').val(toDate.substring(0, toDate.indexOf(' ')));
            $('#amPm').val(fromDate.slice(-1) + 'M');
        }
    } else if (countChkBoxChecked() > 1) {
        last = getChkBoxClass(this)
        if (
            (tmpLast !== undefined) &&
            (tmpLast > last)) {
            checkBoxFrom(last, tmpLast, false);
            toDate = getDateFormatted(this, 'date', 'month', 'year', true);
            clearPaint(start, tmpLast);
            tmpLast = last;
            paint(start, last - 1);
        } else if (last === tmpLast) {
            // if user check or uncheck on the same last checkbox
            if ($(this).prop('checked')) {
                toDate = getDateFormatted(this, 'date', 'month', 'year', false);
                paint(start, last)
            } else {
                clearPaint(start, last);
                paint(start, last - 1);
                toDate = getDateFormatted(this, 'date', 'month', 'year', true);
            }
        } else {
            tmpLast = last;
            checkBoxFrom(start, last, true);
            toDate = getDateFormatted(this, 'date', 'month', 'year', false);
            clearPaint(start, last);
            paint(start, last)
        }
        if (last === start) {
            clearChecked();
            paint(start, last);
        } else {
            $('#date_from').val(fromDate.substring(0, fromDate.indexOf(' ')));
            $('#date_to').val(toDate.substring(0, toDate.indexOf(' ')));
        }
    } else {
        clearChecked();
        let tid = getChkBoxClass(this);
        clearPaint(tid, tid + 1);

    }

    function checkBoxFrom(start, last, status) {
        for (let i = start; i <= last; i++) {
            $('#' + i).prop('checked', status);
        }
    }

    function paint(fId, lId) {
        for (let i = fId; i <= lId; i++) {
            if (fId == lId) {
                let input = document.getElementById(i);
                let style1 = document.createAttribute('style');
                style1.value = 'background-color:#949FFF;display:inline;border-radius: 10px;';
                input.parentNode.setAttributeNode(style1);
            } else if (i == fId) {
                let input = document.getElementById(i);
                let style1 = document.createAttribute('style');
                style1.value = 'background-color:#949FFF;display:inline;border-top-left-radius: 10px;border-bottom-left-radius: 10px';
                input.parentNode.setAttributeNode(style1);
            } else if (i == lId) {
                let input = document.getElementById(i);
                let style1 = document.createAttribute('style');
                style1.value = 'background-color:#949FFF;display:inline;border-top-right-radius: 10px;border-bottom-right-radius: 10px;'
                input.parentNode.setAttributeNode(style1);
            } else {
                let input = document.getElementById(i);
                let style1 = document.createAttribute('style');
                style1.value = 'background-color:#949FFF;display:inline'
                input.parentNode.setAttributeNode(style1);
            }
        }
    }

    function clearPaint(fId, lId) {
        for (let i = fId; i <= lId; i++) {
            let input = document.getElementById(i);
            let style1 = document.createAttribute('style');
            style1.value = 'background-color:white;display:inline'
            input.parentNode.setAttributeNode(style1);
        }
    }
});