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
                +'        <span id="precioProducto">' +   format($articulo.pvpConDesc)  + '</span>'
                +'    </p>'    
                +'</li>'
                +'<li>'
                    +'<p class="li-precio">' 
                    +    '<span id="cantidadProducto">'+$articulo.productoCarrito.cantidad + '</span>'
                +    '</p>'
                +'</li>'
                +'<li>'
                +  '  <p class="li-total">'
                + '       <span id="totalProducto">'+ format($total) + '</span>'
                +'    </p>'
                +'</li>'
            +'</ul>';
                
            });
            $('#tablaProd').html($tablaContent);
            //console.log($subtotal);
            $('#subtotalProducto').text(format($subtotal));
            $pedido = parseFloat(window.sessionStorage.CostoEnv);
            let $final = $subtotal + $descuento +$pedido;
            $('#totalPedido').text(format($final));
            $('#envioProducto').text(format($pedido) );
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

$(document).ready(($) => {
    let $idUs = window.sessionStorage.idUsuario;
    console.log($idUs);
   /*  if ($idUs === undefined ) {
        window.location = 'login.html';
    } */

    let ids = [];
    //console.log("aqui");
    obtenerArticulosCarrito();
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

    $("#modal1").click(function () {
        console.log("click");

        const $idUsuario = window.sessionStorage.idUsuario;
        const $token = window.sessionStorage.token;
        const $email = window.sessionStorage.email;
        console.log();

        $.ajax({
            url: `${$base_backend}/api/usuario/${$idUsuario}/tarjetas?mail=${$email}`,
            method: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            }
        }).done((data, textStatus, jqXHR) => {
            
            console.log(data);
            console.log(data.length);

            if (data.length == 0) {
                var $options = "<option value ='0' selected>Seleccione una opción</option>";
                $("#first_six_digits").html($options);
            } else {
                ids.length=0;
                var $options = "<option value ='0' selected>Seleccione una opción</option>";
                data.forEach(element => {
                    let datatar = [element.id, element.first_six_digits, element.customer_id];
                    ids.push(datatar);
                    //console.log(element.id);
                    //console.log(element.last_four_digits);
                    $options += "<option value ='" + element.first_six_digits +"'>"+element.last_four_digits+"</option>";
                    $("#first_six_digits").html($options);
                });
                console.log($options);
            }
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    });

    $("#instllmentss").prop('disabled', true);

    $("#first_six_digits").change(function () {
        
        let $digitos = this.value;
        console.log($digitos);
        if (this.value === "0") {
            $("#instllmentss").prop('disabled', true);
        } else {
            $("#instllmentss").prop('disabled', false);
            $.ajax({
                url: `${$base_backend}/api/ventas/pagos/installments/?firstSix=${$digitos}`,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                headers: {
                    'Authorization': `Bearer ${$token}}`
                }
            }).done((data, textStatus, jqXHR) => {
                
                //console.log(data);
                //console.log(ids);
                //console.log($digitos);
                let $optionsM = "<option value ='0' selected>Seleccione una opción</option>";
                let cont = 0;
                data.forEach(element => {
                    if (cont === 0) {
                        $optionsM += "<option value ='"+ element +"'>"+element+" Mes</option>";    
                    }else{
                        $optionsM += "<option value ='"+ element +"'>"+element+" Meses</option>";
                    }
                    cont += 1;    
                });
                $("#instllmentss").html($optionsM);
                
                ids.forEach( array => {
                    console.log(array);
                    if (array[1] === $digitos ) {
                        //console.log(array);
                        $('#cardId').val(array[0]);
                        $('#ClientIdd').val(array[2]);
                    }
                });
           
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });
        }


    });

    let valor = window.sessionStorage.DirEnvio;
    let destinatario = valor.split("|")[3];
    let direccion = valor.split("|")[4];
    $("#destinatarioDestinoE").html(destinatario);
    $("#direccionDestinoE").html(direccion);


});
