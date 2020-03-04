let llenarCarouselMasVendido2 = ($articulos) => {

	let $carruselcontent = '';

	$('.SlickCarousel3').empty();

	$articulos.forEach($articulo => {

		let precioDescuento = '';
		if ($articulo.porcentajeDescuento != 0) {
			precioDescuento += '<label class="precio-regular">' + format($articulo.pvpConImp) + '</label>';
		}

		$carruselcontent += '<div class="ProductBlock">'
			+ '	<div class="contentProductos">'
			+ '			<figure class="snip1313">'
			+ '				<a href="#!" class="btn-detalle" data-idArticulo="' + $articulo.id + '">'
			+ '				<div class="carrusel-item"'
			+ '					style="background-image: url(' + $articulo.imagen1 + ');">'
			+ '				</div>'
			+ '				</a>'
			+ '				<a href="#!">'
			+ '					<span class="read-more" data-idArticulo="' + $articulo.id + '">'
			+ '						Agregar <img src="../../../assets/images/shopping-bag-white.png">'
			+ '					</span>'
			+ '				</a>'
			+ '			</figure>'
			+ '		<div class="container-producto">'
			+ '			<p class="item-title">' + $articulo.descripcion + '</p>'
			+ '			<div class="pull-down">'
			+ precioDescuento + '<label class="precio-actual">' + format($articulo.pvpConDesc) + '</label>'
			+ '			</div>'
			+ '		</div>'
			+ '	</div>'
			+ '</div>';
	});

	$('.SlickCarousel3').append($carruselcontent);

	setTimeout(function () {
		$(".SlickCarousel3").slick(carouselNuevos_options);
	}, 500);
}

let getArticulosCarrusel2 = ($carrusel) => {
    $.ajax({
        url: `${$base_backend}/api/articulos/categoriaCarrusel/`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        data: {
            categoriaCarrusel: 2
        }
    }).done((data, textStatus, jqXHR) => {
        console.info(data);
        llenarCarouselMasVendido2(data);
        
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let llenarDatosArticulo = ($array) => {

    //console.log($array.descripcion);
    let $body;
    let $body2
    if ($array.porcentajeDescuento === 0) {
        $body = '<p>'
        + ' <strong>' + $array.descripcion + '</strong>'
        + '</p>'
        + '<p>'
        + '<strong>' + format($array.pvpConDesc) + '</strong>'
        + '</p>'
        + '<p>'
        + 'Descripción:'
        + '</p>'
        + '<pre style="font-family:'+'Lato'+', sans-serif; white-space: pre-line;"> '
        + $array.detalle
        + '</pre>'
        + '<p>  '
        + 'Precio Final: <b>' + format($array.pvpConDesc) + '</b>'
        + '</p>'
        + '<div class="input-group mb-3 top-20" style="width: 50%;">'
        + '<div class="input-group-prepend">'
        + '    <button class="btn btn-primary btn-disminuir-detalle color-castellana" type="button"><i class="fa fa-minus"></i></button>'
        + '</div>'
        + '<input id="cantidad-fromDetalle" type="text" class="form-control" data-idArticulo="' + $array.id + '" value="1" min="1"  style="text-align: center;" onkeypress="return onlynum(event)"  onpaste="return false">'
        + '<div class="input-group-append">'
        + '    <button class="btn btn-primary btn-aumentar-detalle color-castellana" type="button"><i class="fa fa-plus"></i></button>'
        + '</div>'
        + '</div>'
        + '<button class="btn btn-primary btn-addCart btn-fromDetalle read-more-2" data-idArticulo="' + $array.id + '" data-pvpConDescuento="' + $array.pvpConDesc + '" style="margin-top: 15px;">Agregar al carrito</button>'
        + '<p style="font-weight: 400; font-style: italic; color: #76122C; margin-top: 15px;">Precios Exclusivos Venta en Línea</p>'
        $body2 = '<figure class="zoom" onmousemove="zoom(event)" style="background-image: url(' + $array.imagen1 + ')">'
        + '<div class="content-zoom" style="background-image: url(' + $array.imagen1 + ')">'
        + '</div> '
        + '</figure>';
        
    } else {
        $body = '<p>'
        + ' <strong>' + $array.descripcion + '</strong>'
        + '</p>'
        + '<p>'
        + '<strong>' + format($array.pvpConDesc) + '</strong>'
        + '</p>'
        + '<p>'
        + 'Descripción:'
        + '</p>'
        + '<pre style="font-family:'+'Lato'+', sans-serif; white-space: pre-line;">   '
        + $array.detalle
        + '</pre>    '
        + 'Precio Normal: ' + format($array.pvpConImp)
        + '<p>    '
        + 'Descuento: ' + ($array.porcentajeDescuento  * 100) + ' %'
        + '</p>'
        + '<p>  '
        + 'Precio Final: <b>' + format($array.pvpConDesc) + '</b>'
        + '</p>'
        + '<div class="input-group mb-3 top-20" style="width: 50%;">'
        + '<div class="input-group-prepend">'
        + '    <button class="btn btn-primary btn-disminuir-detalle color-castellana" type="button"><i class="fa fa-minus"></i></button>'
        + '</div>'
        + '<input id="cantidad-fromDetalle" type="text" class="form-control" data-idArticulo="' + $array.id + '" value="1" min="1"  style="text-align: center;" onkeypress="return onlynum(event)" onpaste="return false">'
        + '<div class="input-group-append">'
        + '    <button class="btn btn-primary btn-aumentar-detalle color-castellana" type="button"><i class="fa fa-plus"></i></button>'
        + '</div>'
        + '</div>'
        + '<button class="btn btn-primary btn-addCart btn-fromDetalle read-more-2" data-idArticulo="' + $array.id + '"  data-pvpConDescuento="' + $array.pvpConDesc + '" style="margin-top: 15px;">Agregar al carrito</button>'
        + '<p style="font-weight: 400; font-style: italic; color: #76122C; margin-top: 15px;">Precios Exclusivos Venta en Línea</p>'
        $body2 = '<figure class="zoom" onmousemove="zoom(event)" style="background-image: url(' + $array.imagen1 + ')">'
        + '<div class="content-zoom" style="background-image: url(' + $array.imagen1 + ')">'
        + '</div> '
        + '</figure>';
        
    }
    
    $('#imagenArt').html($body2);
    $('#detalleBotella').html($body);


}

    //SOLO INSERTA NUMEROS
    function onlynum(e){
        key = e.keyCode || e.which;
        teclado = String.fromCharCode(key);
        numeros = "0123456789";
        especiales = "8-37-38-46";
        teclado_especial=false;
        for(var i in especiales){
            if(key == especiales[i]){
                teclado_especial=true;
            }
        }
        if(numeros.indexOf(teclado)==-1 && !teclado_especial){
            return false;
        }
    }


$(document).ready($ => {


    // Acción para disminuir botella en el detalle
    $("body").on("click", ".btn-disminuir-detalle", function () {
        var actual = parseInt($("#cantidad-fromDetalle").val());
        var nueva = actual - 1;
        $("#cantidad-fromDetalle").val(nueva);
        if (nueva < 0 ) {
            $("#cantidad-fromDetalle").val(0);
        }
        console.log("Entra aqui");
    });


    // Acción para aumentar botella en el detalle
    $("body").on("click", ".btn-aumentar-detalle", function () {
        var actual = parseInt($("#cantidad-fromDetalle").val());
        var idArt = $("#cantidad-fromDetalle").attr("data-idArticulo");

        
        console.log(actual);
        $("#cantidad-fromDetalle").val(actual+1);
        
        
        console.log("ID ART_ " + idArt);
        console.log("Entra aca");
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

    let getDetalleArticulo = ($idArticulo) => {
        $.ajax({
            url: `${$base_backend}/api/articulos/detalleArticulo?id=${$idArticulo}`,
            // url: `${$base_backend}/api/articulos/detalleArticulo/${$idArticulo}/`,
            method: 'GET',
            contentType: 'application/json',
            dataType: 'json'
        }).done((data, textStatus, jqXHR) => {
            console.log(data);
            llenarDatosArticulo(data);
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }

    // let $idArticulo = window.localStorage.getItem('idArt');
    let URL = window.location.href;
    console.log(URL);
    let urlsplit = URL.split("-");
    console.log(urlsplit);
    let ultimo = urlsplit[urlsplit.length - 1 ];
    console.log(ultimo);
    let utimomas = ultimo.replace("_", "").replace("#", "").replace("!", "");
    console.log(utimomas);
    // console.log("idArticulo: " + $idArticulo);
    // getDetalleArticulo($idArticulo);

    console.log("idArticulo: " + utimomas);
    getDetalleArticulo(utimomas);
    getArticulosCarrusel2();

});

