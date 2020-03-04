let obtenerArticulosCarrito = () => {
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
        //console.log(data);
        //console.log(data[1]);
	        
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
                +'    </p>'
                +'    <p class="existecia">'
                +'        <span id="idProductos">'+$articulo.productoCarrito.id+'</span>'
                +    '</p>'
                +    '<p class="existecia">'
                +        '<span id="existenciaProdcuto">En existencia</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +'    <p class="li-precio">'
                +'        <span id="precioProducto">$ ' +   $articulo.pvpConDesc  +  '</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$articulo.productoCarrito.cantidad+'</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +  '  <p class="li-total">'
                + '       <span id="totalProducto">$'+ $total +'</span>'
                +'    </p>'
                +'</li>'
            +'</ul>';
                
            });
            $('#tablaProd').html($tablaContent);
            //console.log($subtotal);
            $('#subtotalProducto').text(format( $subtotal));
            let $final = $subtotal + $descuento +$pedido;
            window.sessionStorage.setItem("subTotal", $subtotal);
            //$('#totalPedido').text('$'+ $final);
            calcularEnvio();
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
                +'    <p class="existecia">'
                +'        <span id="idProductos">'+$articulo.productoCarrito.id+'</span>'
                +    '</p>'
                +    '<p class="existecia">'
                +        '<span id="existenciaProdcuto">En existencia</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +'    <p class="li-precio">'
                +'        <span id="precioProducto">$235.00</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$cantidad+'</span>'
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

// let obtenDirecciones = () => {
//     let idUsuario = window.sessionStorage.idUsuario;
//     $.ajax({
//         url: `${$base_backend}/api/usuario/direcciones/${idUsuario}/`,
//         method: 'GET',
//         contentType: 'application/json',
//         dataType: 'json',
//         headers: {
//             'Authorization': `Bearer ${$token}}`
//         }
//     }).done((data, textStatus, jqXHR) => {
//         let contDir = "";
//         console.log("cuando confirma: " + data);
//         if (data.leght == 0) {
//             console.log("No tiene direcciones");
//         } else {
//             let cpd = data[0].codigoPostal
//             console.log('C.P.: ' + cpd);
//             calcularEnvio(cpd);
//             return cpd;
//         }
//     }).fail((qXHR, textStatus, errorThrown) => {
//         console.error(qXHR);
//         console.error(textStatus);
//         console.error(errorThrown);
//     });
// }

let calcularEnvio = () => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    let $cp = window.sessionStorage.getItem('idDirEnvio');
    const $token = window.sessionStorage.token;
    console.log($cp);
    $.ajax({
        url: `${$base_backend}/api/ventas/tipoenvio/?cpDestino=${$cp}&idToken=${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        headers: {
            'Authorization': `Bearer ${$token}}`
        },
    }).done((data, textStatus, jqXHR) => {
        console.log(data[0].tipoServicio[0].costoTotal);
        console.log(Number((data[0].tipoServicio[0].costoTotal).toFixed(2)));
        
        let sub1 = parseFloat(window.sessionStorage.subTotal);
        let env1 = 0.00;
        let totall =  0.00;
        let texto = '';
        
        
        if(sub1>499.99){
            console.log("COSTO GRATIS");
            totall = Number(sub1).toFixed(2) ;
            env1 = 0.00;
            texto = 'Costo envío: ' + format(0.00);
            $("#fedex").prop("checked", true);
            window.sessionStorage.setItem('CostoEnv', 0.00);
        }else if (sub1 < 499.99){
            console.log("Entra en esta validacion");
            env1 = Number((data[0].tipoServicio[0].costoTotal).toFixed(2));
            totall =  Number(sub1 + env1).toFixed(2) ;
            texto = 'Costo envío: ' + format(data[0].tipoServicio[0].costoTotal);
            $("#fedex").prop("checked", true);
            window.sessionStorage.setItem('CostoEnv', Number((data[0].tipoServicio[0].costoTotal).toFixed(2)));
        }
        
        
        $('#idcostoEnv').text(texto);
        

        // $("#fedex").prop("checked", true);
        
        
        
        console.log(sub1);
        console.log(env1);
        console.log(totall);
        $('#envioProducto').text(format(env1));
        $('#totalPedido').text(format(totall));

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

$('#fedex').on('change', function() {
    if( $(this).is(':checked') ){
        // Hacer algo si el checkbox ha sido seleccionado
        $('#confCo').attr("disabled", false);
        let sub = parseFloat(window.sessionStorage.subTotal);
        let env = parseFloat(window.sessionStorage.CostoEnv);
        let total = sub + env;
        console.log(total);
        $('#envioProducto').text('$'+env);
        $('#totalPedido').text('$'+ total);
    } else {
        // Hacer algo si el checkbox ha sido deseleccionado
        $('#confCo').attr("disabled", true);
        let sub = parseFloat(window.sessionStorage.subTotal);
        $('#totalPedido').text('$'+ sub);
        $('#envioProducto').text('$0');
    }
});

$('#confCo').on('click', function () {
    window.location.href = "confirmar-pago.html";
});

$(document).ready(($) => {
    
    $('#confCo').attr("disabled", false);
    let $idUs = window.sessionStorage.idUsuario;
    console.log($idUs);
    if ($idUs === undefined ) {
        window.location = 'login.html';
    }
    //console.log("CP global: " + $codigoPostalEnvio);
    //calcularEnvio();

    $("#fedex").prop("checked", false);
    obtenerArticulosCarrito();
    //calcularEnvio();
    //obtenDirecciones();
    //Evento para cerrar sesion
    $('.btn-logout').on('click', function () {
        doLogout();
    });
    let $tokens = window.sessionStorage.token;
    //console.log("token: " + $tokens);
    if (!$tokens) {
        //console.log("si");
        //Mostrar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').show();
        $('.logged').hide();
    }else{
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        //console.log("no");
        $('.unlogged').hide();
        $('.logged').show();
    }        
});