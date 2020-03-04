$(document).ready(($) => {
    //Evento para cerrar sesion
    $('.btn-logout').on('click', function () {
        doLogout();
    });
    let $tokens = window.sessionStorage.token;
    //console.log("token: " + $tokens);
    if (!$tokens) {
        //Mostrar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').show();
        $('.logged').hide();
    }else{
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').hide();
        $('.logged').show();
    }
        
});