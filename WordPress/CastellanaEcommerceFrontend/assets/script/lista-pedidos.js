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
        console.log(data);
        let contDir = "";
        let $dir;
        if (data.leght == 0) {
            console.log("No tiene direcciones");
        } else {
            $dir = data[0].idDireccion + '|' + data[0].codigoPostal + '|'
                                            + data[0].tipo + '|' + data[0].destinatario + '|' + data[0].calle + ', '
                                            + data[0].numExterior + ', ' + data[0].numInterior + ', '
                                            + data[0].colonia + ', ' + data[0].municipio + ', ' + data[0].ciudad + ', '
                                            + data[0].codigoPostal + ', ' + data[0].estado + '|' + data[0].numeroTelefonico + '|'
                                            + data[0].extensionTelefonica + '|' + data[0].idDireccion + '">'
                                + data[0].tipo + ' - '  
                                + data[0].calle + ', '
                                + data[0].numExterior + ', ' 
                                + data[0].colonia ;
            //console.log($dir);
            let direccion = $dir.split("|")[4];
            $('#DireccionGeneral').text('Dirección: ' + direccion);
        }
        let $mail = window.sessionStorage.email;
        let $nom = window.sessionStorage.nombre;
        let $app = window.sessionStorage.appaterno;
        let $apm = window.sessionStorage.apmaterno;
        let $nombre = $nom + " " +$app + " " +$apm;
        $('#nomC').text('Nombre: ' + $nombre);
        $('#mailC').text('Mail: ' + $mail);
        $('#telC').text('Teléfono: ' + data[0].numeroTelefonico);
        window.sessionStorage.setItem("tel", data[0].numeroTelefonico);

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}


$(document).ready(($) => {

    $("#tablePedidos").on("click", ".boton" ,function(){

        let valores=$(this).parents("tr").find("td").find(".numeroPedido").html();
        let fecha=$(this).parents("tr").find("td").find(".fechaPedido").html();
        let status=$(this).parents("tr").find("td").find(".estadoPedido").html();
        //console.log(valores);
        window.sessionStorage.setItem("idVenta", valores);
        window.sessionStorage.setItem("fechaDetalle", fecha);
        window.sessionStorage.setItem("statusPedido", status);
        window.location.href = "detalle-pedido.html";
    });

    const $idUsuario = window.sessionStorage.idUsuario;
    const $token = window.sessionStorage.token;
    $.ajax({
        url: `${$base_backend}/api/usuario/${$idUsuario}/ventas`,
        method: 'GET',
        contentType: 'application/json',
        //dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}}`
        }
    }).done((data, textStatus, jqXHR) => {
        //console.log(data);
        let html = '';
        //$("#tablePedidos > tbody").empty();
        data.forEach(element => {
            html += ''
                +       '<tr id="idenitificador" class="colores" >'
                +'          <td class="p">'
                + '             <span class="numeroPedido">'+element.idVenta+'</span>'
                +'          </td>'
                +'          <td>'
                +'              '
                +'                  <span class="fechaPedido">'+element.fechaCreacion+'</span>'
                +'              '
                +'          </td>'
                +'          <td>'
                +'              '
                +'                  <span class="nombrePedido">'+element.calleDomicilio+'</span>'
                +'              '
                +'          </td>'
                +'          <td>'
                +'               '
                +'                  <span class="precioPedido">'+ format(Number((element.total).toFixed(2))) +'</span>'
                +'              '
                +'          </td>'
                +'          <td>'
                +'                  <span class="estadoPedido">'+element.estatusPortal+'</span>'
                +'          </td>'
                +'          <td class="boton" >'
                +'                 Ver detalle'
                +'          </td>'
                +'      </tr>'
                +'';
        });
        $("#tablePedidos > tbody").append(html);
        //$('.list-pedidos').html(html);
        //window.sessionStorage.setItem();
        //$('.mytab tr.colores:even ').css('background-color', '#eaecee ');
        //$('.mytab tr.colores:odd ').css('background-color', '#fff');  
        $('#tablePedidos').DataTable( {
            scrollX: true,
            language: {
                "sProcessing":     "Procesando...",
                "sLengthMenu":     "Mostrar _MENU_ registros",
                "sZeroRecords":    "No se encontraron resultados",
                "sEmptyTable":     "Ningún dato disponible en esta tabla",
                "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
                "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix":    "",
                "sSearch":         "Buscar:",
                "sUrl":            "",
                "sInfoThousands":  ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst":    "Primero",
                    "sLast":     "Último",
                    "sNext":     "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }
            },
          /*   responsive: {
                details: {
                    display: $.fn.dataTable.Responsive.display.modal( {
                        header: function ( row ) {
                            var data = row.data();
                            return 'Details for '+data[0]+' '+data[1];
                        }
                    } ),
                    renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                        tableClass: 'mytab'
                    } )
                }
            } */
        } );
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
    obtenDirecciones();
    //llenarDatosUsr();
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
