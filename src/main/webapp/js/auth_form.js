$(function() {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

});

function cryptRegister(){
    var password = document.getElementById("passwordRegister");
    var cipher = CryptoJS.MD5(password.value);
    cipher = cipher.toString();
    document.getElementById("user-real-password-register").value = cipher;
    return true;
}

function cryptLogin(){
    var password = document.getElementById("passwordLogin");
    var cipher = CryptoJS.MD5(password.value);
    cipher = cipher.toString();
    document.getElementById("user-real-password-login").value = cipher;
    return true;
}

function cryptChange(){
    var password = document.getElementById("passwordChange");
    var cipher = CryptoJS.MD5(password.value);
    cipher = cipher.toString();
    document.getElementById("user-real-password-change").value = cipher;
    return true;
}