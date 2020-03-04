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
    $('.preloader').delay(500).fadeOut(500);

    let registrarUsuario = () => {
        $('#registroForm').find(':submit').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        let $nombre = $('#nombre').val();
        let $paterno = $('#paterno').val();
        let $materno = $('#materno').val();
        let $email = $('#email').val();
        let $password = $('#password').val();
        $.ajax({
            url: `${$base_backend}/api/usuario/registro/`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                "nombre": $nombre,
                "apmaterno": $paterno,
                "appaterno": $materno,
                "email": $email,
                "password": $password
            })
        }).done((data, textStatus, jqXHR) => {
            //console.log(data);
            setTimeout(function () {
                $('#registroForm').find(':submit').html('Crear cuenta');
                if (data == 1) {
                    swal({
                        title: "Éxito",
                        type: "success",
                        text: "Se hizo el registro correctamente.",
                        confirmButtonText: "Aceptar"
                    }, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'login.html';
                        }
                    });
                } else {
                    swal({
                        tilte: "Error", 
                        text: "Ocurrió un error al registrarse. Intente nuevamente.", 
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

    $('#registroForm').validate({
        rules: {    
            nombre: {
                required: true
            },
            paterno: {
                required: true
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: `${$base_backend}/api/usuario/confirmacion/verificacorreo/`,
                    type: "get",
                    data: {
                        email: function () {
                            return $("#email").val();
                        }
                    }
                }
            },
            password: "required",
            passwordConfirm: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            nombre: {
                required: "*Ingrese su nombre"
            },
            paterno: {
                required: "*Ingrese su apellido paterno"
            },
            email: {
                required: "*Ingrese su correo electrónico",
                email: "*Ingrese un correo electrónico válido",
                remote: "*El correo ya ha sido registrado"
            },
            password: "*Ingrese su contrase&ntilde;a",
            passwordConfirm: {
                required: "*Repita la contraseña ingresada",
                equalTo: "*Las contraseñas no coinciden"
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
            registrarUsuario();
        }
    });

});