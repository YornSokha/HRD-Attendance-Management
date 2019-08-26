// Default Configuration
$(document).ready(function () {
    toastr.options = {
        'closeButton': true,
        'debug': false,
        'newestOnTop': false,
        'progressBar': false,
        'positionClass': 'toast-top-right',
        'preventDuplicates': false,
        'showDuration': '1000',
        'hideDuration': '1000',
        'timeOut': '5000',
        'extendedTimeOut': '1000',
        'showEasing': 'swing',
        'hideEasing': 'linear',
        'showMethod': 'fadeIn',
        'hideMethod': 'fadeOut',
    }
});

// Toast Type
$('#success').click(function (event) {
    toastr.success('You clicked Success toast');
});
$('#info').click(function (event) {
    toastr.info('You clicked Info toast')
});
$('#error').click(function (event) {
    toastr.error('You clicked Error Toast')
});
$('#warning').click(function (event) {
    toastr.warning('You clicked Warning Toast')
});

// Toast Image and Progress Bar
$('#image').click(function (event) {
    toastr.options.progressBar = true,
        toastr.info('<img src="https://image.flaticon.com/icons/svg/34/34579.svg" style="width:150px;">', 'Toast Image')
});


// Toast Position
$('#position').click(function (event) {
    var pos = $('input[name=position]:checked', '#positionForm').val();
    toastr.options.positionClass = "toast-" + pos;
    toastr.options.preventDuplicates = false;
    toastr.info('This sample position', 'Toast Position')
});

// Back button
$('.back').on('click', (e) => {
    window.history.back();
});


//Responsive sidebar
jQuery(function($){
    $('#btn-sidebar').click(function(e){
        e.preventDefault();
        $(".side-bar").toggleClass("slide-sidebar");
    });
});

function myFunction(x) {
    x.classList.toggle("change");
}


$('.delete').on('click', (e) => {
    Swal.fire({
        title: 'Are you sure to delete?',
        text: "This cannot be undone!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.value) {
            toastr.success('Deleted successfully!');
            setInterval(() => {
                $(e.target).closest('form').submit();
            }, 1200);
        }
    })
});


/*profile upload */
$(document).ready(function () {
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();
            reader.onload = function (e) {
                $('#img-preview').attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#file-selector").change(function () {
        readURL(this);
    });
});
/*end profile*/

