$(document).ready(($) => {

    $('.preloader').delay(500).fadeOut(500);
    //Evento para cerrar sesion
    $('.btn-logout').on('click', function () {
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

    $('#loginForm').submit(function (event) {
        event.preventDefault();
    });

    let doLogin = () => {
        $('#loginForm').find(':submit').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        let $email = $('#previoEmail').val();
        let $password = $('#previoPassword').val();
        $.ajax({
            url: `${$base_backend}/api/auth/login/`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                "user": $email,
                "password": $password
            })
        }).done((data, textStatus, jqXHR) => {
            console.log('done');
            $('#loginForm').find(':submit').html('Iniciar Sesión');
            if (data.token) {
                let $usuario = data.usuario;
                let $token = data.token;
                window.sessionStorage.setItem('token', $token);
                window.sessionStorage.setItem('idUsuario', $usuario.idUsuario);
                window.sessionStorage.setItem('nombre', $usuario.nombre);
                window.sessionStorage.setItem('appaterno', $usuario.appaterno);
                window.sessionStorage.setItem('apmaterno', $usuario.apmaterno);
                window.sessionStorage.setItem('email', $usuario.email);
                window.location = './index.html';
            } else {
                $('#previoEmail').val('');
                $('#previoPassword').val('');
                $('#previoEmail').focus();
                swal({
                    title: "Error",
                    type: "error",
                    text: "Ocurrió un error al iniciar sesión. Intente nuevamente.",
                    confirmButtonText: "Aceptar"
                });
            }
        }).fail((qXHR, textStatus, errorThrown) => {
            if (qXHR.responseJSON.status === 400 && qXHR.responseJSON.message === 'CONTRASENIA INCORRECTA') {
                $('#loginForm').find(':submit').html('Iniciar Sesión');
                $('#previoPassword').val('');
                $('#previoPassword').focus();
                swal({
                    title: "Error",
                    type: "error",
                    text: "Contraseña incorrecta. Intente nuevamente.",
                    confirmButtonText: "Aceptar"
                });



            } else {
                $('#loginForm').find(':submit').html('Iniciar Sesión');
                $('#previoPassword').val('');
                $('#previoPassword').focus();
                swal({
                    title: "Error",
                    type: "error",
                    text: "Correo no registrado.",
                    confirmButtonText: "Aceptar"
                });
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            }
        });
    }

    $("#loginForm").validate({
        rules: {
            previoEmail: {
                required: true,
                email: true
            },
            previoPassword: {
                required: true
            },
        },
        messages: {
            previoEmail: {
                required: "*Ingrese su correo electrónico",
                email: "*Ingrese un correo electrónico válido"
            },
            previoPassword: {
                required: "*Ingrese su contraseña"
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass("invalid");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass("invalid");
        },
        errorClass: "text-danger",
        errorElement: "div",
        submitHandler: function (form) {
            doLogin();
        }
    });
});