// <<<<<<< HEAD
$(document).ajaxStop(function () {
    $('.preloader').delay(500).fadeOut(500);
});

$(document).ready(($) => {
    let $idUs = window.sessionStorage.idUsuario;
    console.log($idUs);
    if ($idUs === undefined ) {
        window.location = 'login.html';
    }

    let obtenDirecciones = () => {
        let idUsuario = window.sessionStorage.idUsuario;
        let contenedor = $("#contenedorDirecciones");
        $.ajax({
            url: `${$base_backend}/api/usuario/direcciones/${idUsuario}/`,
            method: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            }
        }).done((data, textStatus, jqXHR) => {
            let contDir = "";
            console.log(data.length);
            if (data.length == 0) {
                window.onload = function(){
                    console.log("No tiene direcciones");
                }
                
                swal({
                    title: "Alerta",
                    type: "warning",
                    text: "Se debe tener al menos una dirección registrada.",
                    confirmButtonText: "Aceptar"
                // }, function (isConfirm) {
                //     if (isConfirm) {
                //         window.location = 'direcciones.html';
                //     }
                });
            } else {
                data.forEach(element => {
                    console.log(element.extensionTelefonica);
                    if (element.extensionTelefonica === "") {
                        contDir +=
                        '<div class="col-md-4"><br> \n'
                        + '<div class="card direccion-card">\n'
                        + '<div class="card-header text-center">' + element.tipo + '</div>\n'
                        + '<div class="card-body text-justify">\n'
                        + '<p class="destinatario">' + element.destinatario + '</p>\n'
                        + '<p class="direccion">' + element.calle + ', ' + element.numExterior + ', ' + element.numInterior + ', ' + element.colonia + ', ' + element.municipio + ', ' + element.colonia + ', ' + element.codigoPostal + ', ' + element.estado + '</p>\n'
                        + '<p class="telefono"> ' + element.numeroTelefonico + '</p>\n'
                        + '<p class="acciones top-10"><a href="#!" data-toggle="modal" data-target="#editar-direccion" class="editar-direccion" data-iddireccion="' + element.idDireccion + '">Editar</a><a class="btn-eliminarDir  eliminar-direccion" href="#!" data-iddireccion="' + element.idDireccion + '">Eliminar</a></p>\n'
                        + '</div>\n'
                        + '</div>\n'
                        + '</div>\n'
                    }else{
                        contDir +=
                        '<div class="col-md-4"><br> \n'
                        + '<div class="card direccion-card">\n'
                        + '<div class="card-header text-center">' + element.tipo + '</div>\n'
                        + '<div class="card-body text-justify">\n'
                        + '<p class="destinatario">' + element.destinatario + '</p>\n'
                        + '<p class="direccion">' + element.calle + ', ' + element.numExterior + ', ' + element.numInterior + ', ' + element.colonia + ', ' + element.municipio + ', ' + element.colonia + ', ' + element.codigoPostal + ', ' + element.estado + '</p>\n'
                        + '<p class="telefono"> ' + element.numeroTelefonico + ' Ext. ' + element.extensionTelefonica + '</p>\n'
                        + '<p class="acciones top-10"><a href="#!" data-toggle="modal" data-target="#editar-direccion" class="editar-direccion" data-iddireccion="' + element.idDireccion + '">Editar</a><a class="btn-eliminarDir  eliminar-direccion" href="#!" data-iddireccion="' + element.idDireccion + '">Eliminar</a></p>\n'
                        + '</div>\n'
                        + '</div>\n'
                        + '</div>\n'
                    }
                });
            }
            contenedor.append(contDir);
            console.log(data);
            //console.log(contDir);
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }

    obtenDirecciones();

    // ------------Eliminar card de direcciÃ³n----------------

    let eliminarCarDireccion = ($idDireccion) => {

        let idCliente = window.sessionStorage.idUsuario;
        $.ajax({ // Inicio de Ajax
            url: `${$base_backend}/api/usuario/direcciones/${idCliente}/${$idDireccion}`, //URL del token + (parametro)
            method: 'POST', // Metodo del Ajax
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            }
        }).done((data, textStatus, jqXHR) => {
            console.log(data)
            obtenDirecciones(); // Pinta el extracto de cÃ³digo que esta en la funciÃ³n
            swal({ // Se movio la alerta de confirmaciÃ³n al done para activarla cuando cargue el ajax
                title: 'Eliminada',
                text: 'Su dirección ha sido eliminada',
                type: 'success',
                confirmButtonText: "Aceptar"
            }, function(isConfirm){
                if(isConfirm){
                    setTimeout(location.reload(), 3000);
                }
            }
            // ).then(function () {
            //     setTimeout(location.reload(), 3000);
            // }
            );
            // location.reload(); Evento para recargar la pagina 
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });

    }

    //Evento para card direcciÃ³n
                            
    $('body').on('click', '.btn-eliminarDir', function () {
        let $idDireccion = $(this).attr('data-iddireccion');  // Variable que contiene el id donde lo manda a llamar
        
        swal({     // Aletra de confirmaciÃ³n para eliminar
            title: '¿Estás seguro?',
            text: "Se eliminará dirección",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#46f964',
            cancelButtonColor: '#f61852',
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Eliminar'
        }, function (isConfirm) {
            if (isConfirm) {
                eliminarCarDireccion($idDireccion);
                $('#contenedorDirecciones').empty();
            }
        });

        // let $idDireccion = $(this).attr('data-iddireccion'); Esta variable esta ubicada de esta manera si no existe alerta  
        // eliminarCarDireccion($idDireccion); Esta variable esta ubicada de esta manera si no existe alerta

       
    });
    $('.btn-logout').on('click', function () {
        doLogout();
    });
    let $tokens = window.sessionStorage.token;
    //console.log("token: " + $tokens);
    if (!$tokens) {
        //Mostrar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').show();
        $('.logged').hide();
    } else {
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').hide();
        $('.logged').show();
    }
/*   $('.btn-eliminarDir').click(function(e){
      $('.direccion-card').fadeOut('slow', function(){
      });
  });
     */

// =======
// $(document).ready(($) => {
//     //Evento para cerrar sesion
//     $('.btn-logout').on('click', function () {
//         doLogout();
//     });
//     let $tokens = window.sessionStorage.token;
//     //console.log("token: " + $tokens);
//     if (!$tokens) {
//         //Mostrar opciones Iniciar sesión y Registrarse de navbar
//         $('.unlogged').show();
//         $('.logged').hide();
//     }else{
//         //Ocultar opciones Iniciar sesión y Registrarse de navbar
//         $('.unlogged').hide();
//         $('.logged').show();
//     }

// >>>>>>> 09cc3ba6042d2271e3c774d4843f25d75edb53d3
});