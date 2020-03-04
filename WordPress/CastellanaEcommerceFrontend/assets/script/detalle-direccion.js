$(document).ajaxStop(function () {
	$('.preloader').delay(500).fadeOut(500);
});

$(document).ready(($) => {

    let detalleDireccion = ($idDireccion) =>{
        $.ajax({
            url: `${$base_backend}/api/usuario/direcciones/detalle/${$idDireccion}`,  // Cuando mandas a llamar el id de la variable colocar '$'
            method: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                'Authorization': `Bearer ${$token}}`
            }
        }).done((data, textStatus, jqXHR) => {
            $("#tipoDireccion2").val(data.tipo);
            $("#destinatario2").val(data.destinatario);
            $("#calle2").val(data.calle);
            $("#numExterior2").val(data.numExterior);
            $("#numInterior2").val(data.numInterior);
            $("#codigoPostal2").val(data.codigoPostal);
            $("#ciudad2").val(data.ciudad);
            $("#estado2").val(data.estado);
            $("#referencias2").val(data.referencia);
            $("#numTelefonico2").val(data.numeroTelefonico);
            $("#extension2").val(data.extensionTelefonica);
            // $("#colonia2").append('<option value="'+data.colonia+'|'+data.estado+'|'+data.municipio+'|'+data.estado+'" selected="">'+data.colonia+'</option>')
            $("#colonia2").val(data.colonia);
            $("#municipio2").val(data.municipio);
            $('#btn-editDireccion').attr('data-editdireccion', data.idDireccion);
            console.log(data); 

            
        }).fail((qXHR, textStatus, errorThrown) => {
            console.error(qXHR);
            console.error(textStatus);
            console.error(errorThrown);
        });
    }
    
    // detalleDireccion(); Variable para ejecutar Ajax & pintar el done  

    //Evento para detalle de la dirección
    $('body').on('click', '.editar-direccion', function () {
        let $idDireccion = $(this).attr('data-iddireccion'); 
        console.log($idDireccion)
        $("#editarDireccion")[0].reset();
        // Como guardar el "id en el storage"
        // window.sessionStorage.setItem('idDireccion', $idDireccion); 
        // Como guardar el "id en el storage"
        detalleDireccion($idDireccion); // Como obtener la Id data del evento
        
    });


});