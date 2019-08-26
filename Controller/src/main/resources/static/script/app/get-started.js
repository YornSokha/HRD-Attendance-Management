const baseHttp = 'http://110.74.194.125:15001/api/get-start/'
let btnnext = ['#course', '#class', '#generation'];
let btnnext1 = ['#generation', '#course', '#class'];
let dataHeader = ['Course', 'Class', 'Generation'];
let pathMap=['generation','course','classroom'];
let i=1,k=0,m=0;
let classNameID;

$('#next').click(function () {
    classNameID=$('#clsName').val();
    PostData(pathMap[k]);
    if(i >2 && classNameID !=0){
        Swal.fire({
            position: 'top-end',
            type: 'success',
            title: 'Your work has been saved',
            showConfirmButton: false,
            timer: 2000
        });
        window.location.href = '/dashboard/';
    }
});
let respon;
function PostData(path) {
    let data = [{'name' : $('#genName').val()},{'name' : $('#couName').val(),'generation':{'id':respon}},{'className' : {'id': $('#clsName').val()} ,'course':{'id':respon}}];

        $.ajax({
            contentType : 'application/json; charset=utf-8',
            url: baseHttp + path,
            dataType: 'json',
            type: 'POST',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            cache: false, // Force requested pages not to be cached by the browser
            processData: false, // Avoid making query string instead of JSON
            data : JSON.stringify(data[m]),//2
            success : function(res) {
                if(res != 0){
                    respon=res;
                    toastr.success('Created');
                    m++;
                    $('#card-header').text(dataHeader[k]);
                    $('ul li.custom:nth-child(' + i + ')').removeClass('active');
                    $(btnnext1[k++]).removeClass('active');
                    $(btnnext1[k]).addClass('active');
                    $('#next').attr("href", btnnext[i++]);
                    $('ul li.custom:nth-child(' + (i) + ')').addClass('active');
                }else{
                    toastr.warning('Please fill data!!!');
                }
            },
            error : function(error) {
                console.log(error)
            }
        });
    }
