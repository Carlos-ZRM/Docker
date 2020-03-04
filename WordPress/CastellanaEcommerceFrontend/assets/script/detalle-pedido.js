$(document).ready(($) => {

    const $fechaDetalle = window.sessionStorage.fechaDetalle;
    let $pedido = window.sessionStorage.statusPedido;
    const $venta = window.sessionStorage.idVenta;
    console.log($venta);
    const $token = window.sessionStorage.token;
    $('.boton-realizado').text("Realizado el " + $fechaDetalle);
    //$pedido = "EN CA";
     if ( $pedido === "ETIQUETA PENDIENTE" ) {
        console.log("pagado")        
        $("#status-entregado").css("display", "none");
        $("#status-enviado").css("display", "none");
        $("#status-pagado").css("display", "block");        
        $('.pedido-entregado').text("Pedido #"+$venta+" - Pagado");
    } else if ( $pedido === "EN CAMINO" ) {
        console.log("enviado")
        $("#status-pagado").css("display", "none");
        $("#status-entregado").css("display", "none");
        $("#status-enviado").css("display", "block");        
        $('.pedido-entregado').text("Pedido #"+$venta+" - Enviado");
    } else {
        console.log("entregado")
        $("#status-pagado").css("display", "none");
        $("#status-enviado").css("display", "none");
        $("#status-entregado").css("display", "block");              
        $('.pedido-entregado').text("Pedido #"+$venta+" - Entregado");
    } 

    $.ajax({
        url: `${$base_backend}/api/usuario/detalleVenta/${$venta}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}}`
        }
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
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
    let $costoEnvio = 0;
    let $totalPedido = 0;
            data.forEach($articulo => {
                let $total = $articulo.precioConDescuento * $articulo.cantidad;
                $tablaContent += '<ul>'
                +'<li>'
                +   ' <img src="'+ $articulo.imagen1  +'" alt="">'
                +'</li>'
                +'<li>'
                +'    <p class="li-producto">'
                +'        <span id="nombreProducto">'+$articulo.descripcionArticulo+'</span>'
                +'    </p>'
                // +'    <p class="existecia">'
                // +'        <span id="idProductos">'+$articulo.idArticulo+'</span>'
                // +    '</p>'
                // +    '<p class="existecia">'
                // +        '<span id="existenciaProdcuto">En existencia</span>'
                // +    '</p>'
                +'</li>'
                +'<li>'
                +'    <p class="li-precio">'
                +'        <span id="precioProducto">' +   format($articulo.precioConDescuento)  +  '</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$articulo.cantidad+'</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +  '  <p class="li-total">'
                + '       <span id="totalProducto">'+ format($total) +'</span>'
                +'    </p>'
                +'</li>'
            +'</ul>';
            $subtotal += $total;
            $costoEnvio = $articulo.precioEnvio;
            $totalPedido = $articulo.totalVenta;
            });
            $('#tablaProd').html($tablaContent);
            $('#subtotalProductoDP').text(format($subtotal));
            $('#envioProductoDP').text(format($costoEnvio));
            $('#totalPedidoDP').text(format($totalPedido));
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });

    let $mail = window.sessionStorage.email;
    let $nom = window.sessionStorage.nombre;
    let $app = window.sessionStorage.appaterno;
    let $apm = window.sessionStorage.apmaterno;
    let $nombre = $nom + " " +$app + " " +$apm;
    $('#nomC').text('Nombre: ' + $nombre);
    $('#mailC').text('Mail: ' + $mail);
    $('#telC').text('Teléfono: ' + window.sessionStorage.tel);
    
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