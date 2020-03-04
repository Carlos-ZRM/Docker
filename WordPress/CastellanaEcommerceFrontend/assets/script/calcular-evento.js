let calcularEvento = (np) => {
    // const $token = window.sessionStorage.token;
    $.ajax({
        url: `${$base_backend}/api/usuario/calcularEvento/${np}`,
        method: 'GET',
        contentType: 'application/json',
        dataType: 'json'
        // headers: {
        //     'Authorization': `Bearer ${$token}}`
        // },
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        var myTable = $('#clientes').DataTable();
        myTable.destroy();
        var dataSet= [];
        if (np === '50' || np === '80' || np === '100' || np === '150' || np === '200' || np === '250' || np === '300' ||
        np === '350' || np === '400' || np === '450' || np === '500' || np === '550' || np === '600' || np === '700' || np === '800'){
            dataSet = [
                ["RON", data.ron, ],
                ["VODKA", data.vodka, ],
                ["TEQUILA", data.tequila_mezcal, ],
                ["MEZCAL", data.tequila_mezcal, ],
                ["COGNAG/BRANDY", data.brandy_cognac, ],
                ["WHISKY", data.whisky, ],
                ["VINO TINTO", data.vinoTinto, ],
                ["VINO BLANCO/ROSADO", data.vinoBlanco_Rosado, ],
                ["ANÍS", data.anis, ],
                ["LICOR/CREMA", data.licor_crema, ],
                ["CHAMPAGNE/VINO ESPUMOSO", data.champagne_espumoso, ],
                ["GINEBRA", data.ginebra, ],
                ["REFRESCOS", data.refrescos, ],
                ["CERVEZA", data.cerveza, ],
                ["HIELO 5 kgs", data.hielo5kg, ],
              ]                               
        } else {
            dataSet = [
                ["RON", Math.round( (np * data.ron )/100), ],
                ["VODKA", Math.round( ( np * data.vodka)/100), ],
                ["TEQUILA", Math.round( ( np * data.tequila_mezcal)/100), ],
                ["MEZCAL", Math.round( ( np * data.tequila_mezcal)/100), ],
                ["COGNAG/BRANDY", Math.round( ( np * data.brandy_cognac)/100), ],
                ["WHISKY", Math.round( ( np * data.whisky)/100), ],
                ["VINO TINTO", Math.round( ( np * data.vinoTinto)/100), ],
                ["VINO BLANCO/ROSADO", Math.round( ( np * data.vinoBlanco_Rosado)/100), ],
                ["ANÍS", Math.round( ( np * data.anis)/100), ],
                ["LICOR/CREMA", Math.round( ( np * data.licor_crema)/100), ],
                ["CHAMPAGNE/VINO ESPUMOSO", Math.round( ( np * data.champagne_espumoso)/100), ],
                ["GINEBRA", Math.round( ( np * data.ginebra)/100), ],
                ["REFRESCOS", Math.round( ( np * data.refrescos)/100), ],
                ["CERVEZA", Math.round( ( np * data.cerveza)/100), ],
                ["HIELO 5 kgs", Math.round( ( np * data.hielo5kg)/100), ],
              ]       
        }
        
        var columnDefs = [{
            title: "Producto"
            },{
            title: "Cantidad"
            }];
        var columnDefs = [{
            title: "Producto"
            },{
            title: "Cantidad"
            }];
        

        myTable = $('#clientes').DataTable({
            "sPaginationType": "full_numbers",
            data: dataSet,
            columns: columnDefs,
                      dom: 'Bfrtip',    
            select: 'single',
            // scrollX: true,
            // responsive: true,
            altEditor: true,    // Enable altEditor
            "language": {
              "url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
            },
            buttons: [{
              text: 'Agregar Producto',
                          name: 'add',        // do not change name
                          className:'boton-cliente'
                      },
            {
              extend: 'selected', // Bind to Selected row
              text: 'Editar',
                    name: 'edit',        // do not change name
                    className:'boton-editar'
            },
            {
              extend: 'selected', // Bind to Selected row
              text: 'Eliminar',
                      name: 'delete',      // do not change name
                      className:'boton-eliminar'
                },
                {
                    extend: 'collection',
                    text: 'Exportar',
                    className:'boton-export',
                    buttons: [
                            'copy',
                            'excel',
                            'csv',
                            'pdf',
                            'print'
                    ]
                }]
      
              });
        

    }).fail((qXHR, textStatus, errorThrown) => {
        console.error(qXHR);
        console.error(textStatus);
        console.error(errorThrown);
    });
}


$(document).ready(($) => {

    $('#calcular').on('click', function () {
        calcularEvento( $('#personas').val() );
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
        
});