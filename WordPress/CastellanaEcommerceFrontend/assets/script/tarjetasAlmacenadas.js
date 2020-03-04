$(document).ready(function () {
    /**
     * Credenciales MP La Castellana Cuenta: castellanaonline@lacastellana.com
     * 
     * Obtener credenciales en el link https://www.mercadopago.com/mlm/account/credentials?type=basic
     * 
     * Para generar el TOKEN de la tarjeta se utiliza PUBLIC_KEY
     */
    //const PUBLIC_KEY_MP = "TEST-8a918572-ee18-4ae2-834b-13c44d77487d";
    //PRODUCCION LA CASTELLANA
     const PUBLIC_KEY_MP = "APP_USR-e01eafd5-86f5-4092-a156-7ebb217a5e77";
    
    Mercadopago.setPublishableKey(PUBLIC_KEY_MP);

    function addEvent(el, eventName, handler) {
        if (el.addEventListener) {
            el.addEventListener(eventName, handler);
        } else {
            el.attachEvent('on' + eventName, function () {
                handler.call(el);
            });
        }
    }
    ;

    doSubmit2 = false;
    addEvent(document.querySelector('#pay2'), 'submit', doPay2);

    $("#btn-pago-mp2").on("click", function(e){
        var $form = document.querySelector('#pay2');
//            console.log('cardExpirationMonth: ' + $("#cardExpirationMonth").val());
//            console.log('cardExpirationYear: ' + $("#cardExpirationYear").val());
            //Mercadopago.createToken($form, sdkResponseHandler); // The function "sdkResponseHandler" is defined below     
    })

    function doPay2(event) {
        event.preventDefault();
        if (!doSubmit2) {
            var $form2 = document.querySelector('#pay2');
            console.log("Token");
            Mercadopago.createToken($form2, sdkResponseHandler); // The function "sdkResponseHandler2" is defined below

            return false;
        }
    };

    function sdkResponseHandler(status, response) {
        console.log("Se entro al Handler almacenada");
        console.log(status);
        console.log(response);
        if (status != 200 && status != 201) {
            //alert("verify filled data");
            console.log("verify filled data Form2- \n" + JSON.stringify(response));

            if (response.cause[0].code == 'E301') {
                swal({title: "Error", text: "Tarjeta inválida.", type: "error"});
            } else {
                swal({title: "Error", text: "Tarjeta inválida.", type: "error"});
            }
        } else {
            let calleT = $("#calleTarjetaA").val();
            let cpTarjeta = $("#cpTarjetaA").val();
            let  nTarjeta = $("#numeroTarjetaA").val();

            if( calleT.trim() !== "" && cpTarjeta !== "" && nTarjeta !== "" ){
//            console.log("Se entró al handler----sfsgerge: \n" + JSON.stringify(response));
            //var form = document.querySelector('#pay2');
            //var card = document.createElement('input');
            //card.setAttribute('name', 'token');
            //card.setAttribute('type', 'hidden');
            //card.setAttribute('value', response.id);
            //form.appendChild(card);
            doSubmit2=true;
            //form.submit();
            
            //var tipoEnvio = $("#total-costoEnvio").attr("data-tipoEnvio");
            $("#btn-pago-mp2").html('<i class="fa fa-spinner fa-spin"></i>&nbsp;Procesando');
            const $token = window.sessionStorage.token;
            const $tokenC =window.localStorage.getItem("token_carrito");
            let $idCli = $('#ClientIdd').val();
            let $idCa = $('#cardId').val();
            let $inst = $('#instllmentss').val();
            

            $.ajax({
                url: `${$base_backend}/api/ventas/pagos/registrada/`,
                method: "POST",
                contentType: 'application/json',
                dataType: "json",
                headers: {
                    'Authorization': `Bearer ${$token}`
                },
                data:JSON.stringify({
                    clienteId: $idCli,
                    costoEnvio: window.sessionStorage.CostoEnv,
                    costoTotal: $("#totalPedido").text().replace("$", "").replace(/,/g, ""),
                    idCard: $idCa,
                    idDireccion: window.sessionStorage.idDirEnvi,
                    // installments: $inst,
                    installments: "1",
                    tipoEnvio: "Terrestre",
                    token: response.id,
                    tokenCarrito : $tokenC,
                    totalCompra: parseFloat(window.sessionStorage.CostoEnv),
                    calleTarjeta: calleT,
                    cpTarjeta: cpTarjeta,
                    numCalleTarjera: nTarjeta
                })
            }).done((data, textStatus, jqXHR) => {
                console.log(data);
                $("#btn-pago-mp2").html('<i class="fa fa-check"></i>&nbsp;Confirmar pago');
                let $operation = JSON.parse(data.operation);
                let $statusResponse = data.status;
                let $status_detail = data.status_detail;
                let $full_json = JSON.parse(data.paymentObject);
                let $paymentObject = data.paymentObject;
                let $paymentId = $full_json.id;
 
                if ($operation) {
                    //     console.log("paymentId: " + paymentId + " statusResponse: " + statusResponse + " status_detail: " + status_detail);

                    var statusES = "", detalleES = "", swalTitle = "", swalType = "", swalText = "", locationText = "";

                    if ($statusResponse == "approved") {
                        statusES = "Aprobada";
                        swalTitle = "Éxito";
                        swalType = "success";
                        swalText = "El pago ha sido aprobado.";
                        if ($status_detail == "accredited") {
                            detalleES = "Acreditada";
                        } else {
                            detalleES = $status_detail;
                        }
                    //         locationText = "./MisPedidos.do";
                    } else {
                        swalTitle = "Error";
                        swalType = "error";
                        swalText = "Verifique los datos ingresados.";
                        statusES = $statusResponse;
                        detalleES = $status_detail;
                        locationText = "./ConfirmacionCompra.do";
                    }

                    swal({
                        title: swalTitle,
                        text: swalText + "<br/><strong>ID:</strong> " + $paymentId + "</br><strong>Estatus:</strong> " + statusES + " </br><strong>Detalle:</strong> " + detalleES,
                        type: swalType,
                        confirmButtonText: "Aceptar",
                        html: true
                    }, function () {
                        window.location.href = "lista-pedidos.html";;}
                         );
                }else {
                    swal({title: "Error", text: "Ocurrió un error al procesar el pago.", type: "error", confirmButtonText: "Aceptar"});
                }
                
            }).fail((qXHR, textStatus, errorThrown) => {
                console.error(qXHR);
                console.error(textStatus);
                console.error(errorThrown);
                });
            }else{
                swal({
                
                    title : "Los campos no pueden estar vacíos",
                    confirmButtonText: "Aceptar",
                    type : "warning",
                    showCloseButton: false,
                    showConfirmButton : true    
                    });
            }
            
        }
    };

    jQuery.fn.ForceNumericOnly =
    function()
    {
    return this.each(function()
    {
        $(this).keydown(function(e)
        {
            var key = e.charCode || e.keyCode || 0;
            // allow backspace, tab, delete, enter, arrows, numbers and keypad numbers ONLY
            // home, end, period, and numpad decimal
            return (
                key == 8 || 
                key == 9 ||
                key == 13 ||
                key == 46 ||
                key == 110 ||
                key == 190 ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
    });
}

$("#numeroTarjetaA").ForceNumericOnly();
$("#cpTarjetaA").ForceNumericOnly();

});