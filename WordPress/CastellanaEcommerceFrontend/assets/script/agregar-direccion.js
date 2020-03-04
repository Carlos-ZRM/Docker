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

    $('#nuevaDirForm').submit(function (event) {
        event.preventDefault();
    });

    let registrarDireccion = () => {
        $('#nuevaDirForm').find(':submit').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        
        const $idUsuario = window.sessionStorage.idUsuario;
        const $token = window.sessionStorage.token;
        let $tipoD = $("#tipoDireccion").val();
        let $destinatario = $("#destinatario").val();
        let $calle = $("#calle").val();
        let $numE = $("#numExterior").val();
        let $numI = $("#numInterior").val();
        let $cP = $("#codigoPostal").val();
        let $col = $("#colonia").val();
        let $ciudad = $("#ciudad").val();
        let $municipio = $("#municipio").val();
        let $estado = $("#estado").val();
        let $referecias = $("#referencias").val();
        let $numTel = $("#numTelefonico").val();
        let $ext = $("#extension").val();
        let $arrayDeCadenas = $col.split('|');
        //console.log("Nueva direccion\n");
        console.log("TOKEN: "+$token);

        $.ajax({
            url: `${$base_backend}/api/usuario/${$idUsuario}/addDireccion/${$token}`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            },
            data: JSON.stringify({
                "calle": $calle,
                "ciudad": $ciudad,
                "codigoPostal": $cP,
                "colonia": $arrayDeCadenas[0],
                "destinatario": $destinatario,
                "estado": $estado,
                "extensionTelefonica": $ext,
                "idCliente": $idUsuario,
                "municipio": $municipio,
                "numExterior": $numE,
                "numInterior": $numI,
                "numeroTelefonico": $numTel,
                "razonSocial": "string",
                "referencia": $referecias,
                "tipo": $tipoD
            })
        }).done((data, textStatus, jqXHR) => {
            console.log('REspuesta: '+data);
            setTimeout(function () {
                $('#nuevaDirForm').find(':submit').html('Registrar dirección');
                if (data == 1) {
                    swal({
                        title: "Éxito",
                        type: "success",
                        text: "Se registró dirección correctamente.",
						confirmButtonText: "Aceptar"
                    }, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'direcciones.html';
                        }
                    });
                } else {
                    swal({
                        title: "Error",
                        type: "error",
                        text: "Ocurrió un error al registrar dirección. Intente nuevamente.", 
                        confirmButtonText: "Aceptar"});
                }
            }, 500);
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }

    var msg_max45length = "*Ingrese menos de 45 caracteres.";

    $("#nuevaDirForm").validate({
        rules: {
            tipoDireccion: {
                required: true,
                maxlength: 45
            },
            destinatario: {
                required: true,
                maxlength: 45
            },
            calle: {
                required: true,
                maxlength: 100
            },
            numExterior: {
                required: true,
                maxlength: 45
            },
            codigoPostal: {
                required: true,
                number: true,
                minlength: 5,
                maxlength: 5
            },
            colonia: {
                required: true,
                maxlength: 45
            },
            ciudad: {
                required: true,
                maxlength: 45
            },
            municipio: {
                required: true,
                maxlength: 45
            },
            estado: {
                required: true,
                maxlength: 45
            },
            numTelefonico: {
                required: true,
                maxlength: 45,
                number: true
            }
        },
        messages: {
            tipoDireccion: {
                required: "*Ingrese su tipo de dirección",
                maxlength: msg_max45length
            },
            destinatario: {
                required: "*Ingrese su destinatario",
                maxlength: msg_max45length
            },
            calle: {
                required: "*Ingrese su calle",
                maxlength: "*Ingrese menos de 100 caracteres"
            },
            numExterior: {
                required: "*Ingrese su número exterior",
                maxlength: msg_max45length
            },
            codigoPostal: {
                required: "*Ingrese su código postal",
                number: "*Ingrese un código postal válido",
                minlength: "*Ingrese al menos 5 números",
                maxlength: "*Ingrese no más de 5 números"
            },
            colonia: {
                required: "*Ingrese su colonia",
                maxlength: msg_max45length
            },
            ciudad: {
                required: "*Ingrese su ciudad",
                maxlength: msg_max45length
            },
            municipio: {
                required: "*Ingrese su municipio",
                maxlength: msg_max45length
            },
            estado: {
                required: "*Ingrese su estado",
                maxlength: msg_max45length
            },
            numTelefonico: {
                required: "*Ingrese su número telefónico",
                number: "*Ingrese un número telefónico válido",
                maxlength: msg_max45length
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
            registrarDireccion();
        }
    });

    $("#codigoPostal").on("change", function () {
        let $length = $(this).val().length;
        let $cp = $(this).val();
        if ($length === 5) {
            $.ajax({
                url: `${$base_backend}/api/direcciones/validaCP/?cp=${$cp}`,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json'
            }).done((data, textStatus, jqXHR) => {
                
                if (data === true) {
                    console.log(data);
                } else {
                    swal({
                        title: "Error",
                        type: "error",
                        text: "El código postal no es válido.",
                        confirmButtonText: "Aceptar"    
                    });
                    $("#codigoPostal").val("");
                }
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });
        } else {
            swal({
                title: "Error",
                type: "error",
                text: "El código postal no es válido.",
                confirmButtonText: "Aceptar"
            });
        }
    });

    // $("#codigoPostal").on("change", function () {
    //     let $length = $(this).val().length;
    //     let $cp = $(this).val();
    //     if ($length === 5) {
    //         $.ajax({
    //             url: `${$base_backend}/api/direcciones/codigopostal/?cp=${$cp}`,
    //             method: 'GET',
    //             contentType: 'application/json',
    //             dataType: 'json'
    //         }).done((data, textStatus, jqXHR) => {
    //             //console.log(data);
    //             let $aux = '';
    //             if (data.colonias.length > 0) {
    //                 data.colonias.forEach(($colonia, $index) => {
    //                     //key = Colonia|Ciudad|Municipio|Estado
    //                     //value = Colonia
    //                     let $key = $colonia + '|' + data.estado + '|' + data.municipio + '|' + data.estado;
    //                     let $value = $colonia;
    //                     if ($index === 0) {
    //                         $aux += "<option value='" + $key.toUpperCase() + "' selected>" + $value.toUpperCase() + "</option>";
    //                         $("#ciudad").val((data.estado).toUpperCase());
    //                         $("#municipio").val((data.municipio).toUpperCase());
    //                         $("#estado").val((data.estado).toUpperCase());
    //                     } else {
    //                         $aux += "<option value='" + $key.toUpperCase() + "'>" + $value.toUpperCase() + "</option>";
    //                     }
    //                 });
    //                 $("#colonia").attr("disabled", false).empty().append($aux);
    //             } else {
    //                 swal({
    //                     title: "Error",
    //                     type: "error",
    //                     text: "El código postal no es válido.",
    //                     confirmButtonText: "Aceptar"    
    //                 });
    //                 $("#codigoPostal").val("");
    //                 $("#colonia").attr("disabled", true).empty().append("");
    //                 $("#ciudad").val("");
    //                 $("#municipio").val("");
    //                 $("#estado").val("");
    //             }
    //         }).fail((qXHR, textStatus, errorThrown) => {
    //             console.error(qXHR);
    //             console.error(textStatus);
    //             console.error(errorThrown);
    //         });
    //     } else {
    //         swal({
    //             title: "Error",
    //             type: "error",
    //             text: "El código postal no es válido.",
    //             confirmButtonText: "Aceptar"
    //         });
    //     }
    // });

    // $("#colonia").on("change", function () {
    //     let $key = $(this).val();

    //     let $splitChar = "|";

    //     let $ciudad = $key.split($splitChar)[1];
    //     let $municipio = $key.split($splitChar)[2];
    //     let $estado = $key.split($splitChar)[3];

    //     $("#ciudad").val($ciudad);
    //     $("#municipio").val($municipio);
    //     $("#estado").val($estado);
    // });

});