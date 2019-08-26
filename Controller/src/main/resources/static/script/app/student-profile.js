$('#btnSaveChangePassword').click(function () {
    let oldPassword=$('#text_old_password').val();
    // let newPassword=$('#text_new_password').val();
    // let confirmPassword=$('#text_confirm_password').val();
    $('#btnSaveChangePassword').attr('href',"/student/changePassword/"+oldPassword);
})

// $('#select_course').change(function(){
//     var id=$(this).children("option:selected").val();
//     $('#btnHide').attr("href", "/classroom/"+id);
//     $('#btnHide').text("FILTER");
// })