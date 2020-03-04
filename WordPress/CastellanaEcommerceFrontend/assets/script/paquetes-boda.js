$(document).ajaxStop(function () {
    $('.preloader').delay(500).fadeOut(500);
});

let llenarTablaBodas = ($array) => {
    let $numCol = 3;
    let $tbody = '';

    $('#tablaBodas > tbody').empty();

    for (let i = 0; i < $array.length; i = i + $numCol) {
        let $subarray = $array.slice(i, i + $numCol);

        console.log($subarray);

        $tbody += '<tr><td class="row">';

        $subarray.forEach(($item, i) => {

            let precioDescuento = '';
            if ($item.porcentajeDescuento != 0) {
                precioDescuento += '<label class="precio-regular">' + format($item.pvpConImp) + '</label>';
            }

            $tbody += '<div class="col-lg-4 col-md-4 offset-md-0 col-10 offset-1">'
                + '    <div class="contentProductos">'
                + '        <figure class="snip1313">'
                + '            <a href="#!" class="btn-detalle" data-idArticulo="' + $item.id + '">'
                + '                <div class="carrusel-item" style="background-image: url(' + $item.imagen1 + ');"></div>'
                + '            </a>'
                + '            <a href="#!">'
                + '                <span class="read-more" data-idArticulo="' + $item.id + '">'
                + '                    Agregar&nbsp;<img src="../../../assets/images/shopping-bag-white.png">'
                + '                </span>'
                + '            </a>'
                + '        </figure>'
                + '        <div class="container-producto">'
                + '            <p class="item-title ">' + $item.descripcion + '</p>'
                + '            <div class="pull-down">'
                + precioDescuento + '<label class="precio-actual">' + format($item.pvpConDesc) + '&nbsp;MXN</label>'
                + '            </div>'
                + '        </div>'
                + '    </div>'
                + '</div>';
        });

        for (let j = 0; j < ($numCol - $subarray.length); j++) {
            $tbody += '';
        }

        $tbody += '</td></tr>';
    }

    $('#tablaBodas > tbody').append($tbody);

    initDatatableBodas();
}

let initDatatableBodas = () => {
    $('#tablaBodas').DataTable({
        "lengthMenu": [[5, 10, 20, -1], [15, 30, 60, "Todos"]],
        "aaSorting": [],
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
        },
    });
}

let getPaquetesBodas = ($idCategoria) => {
    // console.log(`${$base_backend}/api/articulos/paquetesBoda/${$idCategoria}`);
    $.ajax({
        url: `${$base_backend}/api/articulos/paquetesBoda/${$idCategoria}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        var dataTable = $('#tablaBodas').DataTable();
        dataTable.destroy();
        llenarTablaBodas(data);
    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}

$(document).ready($ => {
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
    } else {
        //Ocultar opciones Iniciar sesión y Registrarse de navbar
        console.log("no");
        $('.unlogged').hide();
        $('.logged').show();
    }

    let $idCategoria = window.localStorage.getItem('idCategoria');

    console.log("idCategoria: " + $idCategoria);

    getPaquetesBodas($idCategoria);

    
});    