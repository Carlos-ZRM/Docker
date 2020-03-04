$(document).ready(($) => {

    $('.preloader').delay(500).fadeOut(500);

    $('#contactanosForm').submit(function (event) {
        event.preventDefault();
    });
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
    let limpiarForm = () => {
        $('#nombre').val('');
        $('#email').val('');
        $('#mensaje').val('');
    }

    let enviaInfoContacto = () => {
        $('#contactanosForm').find(':submit').html('Enviando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        let $nombre = $('#nombre').val();
        let $email = $('#email').val();
        let $mensaje = $('#mensaje').val();
        console.log("NOMBRE: " + $nombre);
        console.log("EMAIL: " + $email);
        console.log("MENSAJE: " + $mensaje);

        $.ajax({
            url: `${$base_backend}/api/usuario/contacto/`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                "nombre": $nombre,
                "mail": $email,
                "mensaje": $mensaje
            })
        }).done((data, textStatus, jqXHR) => {
            console.log("DEBE SER TRUE :     " +  data);
            setTimeout(function () {
                $('#contactanosForm').find(':submit').html('Enviar');
                if (data) {
                    limpiarForm();
                    swal({
                        title: "Éxito",
                        type: "success",
                        text: "La información se envió correctamente.",
                        confirmButtonText: "Aceptar"
                    });
                } else {
                    swal({
                        title: "Error",
                        type: "error",
                        text: "Ocurrió un error al enviar. Intente nuevamente.",
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

    $('#contactanosForm').validate({
        rules: {
            nombre: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            mensaje: {
                required: true,
            }
        },
        messages: {
            nombre: {
                required: "*Ingrese su nombre"
            },
            email: {
                required: "*Ingrese su correo electrónico",
                email: "*Ingrese un correo electrónico válido"
            },
            mensaje: {
                required: "*Ingrese un mensaje"
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
            enviaInfoContacto();
        }
    });

});