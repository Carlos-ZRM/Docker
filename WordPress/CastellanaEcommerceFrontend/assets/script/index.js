//const $base_backend = `http://localhost/ecommerce_backend`; //LOCAL
// const $base_backend = 'https://lacastellanatoqa.wundertec.com/ecommerce_backend'; //QA
// const $base_backend = 'https://lacastellanatopreprod.wundertec.com/ecommerce_backend_pre'; //PREPRO
const $base_backend = 'http://54.144.202.252:8087/lacastellana-ecommerce'; //PRODUCCION

// const $base_backend = 'http://lacastellanaqa-112212876.us-east-1.elb.amazonaws.com/ecommerce_backend:8080';


//ARTICULOS DE LOS QUE SOLO SE PUEDE ADQUIRIR UNA UNIDAD
const $art1 = 2846;
const $art2 = 2874;
const $art3 = 3841;
const $fecha = '2018-12-08';

const $token = window.sessionStorage.token;

let menuClose = document.querySelector(".top-nav .menu-close");
let menuWrapper = document.querySelector(".top-nav .menu-wrapper");
let topBannerOverlay = document.querySelector(".top-banner-overlay");

menuClose.addEventListener("click", () => {
    menuWrapper.classList.remove("is-opened");
    topBannerOverlay.classList.remove("is-moved");
  });

$('body').on('change', '.input-cantidad-compra', function () {
    console.log("ENTRA CUANDO SE MODIFICA LA CANTIDAAD");
    let $idVenta = $(this).parent().parent().find('.input-cantidad-compra').attr('idventaexpontanea');
    let $cantidad = $(this).parent().parent().find('.input-cantidad-compra').val();
    let $idArticulo = $(this).parent().parent().find('.input-cantidad-compra').attr('idArticulo');
    //console.log($idVenta);
    console.log($cantidad);
    actualizarArticuloCarrito($idVenta, $cantidad, $idArticulo); 
     
});

let changeCantidad = ($obj,$nuevaCantidad) =>{
    let $idVenta = $obj.parent().parent().find('.input-cantidad-compra').attr('idventaexpontanea');
    let $cantidad = $nuevaCantidad;//$obj.parent().parent().find('.input-cantidad-compra').val();
    let $idArticulo = $obj.parent().parent().find('.input-cantidad-compra').attr('idArticulo');
    //console.log($idVenta);
    //console.log($cantidad);
    // function listoAlActualizar(){
    //     $.when(actualizarArticuloCarrito($idVenta, $cantidad, $idArticulo)).done(function(){
    //         obtenerArticulosCarritoModal();  
    //     });
    // }
    // listoAlActualizar();
    actualizarArticuloCarrito($idVenta, $cantidad, $idArticulo);
     
}

//Funcion para formato de moneda
const formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2
});

let format = ($num) => {
    return formatter.format($num);
}

//Funcion para quitar formato de moneda
let unformat = ($num) => {
    return $num.replace(/,/g, '').replace('$', '');
}

//Genera un token Integer entre [1,100000]
let generarTokenCarrito = () => {
    let min = Math.ceil(1);
    let max = Math.floor(100000);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

//Funcion para agregar articulos al carrito
let agregarArticuloCarrito = ($idArticulo, $cantidad) => {


    console.log("agregar un articulo");

    console.log("Id Articulo: "  + $idArticulo + " cantidad " + $cantidad);

    let $token_carrito = window.localStorage.getItem('token_carrito');

        
    $.ajax({
        url: `${$base_backend}/api/articulos/inventario/${$idArticulo}?idToken=${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {

        console.log("Inventario: " + data[0]);
        console.log("En carrito: " + data[1]);
        console.log("Cantidad +: " + $cantidad);
        console.log("Nueva +: " + (parseInt(data[1]) + parseInt($cantidad)));
        let cantidadInventario = 0;
        let cantidadArticuloCarrito = 0;
        let nuevaCantidad = 0;
        console.log(parseInt(data[0]) < (parseInt(data[1]) + parseInt($cantidad)));
        if (parseInt(data[0]) >= (parseInt(data[1]) + parseInt($cantidad))){
            $.ajax({
                url: `${$base_backend}/api/usuario/carrito/agrega/${$token_carrito}?idArticulo=${$idArticulo}&cantidad=${$cantidad}`,
                method: 'POST',
                contentType: 'application/json',
                dataType: 'json'
            }).done((data, textStatus, jqXHR) => {
    
                console.log("Cantidad inventario: " + data[0]);
                console.log("Cantidad actual en carrrito: " + data[1]);
                console.log("Nueva cantidad: " + data[2]);
                console.log("IF inserto: " + data[3]);
            
                var cantidad = parseInt(data[2]);

                if(cantidad > 2){
                    console.log("segunda validacion");
                }

                if ( ($idArticulo == $art1 || $idArticulo == $art2 || $idArticulo == $art3) && cantidad > 1){
                    swal({
                        title: "¡Aviso!",
                        text: "Solo puede adquirir una unidad de este producto",
                        type: "warning",
                        confirmButtonText: "Aceptar"
                    });

                    var nuevaCantidad = cantidad - 1

                    nuevaValidacionUnaUnidadPorArticulo($token_carrito, $idArticulo, nuevaCantidad);
                } else {
                    obtenerArticulosCarritoModal();
                    if (data[3] === 1) {
                        swal({
                            title: "Éxito",
                            type: "success",
                            text: "Se agregó artículo al carrito.",
                            showConfirmButton: false,
                            timer: 1500
                            // confirmButtonText: "Aceptar"
                        });
                    } else {
                        swal({
                            title: "Error",
                            text: "Ocurrió un error al agregar al carrito. Intente nuevamente.",
                            type: "error",
                            confirmButtonText: "Aceptar"
                        });
                    }
                }
                
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });

        } else {
            swal({
                title: "ALERTA",
                text: "Actualmente contamos con " +data[0]+" unidades de este producto",
                type: "warning",
                confirmButtonText: "Aceptar"
            });  
            // $("#cantidad-fromDetalle").val((parseInt(data[0])- parseInt(data[1])));
            $("#cantidad-fromDetalle").val(1);
        }

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let nuevaValidacionUnaUnidadPorArticulo = ($token_carrito, $idArticulo, $cantidad) =>{
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/agrega/${$token_carrito}?idArticulo=${$idArticulo}&cantidad=${-$cantidad}`,
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        obtenerArticulosCarritoModal();
        console.log("NO PUEDE HABER MAS DE 1");
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

//Funcion para obtener los articulos del carrito
let obtenerArticulosCarritoModal = () => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/getArticulos/${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        let $totalCarrito = 0;
        let $cantidadCarrito = 0;
        let $carritocontent = '';

        $('#carritoTable').empty();

        data.forEach($articulo => {
            $totalCarrito += ($articulo.pvpConDesc * $articulo.productoCarrito.cantidad);
            $cantidadCarrito += $articulo.productoCarrito.cantidad;
            $carritocontent += '<tr class="item-row d-flex align-items-center contenedor-items" style="padding: 5px 10px;">'
                + '	<td class="col-1 text-center align-self-center">'
                + '		<a href="#" class="btn-quitCart cierrate-ya removeDiv removeItem"'
                + '			idventaexpontanea="' + $articulo.productoCarrito.id + '" title="Quitar producto">'
                + '			<img src="https://img.icons8.com/dotty/40/000000/close-window.png">'
                + '		</a>'
                + '	</td>'
                + '	<td class="col-4 text-center" style="height: 90px;width: 75px;vertical-align: middle;">'
                + '		<img src="' + $articulo.imagen1 + '"'
                + '			style="max-height: 100%;max-width: 100%;vertical-align: middle;">'
                + '	</td>'
                + '	<td class="col-7 align-self-center" style="padding: 0px;">'
                + '		<span class="item-title">' + $articulo.descripcion + '</span>'
                + '		<br>'
                + '		<div style="margin-top: 10px;">'
                + '			<span class="bold-label precio-item-carrito">Precio: </span><span>' + format($articulo.pvpConDesc) + '</span>'
                + '		</div>'
                + '		<div class="input-group input-group-sm mb-3" style="width: 100%;margin-top: 10px;">'
                + '			<label class="bold-label" style="margin: 8px 10px 8px 0px;">Cantidad:</label>'
                + '			<div class="input-group-prepend">'
                + '				<button class="btn-shopping btn-disminuir" type="button" title="Menos">'
                + '					<img src="../../../assets/images/less.svg">'
                + '				</button>'
                + '			</div>'
                + '			<input type="text" idventaexpontanea="' + $articulo.productoCarrito.id + '"'
                + '                            idArticulo="' + $articulo.id + '"'
                + '				class="form-control input-cantidad-compra height-input-carrito"  value="' + $articulo.productoCarrito.cantidad + '"'
                + '				style="text-align: center;" onkeypress="return onlynum(event)" onpaste="return false">'
                + '			<div class="input-group-append">'
                + '				<button class="btn-shopping btn-aumentar" type="button" title="Más">'
                + '					<img src="../../../assets/images/add.svg">'
                + '				</button>'
                + '			</div>'
                + '		</div>'
                + '	</td>'
                + '</tr>';
        });

        $('#carritoTable').append($carritocontent);

        $('.shop-qty').text($cantidadCarrito);
        $('#total-carrito').text(format($totalCarrito));

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

let obtenerArticulosCarritoModal2 = () => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/getArticulos/${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        console.log("Articulos del carrito: "+data.length);
        

        if (data.length === 0) {
            //$( "#showToast" ).click(function() {
                $('.toast').toast('show');
              //});
        }else{
            let menuOpen = document.querySelector(".top-nav .menu-open");
            let menuClose = document.querySelector(".top-nav .menu-close");
            let menuWrapper = document.querySelector(".top-nav .menu-wrapper");
            let topBannerOverlay = document.querySelector(".top-banner-overlay");


            menuWrapper.classList.add("is-opened");
            topBannerOverlay.classList.add("is-moved");
            
      
            menuClose.addEventListener("click", () => {
              menuWrapper.classList.remove("is-opened");
              topBannerOverlay.classList.remove("is-moved");
            });
          

        }

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}


let obtenerArticulosCarritoModal3 = () => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/getArticulos/${$token_carrito}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        console.log("Articulos del carrito: "+data.length);
        

        if (data.length === 0) {
            console.log("cerrar");
            let menuOpen = document.querySelector(".top-nav .menu-open");
            let menuClose = document.querySelector(".top-nav .menu-close");
            let menuWrapper = document.querySelector(".top-nav .menu-wrapper");
            let topBannerOverlay = document.querySelector(".top-banner-overlay");
            menuWrapper.classList.remove("is-opened");
            topBannerOverlay.classList.remove("is-moved");
        }
            //menuWrapper.classList.add("is-opened");
            //topBannerOverlay.classList.add("is-moved");
            

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}


//Funcion para eliminar articulos del carrito
let eliminarArticuloCarrito = ($idVenta) => {
    let $token_carrito = window.localStorage.getItem('token_carrito');
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/elimina/${$token_carrito}?idVenta=${$idVenta}`,
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        setTimeout(function () {
            obtenerArticulosCarritoModal();
            if (!data) {
                swal({
                    title: "Error", 
                    text: "Ocurrió un error al eliminar el artículo. Intente nuevamente.", 
                    type: "error",
                    confirmButtonText: "Aceptar"
                });
            }
        }, 500);
        obtenerArticulosCarritoModal3();
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

//Funcion para actualizar articulos del carrito
let actualizarArticuloCarrito = ($idVenta, $cantidad, $idArticulo) => {
    console.log("Entra aqui cuando cambia la cantidad de uno en uno");
    let $token_carrito = window.localStorage.getItem('token_carrito');
    console.log("Cantidad: " + $cantidad);
    console.log($idArticulo+ "articulo");
    console.log();
    var cantidadInventario = 0;
    var cantidadArticuloCarrito = 0;
    var nuevaCantidad=0;

    setTimeout(function(){
        obtenerArticulosCarritoModal();
    }, 1500);
    if ( ($idArticulo == $art1 || $idArticulo == $art2 || $idArticulo == $art3) && $cantidad > 1) {
        swal({
            title: "¡Aviso!",
            text: "Solo puede adquirir una unidad de este producto",
            type: "warning",
            confirmButtonText: "Aceptar"
        });
    } else {
    $.ajax({
        url: `${$base_backend}/api/usuario/carrito/update/${$token_carrito}?idVenta=${$idVenta}&cantidad=${$cantidad}&idArticulo=${$idArticulo}`,
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {

        console.log(data[0]);
        console.log(data[1]);
        // obtenerArticulosCarritoModal();
        if (data[0] < $cantidad) {
            swal({
                title: "ALERTA",
                text: "Actualmente contamos con " +data[0]+" unidades de este producto",
                type: "warning",
                confirmButtonText: "Aceptar"
            });
            $.ajax({
                url: `${$base_backend}/api/usuario/carrito/update/${$token_carrito}?idVenta=${$idVenta}&cantidad=${$cantidad}&idArticulo=${$idArticulo}`,
                method: 'POST',
                contentType: 'application/json',
                dataType: 'json'
            }).done((data, textStatus, jqXHR) => {s
                if (!data) {
                    swal({
                        title: "Error",
                        text: "Ocurrió un error al actualizar la cantidad. Intente nuevamente.",
                        type: "error",
                        confirmButtonText: "Aceptar"
                    });
                }
                
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });  
        } else {
            swal({
                title: "Éxito",
                text: "El producto se actualizó en el carrito.",
                type: "success",
                showConfirmButton: false,
                timer: 1500
                // confirmButtonText: "Aceptar"
            });
        }
        // obtenerArticulosCarritoModal(); 
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
    }
}

//Funcion para verificar si el JWT existe en sessionStorage
let isLogged = () => {
    if (!$token) {
        //Mostrar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').show();
        $('.logged').hide();
    } else {
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        $('.unlogged').hide();
        $('.logged').show();
    }
}

//Funcion para verificar si el usuario tiene al menos una direccion
let existeDireccion = () => {
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
            return data;
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }else{
        return false;
    }
    
}


//Funcion para cerrar sesion
let doLogout = () => {
    window.sessionStorage.removeItem('token');
    window.sessionStorage.removeItem('idUsuario');
    window.sessionStorage.removeItem('nombre');
    window.sessionStorage.removeItem('appaterno');
    window.sessionStorage.removeItem('apmaterno');
    window.sessionStorage.removeItem('email');
    window.location = 'index.html';
}

$(document).ajaxStop(function () {
    $('.preloader').delay(500).fadeOut(500);
});


//SOLO INSERTA NUMEROS
function onlynum(e) {
    key = e.keyCode || e.which;
    teclado = String.fromCharCode(key);
    numeros = "0123456789";
    especiales = "8-37-38-46";
    teclado_especial = false;
    for (var i in especiales) {
        if (key == especiales[i]) {
            teclado_especial = true;
        }
    }
    if (numeros.indexOf(teclado) == -1 && !teclado_especial) {
        return false;
    }
}

$(document).ready(($) => {

    console.log("Es la base: ");

    if (window.localStorage.getItem("token_carrito") === null) {
        let token = generarTokenCarrito();
        window.localStorage.setItem("token_carrito", token);
        console.log('Se genero nuevo token_carrito -> ' + window.localStorage.getItem('token_carrito'));
    } else {
        console.log('Ya existe token_carrito -> ' + window.localStorage.getItem('token_carrito'));
        //existeDireccion();
    }

    obtenerArticulosCarritoModal();
    window.sessionStorage.setItem('ExistenciaDir', "false");
    existeDireccion();

    //Evento para agregar al carrito
    $('body').on('click', '.read-more', function () {
        let $idArticulo = $(this).attr('data-idArticulo');
        let $pvp = $(this).attr('data-pvpConDescuento');
        console.log("pvpCon descuento: " + $pvp);
        console.log("id desde index: " + $idArticulo);

        if ($pvp == 0) {
            swal({
                title: "Error",
                text: "No puede agregar este artículo al carrito. Intente con otro artículo.",
                type: "error",
                confirmButtonText: "Aceptar"
            });
        } else {
            agregarArticuloCarrito($idArticulo, 1);
        }
        
    });

    $('body').on('click', '.menu-open', function () {
        obtenerArticulosCarritoModal2();
    });

    //Evento para agregar al carrito
    $('body').on('click', '.read-more-2', function () {
        let $idArticulo = $(this).attr('data-idArticulo');
        let $pvp = $(this).attr('data-pvpConDescuento');
        let $cantidad = $('#cantidad-fromDetalle').val();
        console.log("pvpCon descuento: " + $pvp);
        if ($pvp == 0) {
            swal({
                title: "Error",
                text: "No puede agregar este artículo al carrito. Intente con otro artículo.",
                type: "error",
                confirmButtonText: "Aceptar"
            });
        } else {
            agregarArticuloCarrito($idArticulo, $cantidad);
        }
    });

    //Evento para eleminiar del carrito
    $('body').on('click', '.btn-quitCart', function () {
        let $idVenta = $(this).attr('idventaexpontanea');
        eliminarArticuloCarrito($idVenta);
    });

    // Evento para actualizar del carrito
    $('body').on('click', '.btn-shopping', function () {
        console.log("CAMBIA AL DAR CLICK EN EL BOTON");
        let $idVenta = $(this).parent().parent().find('.input-cantidad-compra').attr('idventaexpontanea');
        let $cantidad = $(this).parent().parent().find('.input-cantidad-compra').val();
        let $idArticulo = $(this).parent().parent().find('.input-cantidad-compra').attr('idArticulo');
        console.log($idVenta);
        actualizarArticuloCarrito($idVenta, $cantidad, $idArticulo);
    });

    //Evento para filtrar articulos por categoria
    $('body').on('click', '.btn-categoria', function () {
        console.log("Evento");
        let $idCategoria = $(this).attr('data-idcategoria');
        let $idAnidada = $(this).attr('data-idaniada');
        let $nCat = ($(this).html()).toLowerCase();
        let $aux = $nCat.replace(/\s/g, "-");
        console.log($aux);
        let $vino = "";
        if($idCategoria === "10" || $idCategoria === "16" || $idCategoria === "18" || $idCategoria === "34" || 
        $idCategoria === "36" || $idCategoria === "48" || $idCategoria === "58" || $idCategoria === "63" ||
        $idCategoria === "68"){
            console.log("vino");
            $vino = "vino-";
        }
        if($idCategoria !== "75"){
            window.localStorage.setItem("idCategoria", $idCategoria);
            window.localStorage.setItem("idAniada", $idAnidada);    

            window.location = 'categoria.html?'+ $vino + $aux + "-" + $idCategoria +"-"+ $idAnidada;
        } else {
            window.localStorage.setItem("idCategoria", $idCategoria);
            window.location = 'canasta-navideña.html?'+"canasta-navideña"+"-" + $idCategoria +"-"+ $idAnidada;
        }

        
    });

    //Evento para ver detalle del articulo
    // $('body').on('click', '.btn-detalle', function () {
    //     let $idArticulo = $(this).attr('data-idArticulo');

    //     window.localStorage.setItem("idArt", $idArticulo);
        
    //     window.location = 'detalle.html';
    // });


    $('body').on('click', '.btn-detalle', function () {
        let $idArticulo = $(this).attr('data-idArticulo');
        let $nombre =  (($(this).parents(".contentProductos").find(".item-title").text()).replace(/\./g, "")).toLowerCase();
        console.log($nombre);
        let aux = $nombre.replace(/\s/g, "-");
        console.log(aux);
        window.localStorage.setItem("idArt", $idArticulo);
        console.log("detalle.html/"+aux+$idArticulo);
        window.location = "detalle.html?"+aux+"-"+$idArticulo;
    });

    // $('body').on('click', '.articulo-buscador', function () {
    //     let $idArticulo = $(this).attr('data-idArticulo');

    //     window.localStorage.setItem("idArt", $idArticulo);
        
    //     window.location = 'detalle.html?$idArticulo';
    // });
    $('body').on('click', '.articulo-buscador', function () {
        let $idArticulo = $(this).attr('data-idArticulo');
        let $nombre =  (($(this).parents(".contenedor-url").find(".contente-buscador-producto").text()).replace(/\./g, "").replace(/\"/g, "")).toLowerCase();
        let aux = $nombre.replace(/\s/g, "-");
        // window.localStorage.setItem("idArt", $idArticulo);
        window.location = "detalle.html?"+aux+"-"+$idArticulo;
    });

    //Evento para cerrar sesion
    $('.btn-logout').on('click', function () {
        doLogout();
    });

});


    // BUSCADOR
    $(function () {
        var searchInput = $(".main-search");
        var randoContainer = $('.random-article iframe');
        var articleTemplateSource = $("#article-template").html();
        var articleTemplate = Handlebars.compile(articleTemplateSource)
        var articleCardContainer = $('.article-cards-container');
        var article = {};
        var articleRender;
        $(".main-search").autocomplete({
            appendTo: ".search-typeahead",
            position: {
                my: "left top",
                at: "left top",
                of: ".search-typeahead"
            },
            source: function (request, response) {
            }
        });
        $('.search-button').on('click', function () {
            var searchTerm = searchInput.val();
            
            if (searchTerm === ""){
                console.log("ENTRA AQUI PORQUE ESTA VACIO");
                articleCardContainer.hide();
                $('.contenedor-buscador').css({
                    'margin-top': '-340px',
                });
            } else{
            articleCardContainer.html('');
            let aux = "";
            
            $.ajax({
                async: true,
                url: `${$base_backend}/api/articulos/typeahead?stringSearch=${searchTerm}`,
                method: "GET",
                contentType: 'application/json',
                data: {
                    busqueda: searchTerm
                },
                dataType: "json",
            }).done((data, textStatus, jqXHR) =>{
                console.log(data);
                if (data == '') {
                    
                    aux = '<div class="article-card sin-articulos">'
                    + '</div>';
                } else {
                    console.log(data);
                    data.forEach(value =>{
                        aux += '<div class="article-card row">'
                        + '    <div class="col-md-5 col-6">'
                        + '        <img  class="imagen-buscador"  src="' + value.imagen1 + '" >'
                        + '    </div>'
                        + '    <div class="col-md-7 col-6 contenedor-url">'
                        + '        <p class="contente-buscador-producto">"' + value.descripcion + '"</p>'
                        //+ '        <a href="VerDetalle.do?idArticulo=' + value.id + '">	'
                        + '                <button  data-idArticulo="'+ value.id  +'  " class="btn btn-default articulo-buscador">Ver artículo</button>'
                        + '        </a>'
                        + '    </div>'
                        + '</div>';
                    });       
                } 
            //articleRender = articleTemplate(article);
                articleCardContainer.append(aux);
                console.log(aux);
                return data;
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
            });
            randoContainer.hide();
            articleCardContainer.show();
            
            }
            
        });
    });
    $('.desplega-buscador').click(function () {
        $('.contenedor-buscador').css({
            'margin-top': '460px',
        });
    });
    function delay(callback, ms) {
    var timer = 0;
    return function() {
        var context = this, args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
        callback.apply(context, args);
        }, ms || 0);
    };
    }
    // Example usage:
    $('#busc').keyup(delay(function (e) {
    $("#enter-buscador").click();
    }, 500));
    // Autocomplete
    $('.cerrar-buscador').click(function () {
        $('.contenedor-buscador').css({
            'margin-top': '-340px',
        });
    });
    $('.main-search').click(function () {
        $('.main-search').css({
            'z-index': '1',
        });
    });
    $('.cerrar-buscador').click(function () {
        $('.main-search').css({
            'z-index': '3',
        });
    });

    // Cierra el buscador y limpia el value
    function clearFields() {
        document.getElementById("busc").focus();
    }

    //Limpia input
    function limpia(elemento)
    {
        elemento.value = "";
    }
    function verifica(elemento)
    {
        if (elemento.value == "")
            elemento.value = "Buscar producto...";
    };
    // Limpia input y oculta boton cerrar buscador
    $("#busc").on('keyup', function() {
        if ($("#busc").val() == "") {
          $(".main-search").addClass("index-3");
        } else {
          $(".main-search").removeClass("index-3")
        }
      });
    // Abre buscador & oculta carrito
    $(".main-search").click(function(){
        $(".menu-wrapper").removeClass("is-opened");
    });
    // Abre buscador & oculta menu
    $(".main-search").click(function(){
        $("#nav-toggle").removeClass("active");
        $('.nav-list').css({
            'display': 'none',
        });
    });
    // Abre carrito & oculta buscador
    $('.shop-qty').click(function () {
        $('.contenedor-buscador').css({
            'margin-top': '-340px',
        });
    });
    // Abre carrito & oculta menu
    $('.shop-qty').click(function () {
        $("#nav-toggle").removeClass("active");
        $('.nav-list').css({
            'display': 'none',
        });
    });
    // Abre menu & cierra carrito & buscador 
    $("#nav-toggle").click(function(){
        $(".menu-wrapper").removeClass("is-opened");
        $('.contenedor-buscador').css({
            'margin-top': '-340px',
        });
    });
    
    
