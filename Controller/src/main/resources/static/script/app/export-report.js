function exportToExcelFormat(classroomId, date) {
    verticalBgColor(8000);
    $.ajax({
        url: baseHttp + '/api/report/excel/' + classroomId + '/classroom/' + date + '/date',
        type: 'GET',
        success: function (response) {
            downloadFile(response);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function downloadFile(fileAPI) {
    let responseFile = fileAPI["data"];
    let fileURL = responseFile.fileDownloadUri;
    window.open(fileURL);
}