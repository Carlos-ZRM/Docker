$(document).ready(($) => {


    $('.preloader').delay(500).fadeOut(500);
    
    
    //Evento para cerrar sesion
    $('.btn-logout').on('click', function () {
        doLogout();
    });
    $('#CuentaCS').on('click', function () {
        doLogout();
    });
    let $tokens = window.sessionStorage.token;
    //console.log("token: " + $tokens);
    if (!$tokens) {
        console.log("si");
        //Mostrar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').show();
        $('.logged').hide();
    }else{
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        console.log("no");
        $('.unlogged').hide();
        $('.logged').show();
    }
});