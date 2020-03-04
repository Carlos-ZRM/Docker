
$(document).ajaxStop(function () {
	$('.preloader').delay(500).fadeOut(500);
});

let llenarCarouselPromociones = ($articulos) => {

	let $carruselcontent = '';

	$('.SlickCarousel2').empty();

	$articulos.forEach($articulo => {

		let precioDescuento = '';
		console.log($articulo.porcentajeDescuento);
		if ($articulo.porcentajeDescuento != 0) {
			precioDescuento += '<label class="precio-regular">' + format($articulo.pvpConImp) + '</label>';
		}
		console.log(precioDescuento);
		$carruselcontent += '<div class="ProductBlock">'
			+ '	<div class="contentProductos">'
			+ '			<figure class="snip1313">'
			+ '				<a href="#!" class="btn-detalle" data-idArticulo="' + $articulo.id + '">'
			+ '				<div class="carrusel-item height-promo"'
			+ '					style="background-image: url(' + $articulo.imagen1 + ');"></div>'
			+ '				<div class="promotion">'
			+ '					<img src="https://img.icons8.com/wired/40/ffffff/price-tag.png">'
			+ '				</div>'
			+ '				</a>'
			+ '				<a href="#!">'
			+ '					<span class="read-more" data-idArticulo="' + $articulo.id + '"  data-pvpConDescuento="' + $articulo.pvpConDesc + '">'
			+ '						Agregar <img src="../../../assets/images/shopping-bag-white.png">'
			+ '					</span>'
			+ '				</a>'
			+ '			</figure>'
			+ '		<div class="container-producto">'
			+ '			<p class="item-title">' + $articulo.descripcion + '</p>'
			+ '			<div class="pull-down">'
			+ precioDescuento + '<label class="precio-actual">' + format($articulo.pvpConDesc) + '&nbsp;MXN</label>'
			+ '			</div>'
			+ '		</div>'
			+ '	</div>'
			+ '</div>';
	});

	$('.SlickCarousel2').append($carruselcontent);

	setTimeout(function () {
		$(".SlickCarousel2").slick(carouselPromociones_options);
	}, 500);
}

let llenarCarouselMasVendido = ($articulos) => {

	let $carruselcontent = '';

	$('.SlickCarousel').empty();

	$articulos.forEach($articulo => {

		let precioDescuento = '';
		if ($articulo.porcentajeDescuento != 0) {
			precioDescuento += '<label class="precio-regular">' + format($articulo.pvpConImp) + '</label>';
		}

		$carruselcontent += '<div class="ProductBlock">'
			+ '	<div class="contentProductos">'
			+ '			<figure class="snip1313">'
			+ '			   <a href="#!" class="btn-detalle" data-idArticulo="' + $articulo.id + '">'
			+ '                <div class="carrusel-item" style="background-image: url(' + $articulo.imagen1 + ');"></div>'
			+ '            </a>'
			+ '				<a href="#!">'
			+ '					<span class="read-more" data-idArticulo="' + $articulo.id + '" data-pvpConDescuento="' + $articulo.pvpConDesc + '">'
			+ '						Agregar <img src="../../../assets/images/shopping-bag-white.png">'
			+ '					</span>'
			+ '				</a>'
			+ '			</figure>'
			+ '		<div class="container-producto">'
			+ '			<p class="item-title">' + $articulo.descripcion + '</p>'
			+ '			<div class="pull-down">'
			+ precioDescuento + '<label class="precio-actual">' + format($articulo.pvpConDesc) + '&nbsp;MXN</label>'
			+ '			</div>'
			+ '		</div>'
			+ '	</div>'
			+ '</div>';
	});

	$('.SlickCarousel').append($carruselcontent);

	setTimeout(function () {
		$(".SlickCarousel").slick(carouselMasVendidos_options);
	}, 500);
}

let llenarCarouselNuevos = ($articulos) => {

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
			+ '					<span class="read-more" data-idArticulo="' + $articulo.id + '" data-pvpConDescuento="' + $articulo.pvpConDesc + '">'
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

$(document).ready($ => {

	console.log("PRIMERO");
	console.log($base_backend);
	isLogged();

	$("#checknews").click(function () {
		if ($("#checknews").prop("checked")) {
			$("#newsl").attr("disabled", false);
		} else {
			$("#newsl").attr("disabled", true);
			$("#emptyEmail").attr("hidden", true);
			$("#invalidEmail").attr("hidden", true);
		}
	});

	$("#checknews").click(function () {
		if ($("#checknews").prop("checked")) {
			$("#newsl").attr("disabled", false);
		} else {
			$("#newsl").attr("disabled", true);
		}
	});

	let getArticulosCarrusel = ($carrusel) => {
		$.ajax({
			url: `${$base_backend}/api/articulos/categoriaCarrusel/`,
			method: 'GET',
			contentType: 'application/json',
			dataType: 'json',
			data: {
				categoriaCarrusel: $carrusel
			}
		}).done((data, textStatus, jqXHR) => {
			console.info(data);
			switch ($carrusel) {
				case 1:
					llenarCarouselNuevos(data);
					break;
				case 2:
					llenarCarouselMasVendido(data);
					break;
				case 3:
					llenarCarouselPromociones(data);
					break;
			}
		}).fail((qXHR, textStatus, errorThrown) => {
			console.error(qXHR);
			console.error(textStatus);
			console.error(errorThrown);
		});
	}

	getArticulosCarrusel(1);
	getArticulosCarrusel(2);
	getArticulosCarrusel(3);

	let registroNewsletter = () => {
		let $email = $('#correo').val();
		$('#newsl').html('Procesando&nbsp;<i class="fa fa-spinner fa-pulse fa-lg fa-fw">');
		$.ajax({
			url: `${$base_backend}/api/usuario/newsletter/?email=${$email}`,
			method: 'POST',
			contentType: 'application/json',
			dataType: 'json'
		}).done((data, textStatus, jqXHR) => {
			setTimeout(function () {
				$('#newsl').html('<i class="fa fa-sign-in"></i>&nbsp;Suscríbete');
				if (data == 1) {
					swal({
						title: "Éxito",
						type: "success",
						text: "El correo se registró correctamente.",
						confirmButtonText: "Aceptar"
					}, function (isConfirm) {
						if (isConfirm) {
							window.location = 'index.html';
						}
					});
				} else if (data == -1) {
					swal({
						title: "Error", 
						text: "El correo ya está registrado.", 
						type: "error",
						confirmButtonText: "Aceptar"
					});

				} else {
					swal({
						title: "Error", 
						text: "Ocurrió un error al registrarse. Intente nuevamente.", 
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

	var regex = /[\w-\.]{2,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;
	$("#newsl").click(function () {
		if ($("#correo").val() == "") {
			$("#emptyEmail").attr("hidden", false);
		} else {
			if (regex.test($("#correo").val())) {
				$("#emptyEmail").attr("hidden", true);
				$("#invalidEmail").attr("hidden", true);
				registroNewsletter();
			} else {
				$("#invalidEmail").attr("hidden", false);
				$("#emptyEmail").attr("hidden", true);
			}
		}
	});

});
