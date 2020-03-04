$(document).ready(function() {

    var dataSet = [
      
    ]

    var columnDefs = [{
    title: "Producto"
    },{
    title: "Cantidad"
    }];

    var myTable;

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
            
            $('#clientes').on('click', '.logo-cliente', function () {
    var name = $('.logo-cliente', this).eq(1).text();
    $('#modalCliente').modal("show");
            });
    
  });
    

  