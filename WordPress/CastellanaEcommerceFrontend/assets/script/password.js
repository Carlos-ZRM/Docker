$(document).ready(($) => {
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

    $('.preloader').delay(500).fadeOut(500);

    $('#cambiarContrasenaForm').submit(function (event) {
        event.preventDefault();
    });

    let actualizarPassword = () => {
        $('#cambiarContrasenaForm').find(':submit').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        let $newpwd = $('#newPassword').val();
        let $oldpwd = $('#oldPassword').val();
        let $usuario = window.sessionStorage.email;
        $.ajax({
            url: `${$base_backend}/api/usuario/actualizapwd/`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            },
            data: JSON.stringify({
                newpwd: $newpwd,
                oldpwd: $oldpwd,
                usuario: $usuario
            })
        }).done((data, textStatus, jqXHR) => {
            //console.log(data);
            setTimeout(function () {
                $('#cambiarContrasenaForm').find(':submit').html('Guardar');
                if (data == 1) {
                    swal({
                        title: "Éxito",
                        type: "success",
                        text: "Se actualizó la contraseña correctamente.",
                        confirmButtonText:"Aceptar"
                    }, function (isConfirm) {
                        if (isConfirm) {
                            doLogout();
                        }
                    });
                } else {
                    swal({
                        title: "Error", 
                        text: "Ocurrió un error al actualizar contraseña. Intente nuevamente.", 
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

    $("#cambiarContrasenaForm").validate({
        rules: {
            oldPassword: {
                required: true
            },
            newPassword: {
                required: true
            },
            confirmPassword: {
                required: true,
                equalTo : "#newPassword"
            }
        },
        messages: {
            oldPassword: {
                required: "*Ingrese su contrase&ntilde;a"
            },
            newPassword: {
                required: "*Ingrese su contraseña nueva"
            },
            confirmPassword: {
                required: "*Repita la contraseña ingresada",
                equalTo : "*Las contraseñas no coinciden"
            }
        },
        highlight: function(element, errorClass, validClass) {
            $(element).addClass("invalid");
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).removeClass("invalid");
        },
        errorClass: "text-danger",
        errorElement: "div",
        submitHandler: function(form) {
            actualizarPassword();
        }
    });

});