


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

    let doLogin = () => {
        let $email = $('#email').val();
        let $password = "1";
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
            sendPasswordEmail($email);
            
        }).fail((qXHR, textStatus, errorThrown) => {
            if (qXHR.responseJSON.status === 400 && qXHR.responseJSON.message === 'CONTRASENIA INCORRECTA') {
                sendPasswordEmail($email);



            } else {
                swal({
                    title: "Error",
                    type: "error",
                    text: "Correo no registrado.",
                    confirmButtonText: "Aceptar"
                });
                $('#email').val("");
                //console.error(qXHR);
                //console.error(textStatus);
                //console.error(errorThrown);
            }
        });
    }







    let sendPasswordEmail = ($email) => {
        $('#cambiarContrasenaForm').find(':submit').html('Enviando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        $.ajax({
            url: `${$base_backend}/api/usuario/auth/resetpassword/?email=${$email}`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json'
        }).done((data, textStatus, jqXHR) => {
            setTimeout(function () {
                $('#cambiarContrasenaForm').find(':submit').html('Enviar');
                if (data) {
                    swal({
                        title: "Éxito",
                        type: "success",
                        text: "Se envió un correo electrónico para restablecer la contraseña.",
                        confirmButtonText: "Aceptar"
                    }, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'login.html';
                        }
                    });
                } else {
                    swal({
                        tile: "Error", 
                        text: "Ocurrió un error al restablecer la contraseña. Intente nuevamente.", 
                        type: "error",
                        confirmButtonText: "Aceptar"
                    });
                }
            }, 500);
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }

    $('#cambiarContrasenaForm').validate({
        rules: {
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            email: {
                required: "*Ingrese su correo electrónico",
                email: "*Ingrese un correo electrónico válido"
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass("invalid");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass("invalid");
        },
        errorClass: "text-danger",
        errorElement: 'div',
        submitHandler: function (form) {
            let email = $('#email').val();
            //sendPasswordEmail(email);
            doLogin();
        }
    });

});