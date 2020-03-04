//Funcion para validacion de no comprar mas de un articulo especifico por usuario
let validacionNoMasDeUno = () => {
    console.log("Entra a la validacion");
    var $idUsuario = window.sessionStorage.idUsuario;
    var $idToken = window.localStorage.getItem('token_carrito');
    // var $idArts = [$art1, $art2, $art3];
    console.log("Id usuario: " + $idUsuario);
    console.log("Token carrito: " + $idToken);
    console.log("Token sesion: " + $token);
    // console.log("Entra a la validacion: " + $fecha);
    $.ajax({
        url: `${$base_backend}/api/validacion/existente/?idUsuario=${$idUsuario}&idToken=${$idToken}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}`
        }
    }).done((data, textStatus, jqXHR) => {
        var articulos = "\n"
        var mensaje = "";
        console.log(data.length);
        
        if (data.length !== 0){
            data.forEach($articulo => {
                articulos += $articulo.descripcion +"\n";
            });
            swal({
                title: "Lo sentimos",
                text: articulos + " limitado(s) a una unidad por usuario." ,
                type: "warning",
                showConfirmButton: false,
                timer: 3000
                // confirmButtonText: "Aceptar"
            });
            
        }
        obtenerArticulosCarritoModal();
        obtenerArticulosCarrito();        
    }).fail((qXHR, textStatus, errorThrown) => {
        console.log("Ni en pedo tienes un error");
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let obtenerArticulosCarrito = (articulos) => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    const $token = window.sessionStorage.token;  
    $.ajax({
        url: `${$base_backend}/api/confirmacion/verificainventario?idToken=${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}}`
        },
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        console.log(data[1]);
	        
	let $tablaContent = '<ul class="titulos-guia">'
        +'<li>'
        +    'Producto'
        +'</li>'
        +'<li>'
        + '   Nombre del producto'
        +'</li>'
        +'<li>'
        + '   Precio por unidad'
        +'</li>'
        +'<li>'
       +'     Cant.'
      +'  </li>'
       +' <li>'
      +'      Total'
     +'   </li>'
    +'</ul>';
    let $subtotal = 0;
    let $descuento = 0;
    let $pedido = 0;
        if ( data[1] === false ) {
            data[0].forEach($articulo => {
                //console.log('inventario');
                //console.log($articulo);
                //console.log($articulo.imagen1);
                let $total = $articulo.pvpConDesc * $articulo.productoCarrito.cantidad;
                $subtotal += $total;
                $tablaContent += '<ul>'
                +'<li>'
                +   ' <img src="'+ $articulo.imagen1  +'" alt="">'
                +'</li>'
                +'<li>'
                +'    <p class="li-producto">'
                +'        <span id="nombreProducto">'+$articulo.descripcion+'</span>'
                // +'    </p>'
                // +'    <p class="existecia">'
                // +'        <span id="idProductos">'+$articulo.productoCarrito.id+'</span>'
                // +    '</p>'
                // +    '<p class="existecia">'
                // +        '<span id="existenciaProdcuto">En existencia</span>'
                // +    '</p>'
                +'</li>'
                +'<li>'
                +'    <p class="li-precio">'
                +'        <span id="precioProducto">' + format($articulo.pvpConDesc)  + '</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$articulo.productoCarrito.cantidad+'</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +  '  <p class="li-total">'
                + '       <span id="totalProducto">'+ format( $total)  + '</span>'
                +'    </p>'
                +'</li>'
            +'</ul>';
                
            });
            $('#tablaProd').html($tablaContent);
            //console.log($subtotal);
            $('#subtotalProducto').text(format($subtotal));
            if($subtotal === 0){
                swal({
                    title: "Lo sentimos",
                    text: articulos + " limitado(s) a una unidad por usuario." ,
                    type: "warning",
                    showConfirmButton: false,
                    timer: 3000
                    // confirmButtonText: "Aceptar"
                });
                window.location = "index.html"
            }
            let $final = $subtotal + $descuento +$pedido;
            $('#totalPedido').text(format($final));
        }else{
            console.log('No inventario');
            //swal("Error", "Ocurrió un error. Inventario insuficiente en uno o más artículos.", "error");
            data[0].forEach($articulo => {
                let $inventario = $articulo.inventario;
                let $cantidad = $articulo.productoCarrito.cantidad;
                if ( $inventario < $cantidad) {
                    $cantidad = $inventario;
                }
                console.log($articulo);
                $tablaContent += '<ul>'
                +'<li>'
                +   ' <img src="'+ $articulo.imagen1  +'" alt="">'
                +'</li>'
                +'<li>'
                +'    <p class="li-producto">'
                +'        <span id="nombreProducto">'+$articulo.descripcion+'</span>'
                +'    </p>'
                // +'    <p class="existecia">'
                // +'        <span id="idProductos">'+$articulo.productoCarrito.id+'</span>'
                // +    '</p>'
                // +    '<p class="existecia">'
                // +        '<span id="existenciaProdcuto">En existencia</span>'
                // +    '</p>'
                +'</li>'
                +'<li>'
                +'    <p class="li-precio">'
                +'        <span id="precioProducto">$235.00</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$cantidad+ '.00' + '</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +  '  <p class="li-total">'
                + '       <span id="totalProducto">$470.00</span>'
                +'    </p>'
                +'</li>'
            +'</ul>';
                
            });
            $('#tablaProd').html($tablaContent);
            swal({
                title: "Alerta",
                type: "info",
                text: "Inventario insuficiente en uno o más artículos."
            });
        }


    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let obtenDirecciones = () => {
    let idUsuario = window.sessionStorage.idUsuario;
    console.log("ID DEL USUARIO " + idUsuario);
    $.ajax({
        url: `${$base_backend}/api/usuario/direcciones/${idUsuario}/`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}}`
        }
    }).done((data, textStatus, jqXHR) => {
        let $opciones = '';
        console.log(data);
        console.log(data.length);
        if (data.length === undefined) {
            console.log("No tiene direcciones");
        } else {
            sessionStorage.setItem('idDirEnvio', data[0].codigoPostal);
            sessionStorage.setItem('idDirEnvi', data[0].idDireccion);
            sessionStorage.setItem('DirEnvio', data[0].idDireccion + '|' + data[0].codigoPostal + '|'
                + data[0].tipo + '|' + data[0].destinatario + '|' + data[0].calle + ', '
                + data[0].numExterior + ', ' + data[0].numInterior + ', '
                + data[0].colonia + ', ' + data[0].municipio + ', ' + data[0].ciudad + ', '
                + data[0].codigoPostal + ', ' + data[0].estado + '|' + data[0].numeroTelefonico + '|'
                + data[0].extensionTelefonica + '|' + data[0].idDireccion + '">'
                + data[0].tipo + ' - '
                + data[0].calle + ', '
                + data[0].numExterior + ', '
                + data[0].colonia);
            data.forEach(element => {
                $opciones += '<option value="' + element.idDireccion + '|' + element.codigoPostal + '|'
                    + element.tipo + '|' + element.destinatario + '|' + element.calle + ', '
                    + element.numExterior + ', ' + element.numInterior + ', '
                    + element.colonia + ', ' + element.municipio + ', ' + element.ciudad + ', '
                    + element.codigoPostal + ', ' + element.estado + '|' + element.numeroTelefonico + '|'
                    + element.extensionTelefonica + '|' + element.idDireccion + '">'
                    + element.tipo + ' - '
                    + element.calle + ', '
                    + element.numExterior + ', '
                    + element.colonia + '</option>';


            });
            $('#exampleFormControlSelect1').html($opciones);
            let valor = $('#exampleFormControlSelect1').val();
            var tipo = valor.split("|")[2];
            var destinatario = valor.split("|")[3];
            var direccion = valor.split("|")[4];
            var numeroTelefonico = valor.split("|")[5];
            window.name = '{"cpglobal" :' + valor.split("|")[1] + ', "desglo": ' + destinatario + ', "dir": ' + direccion + '}';

            $("#changeDireccion-card .card-header").empty().append(tipo);
            $("#changeDireccion-card .destinatario").empty().append(destinatario);
            $("#changeDireccion-card .direecionString").empty().append(direccion);
            $("#changeDireccion-card .telefonoString").empty().append(numeroTelefonico);
            $("#destinatarioDestinoE").html(destinatario);
            $("#direccionDestinoE").html(direccion);

        }
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let existeDireccion2 = () => {
    const $idUsuario = window.sessionStorage.idUsuario;
    //console.log('id para direccion:' + $idUsuario);
    if ($idUsuario !== undefined ) {
        const $token = window.sessionStorage.token;
        console.log("ya esta logeado");
        $.ajax({
            url: `${$base_backend}/api/confirmacion/verificadireccion?idUsuario=${$idUsuario}`,
            method: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            }
        }).done((data, textStatus, jqXHR) => {
            console.log(data);
            window.sessionStorage.setItem('ExistenciaDir', data);
            console.log(data);
            if (data === false) {
                console.log("-------------------");
                window.location = 'direcciones.html';
        // swal({
        //     title: "Alerta",
        //     type: "warning",
        //     text: "Se debe tener al menos una dirección registrada.",
        //     confirmButtonText: "Aceptar"
        // }, function (isConfirm) {
        //     if (isConfirm) {
        //         window.location = 'direcciones.html';
        //     }
        // });
    }
            
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }
    
}


$(document).ready(($) => {
    existeDireccion2();
    let $idUs = window.sessionStorage.idUsuario;
    //console.log($idUs);
    if ($idUs === undefined ) {
        window.location = 'login.html';
    }
    // obtenerArticulosCarrito();
    validacionNoMasDeUno();
    obtenDirecciones();

    //Para cambiar la direccion 
    $("#exampleFormControlSelect1").on("change", function () {

        var key = $(this).val();

        var idDireccion = key.split("|")[0];
        var cp = key.split("|")[1];
        var tipo = key.split("|")[2];
        var destinatario = key.split("|")[3];
        var direccion = key.split("|")[4];
        var numeroTelefonico = key.split("|")[5];
        var extensionTelefonica = key.split("|")[6];


        $("#changeDireccion-card .card-header").empty().append(tipo);
        $("#changeDireccion-card .destinatario").empty().append(destinatario);
        $("#changeDireccion-card .direecionString").empty().append(direccion);
        $("#changeDireccion-card .telefonoString").empty().append(numeroTelefonico); 


    });

    $("#btn-cambiarDir").on("click", function () {

        var key = $("#exampleFormControlSelect1").val();

        var idDireccion = key.split("|")[0];
        var cp = key.split("|")[1];
        var destinatario = key.split("|")[3];
        var direccion = key.split("|")[4];
        let idD = key.split("|")[1];
        $("#destinatarioDestinoE").html(destinatario);
        $("#direccionDestinoE").html(direccion);
        //console.log('Envio ' + idD);
        sessionStorage.setItem('idDirEnvio', idD);
        sessionStorage.setItem('DirEnvio', key);
        sessionStorage.setItem('idDirEnvi', idDireccion);
        $("#exampleModal").modal("hide");

    });


    //console.log("CP elegido para el envio: " + $codigoPostalEnvio );


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
});