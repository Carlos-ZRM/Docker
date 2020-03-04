$(document).ready(($) => {

    $('.preloader').delay(500).fadeOut(500);

    $('#editarDireccion').submit(function (event) {
        event.preventDefault();
    });

    let editarDireccion = (idDireccion) => {
        $('#editarDireccion').find(':submit').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
        console.log(idDireccion);
        let espacio = $('#colonia2').val();
        let colonia = espacio.split("|")[0];
        console.log(colonia);
        console.log(espacio);
        console.log($("#destinatario2").val());
        console.log($("#extension2").val());
        console.log($("#numTelefonico2").val());
        $.ajax({
            url: `${$base_backend}/api/usuario/direcciones/update/${idDireccion}`,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            },
            data: JSON.stringify({
                "calle": $("#calle2").val(),
                "ciudad": $("#ciudad2").val(),
                "codigoPostal": $("#codigoPostal2").val(),
                // "colonia": colonia,
                "colonia": $("#colonia2").val(),
                "destinatario": $("#destinatario2").val(),
                "estado": $("#estado2").val(),
                "extensionTelefonica": $("#extension2").val(),
                "idDireccion": idDireccion,
                "municipio": $("#municipio2").val(),
                "numExterior": $("#numExterior2").val(),
                "numInterior": $("#numInterior2").val(),
                "numeroTelefonico": $("#numTelefonico2").val(),
                "razonSocial": $("#razonSocial2").val(),
                "referencia": $("#referencias2").val(),
                "tipo": $("#tipoDireccion2").val()
            })
        }).done((data, textStatus, jqXHR) => {
            //console.log(data);
            setTimeout(function () {
                $('#editarDireccion').find(':submit').html('Guardar dirección');
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
                        text: "Ocurrió un error al registrar dirección. Intente nuevamente.", 
                        type: "error",
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

    $("#editarDireccion").validate({
        rules: {
            tipoDireccion2: {
                required: true,
                maxlength: 45
            },
            destinatario2: {
                required: true,
                maxlength: 45
            },
            calle2: {
                required: true,
                maxlength: 100
            },
            numExterior2: {
                required: true,
                maxlength: 45
            },
            codigoPostal2: {
                required: true,
                number: true,
                minlength: 5,
                maxlength: 5
            },
            colonia2: {
                required: true,
                maxlength: 45
            },
            ciudad2: {
                required: true,
                maxlength: 45
            },
            municipio2: {
                required: true,
                maxlength: 45
            },
            estado2: {
                required: true,
                maxlength: 45
            },
            numTelefonico2: {
                required: true,
                maxlength: 45,
                number: true
            }
        },
        messages: {
            tipoDireccion2: {
                required: "*Ingrese su tipo de dirección",
                maxlength: msg_max45length
            },
            destinatario2: {
                required: "*Ingrese su destinatario",
                maxlength: msg_max45length
            },
            calle2: {
                required: "*Ingrese su calle",
                maxlength: "*Ingrese menos de 100 caracteres"
            },
            numExterior2: {
                required: "*Ingrese su número exterior",
                maxlength: msg_max45length
            },
            codigoPostal2: {
                required: "*Ingrese su código postal",
                number: "*Ingrese un código postal válido",
                minlength: "*Ingrese al menos 5 números",
                maxlength: "*Ingrese no más de 5 números"
            },
            colonia2: {
                required: "*Ingrese su colonia",
                maxlength: msg_max45length
            },
            ciudad2: {
                required: "*Ingrese su ciudad",
                maxlength: msg_max45length
            },
            municipio2: {
                required: "*Ingrese su municipio",
                maxlength: msg_max45length
            },
            estado2: {
                required: "*Ingrese su estado",
                maxlength: msg_max45length
            },
            numTelefonico2: {
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
            let editarDireccion2 = $('#btn-editDireccion').attr('data-editdireccion');
            console.log(editarDireccion2);
            editarDireccion(editarDireccion2);
        }
    });

    $("#editar-direccion").on('click', function(){
        $("#tipoDireccion2-error").html("");
        $("#destinatario2-error").html("");
        $("#calle2-error").html("");
        $("#numExterior2-error").html("");
        $("#codigoPostal2-error").html("");
        $("#colonia2-error").html("");
        $("#ciudad2-error").html("");
        $("#municipio2-error").html("");
        $("#estado2-error").html("");
        $("#numTelefonico2-error").html("");
    });

    $("#codigoPostal2").on("change", function () {
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
                    $("#codigoPostal2").val("");
                }
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });
        } else {
            swal({
                title: "Error", 
                text: "El código postal no es válido", 
                type: "error",
                confirmButtonText: "Aceptar"
            });
        }
    });


    // $("#codigoPostal2").on("change", function () {
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
    //                         $("#ciudad2").val((data.estado).toUpperCase());
    //                         $("#municipio2").val((data.municipio).toUpperCase());
    //                         $("#estado2").val((data.estado).toUpperCase());
    //                     } else {
    //                         $aux += "<option value='" + $key.toUpperCase() + "'>" + $value.toUpperCase() + "</option>";
    //                     }
    //                 });
    //                 $("#colonia2").attr("disabled", false).empty().append($aux);
    //             } else {
    //                 swal({
    //                     title: "Error", 
    //                     text: "El código postal no es válido.", 
    //                     type: "error",
    //                     confirmButtonText: "Aceptar"
    //                 });
    //                 $("#codigoPostal2").val("");
    //                 $("#colonia2").attr("disabled", true).empty().append("");
    //                 $("#ciudad2").val("");
    //                 $("#municipio2").val("");
    //                 $("#estado2").val("");
    //             }
    //         }).fail((qXHR, textStatus, errorThrown) => {
    //             console.error(qXHR);
    //             console.error(textStatus);
    //             console.error(errorThrown);
    //         });
    //     } else {
    //         swal({
    //             title: "Error", 
    //             text: "El código postal no es válido", 
    //             type: "error",
    //             confirmButtonText: "Aceptar"
    //         });
    //     }
    // });

    // $("#colonia2").on("change", function () {
    //     let $key = $(this).val();

    //     let $splitChar = "|";

    //     let $ciudad = $key.split($splitChar)[1];
    //     let $municipio = $key.split($splitChar)[2];
    //     let $estado = $key.split($splitChar)[3];

    //     $("#ciudad2").val($ciudad);
    //     $("#municipio2").val($municipio);
    //     $("#estado2").val($estado);
    // });

});



$(document).ready(function() {
    $("form").keypress(function(e) {
        if (e.which == 13) {
            return false;
        }
    });
});