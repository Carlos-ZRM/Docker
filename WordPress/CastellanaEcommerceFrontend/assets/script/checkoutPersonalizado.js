$(document).ready(($) => {

    /**
     * Credenciales MP La Castellana Cuenta: castellanaonline@lacastellana.com
     * 
     * Obtener credenciales en el link https://www.mercadopago.com/mlm/account/credentials?type=basic
     * 
     * Para generar el TOKEN de la tarjeta se utiliza PUBLIC_KEY
     */
    /**
    * Crendeciales MercadoPago
    */
    //SANDBOX LA CASTELLANA
    //const PUBLIC_KEY_MP = "TEST-8a918572-ee18-4ae2-834b-13c44d77487d";
    //PRODUCCION LA CASTELLANA
    const PUBLIC_KEY_MP = "APP_USR-e01eafd5-86f5-4092-a156-7ebb217a5e77";

    Mercadopago.setPublishableKey(PUBLIC_KEY_MP);

    var tipoTarjeta = "";

    function addEvent(el, eventName, handler) {
        if (el.addEventListener) {
            el.addEventListener(eventName, handler);
        } else {
            el.attachEvent('on' + eventName, function () {
                handler.call(el);
            });
        }
    }

    function getBin() {
        var ccNumber = document.querySelector('input[data-checkout="cardNumber"]');
        return ccNumber.value.replace(/[ .-]/g, '').slice(0, 6);

        var cardSelector = document.querySelector("#cardId");
        if (cardSelector && cardSelector[cardSelector.options.selectedIndex].value != "-1") {
            return cardSelector[cardSelector.options.selectedIndex].getAttribute('first_six_digits');
        }

    }

    function clearOptions() {
        var bin = getBin();
        if (bin.length == 0) {
            var selectorIssuer = document.querySelector("#issuer"),
                    fragment = document.createDocumentFragment(),
                    option = new Option("--SELECCIONAR--", '-1');

            selectorIssuer.options.length = 0;
            fragment.appendChild(option);
            selectorIssuer.appendChild(fragment);
            selectorIssuer.setAttribute('disabled', 'disabled');

            var selectorInstallments = document.querySelector("#installments"),
                    fragment = document.createDocumentFragment(),
                    option = new Option("--SELECCIONAR--", '-1');

            selectorInstallments.options.length = 0;
            fragment.appendChild(option);
            selectorInstallments.appendChild(fragment);
            selectorInstallments.setAttribute('disabled', 'disabled');
        }
    }

    function guessingPaymentMethod(event) {
        var bin = getBin();
        amount = document.querySelector('#amount').value;

        if (event.type == "keyup") {
            if (bin.length >= 6) {
                Mercadopago.getPaymentMethod({
                    "bin": bin
                }, setPaymentMethodInfo);
            }
        } else {
            setTimeout(function () {
                if (bin.length >= 6) {
                    Mercadopago.getPaymentMethod({
                        "bin": bin
                    }, setPaymentMethodInfo);
                }
            }, 100);
        }
    }

    function setPaymentMethodInfo(status, response) {
        if (status == 200) {
            /*
             * response contiene toda la info relacionada a la tarjeta
             */
            console.log("Respuesta setPaymentMethodInfo");
            console.log(response);

            var form = document.querySelector('#pay');

            if (document.querySelector("input[name=paymentMethodId]") == null) {
                var paymentMethod = document.createElement('input');
                paymentMethod.setAttribute('name', "paymentMethodId");
                paymentMethod.setAttribute('type', "hidden");
                paymentMethod.setAttribute('value', response[0].id);

                form.appendChild(paymentMethod);
            } else {
                document.querySelector("input[name=paymentMethodId]").value = response[0].id;
            }

            // check if the security code (ex: Tarshop) is required
            var cardConfiguration = response[0].settings,
                    bin = getBin(),
                    amount = document.querySelector('#amount').value;

            for (var index = 0; index < cardConfiguration.length; index++) {
                if (bin.match(cardConfiguration[index].bin.pattern) != null && cardConfiguration[index].security_code.length == 0) {
                    /*
                     * In this case you do not need the Security code. You can hide the input.
                     */
                } else {
                    /*
                     * In this case you NEED the Security code. You MUST show the input.
                     */
                    $("#securityCode").attr("maxlength", cardConfiguration[index].security_code.length);
                }
            }

            Mercadopago.getInstallments({
                "bin": bin,
                "amount": amount
            }, setInstallmentInfo);


            // check if the issuer is necessary to pay
            var issuerMandatory = false,
                    additionalInfo = response[0].additional_info_needed;

            for (var i = 0; i < additionalInfo.length; i++) {
                if (additionalInfo[i] == "issuer_id") {
                    issuerMandatory = true;
                }
            }
            ;
            if (issuerMandatory) {
                Mercadopago.getIssuers(response[0].id, showCardIssuers);
                addEvent(document.querySelector('#issuer'), 'change', setInstallmentsByIssuerId);
            } else {
                document.querySelector("#issuer").style.display = 'none';
                document.querySelector("#issuer").options.length = 0;
            }
        }

        var responseImg = '';

        //console.log(response[0].id);

        if (response[0].id == 'amex') {
            responseImg = 'american-express.png';
            tipoTarjeta = "amex";
        } else if (response[0].id == 'master') {
            responseImg = 'mastercard.png';
            tipoTarjeta = "master";
        } else if (response[0].id == 'visa') {
            responseImg = 'visa.png';
            tipoTarjeta = "visa";
        } else if (response[0].id == 'debmaster') {
            responseImg = 'mastercard.png';
            tipoTarjeta = "debmaster";
        } else if (response[0].id == 'debvisa') {
            responseImg = 'visa.png';
            tipoTarjeta = "debvisa";
        }


//        console.log('security'+JSON.stringify(response[0].settings[0].security_code.length));
        $("#imgCard").html("<span><img src='../../../assets/images/payments/" + responseImg + "' height='60' width='75' alt='Card' style='margin-left: -20px; margin-top: -10px;'></span>");

        $("#securityLength").html("<span class='text-muted' style='font-size: 10px;'>Últimos " + response[0].settings[0].security_code.length + " números que están al reverso</span>");
    }

    addEvent(document.querySelector('input[data-checkout="cardNumber"]'), 'keyup', guessingPaymentMethod);
    addEvent(document.querySelector('input[data-checkout="cardNumber"]'), 'keyup', clearOptions);
    addEvent(document.querySelector('input[data-checkout="cardNumber"]'), 'change', guessingPaymentMethod);
//    cardsHandler();



    doSubmit = false;

    addEvent(document.querySelector('#pay'), 'submit', doPay);

    $("#btn-pago-mp").on("click", function(e){
        var $form = document.querySelector('#pay');
//            console.log('cardExpirationMonth: ' + $("#cardExpirationMonth").val());
//            console.log('cardExpirationYear: ' + $("#cardExpirationYear").val());
            Mercadopago.createToken($form, sdkResponseHandler); // The function "sdkResponseHandler" is defined below     
    })
    function doPay(event) {

        console.log("----------------------------------------------------");
        event.preventDefault();
        if (!doSubmit) {
            var $form = document.querySelector('#pay');
//            console.log('cardExpirationMonth: ' + $("#cardExpirationMonth").val());
//            console.log('cardExpirationYear: ' + $("#cardExpirationYear").val());
            Mercadopago.createToken($form, sdkResponseHandler); // The function "sdkResponseHandler" is defined below          
            return false;
        }
    }

    function showCardIssuers(status, issuers) {
        
        var issuersSelector = document.querySelector("#issuer"),
                fragment = document.createDocumentFragment();

        issuersSelector.options.length = 0;
        var option = new Option("--SELECCIONAR--", '-1');
        fragment.appendChild(option);

        for (var i = 0; i < issuers.length; i++) {
            if (issuers[i].name != "default") {
                option = new Option(issuers[i].name, issuers[i].id);
            } else {
                option = new Option("Otro", issuers[i].id);
            }
            fragment.appendChild(option);
        }
        issuersSelector.appendChild(fragment);
        issuersSelector.removeAttribute('disabled');
        document.querySelector("#issuer").removeAttribute('style');
    }
    ;

    function setInstallmentsByIssuerId(status, response) {
        console.log(response);

        var issuerId = document.querySelector('#issuer').value,
                amount = document.querySelector('#amount').value;

        if (issuerId === '-1') {
            return;
        }

        Mercadopago.getInstallments({
            "bin": getBin(),
            "amount": amount,
            "issuer_id": issuerId
        }, setInstallmentInfo);
    }
    ;

    function setInstallmentInfo(status, response) {

        //console.log(response);

        var selectorInstallments = document.querySelector("#installments"),
                fragment = document.createDocumentFragment();

        selectorInstallments.options.length = 0;

        if (response.length > 0) {
            var option = new Option("--SELECCIONAR--", '-1'),
                    payerCosts = response[0].payer_costs;

            fragment.appendChild(option);
            for (var i = 0; i < payerCosts.length; i++) {
                option = new Option(payerCosts[i].recommended_message || payerCosts[i].installments, payerCosts[i].installments);
                fragment.appendChild(option);
            }
            selectorInstallments.appendChild(fragment);
            selectorInstallments.removeAttribute('disabled');
        }
    }
    ;

    function cardsHandler() {
        clearOptions();
        var cardSelector = document.querySelector("#cardId"),
                amount = document.querySelector('#amount').value;

        if (cardSelector && cardSelector[cardSelector.options.selectedIndex].value != "-1") {
            var _bin = cardSelector[cardSelector.options.selectedIndex].getAttribute("first_six_digits");
            Mercadopago.getPaymentMethod({
                "bin": _bin
            }, setPaymentMethodInfo);
        }
    }

    function sdkResponseHandler(status, response) {
        console.log("ENTRA AQUI");
        if ( $("#issuer").val() === "-1" ){
            swal({title: "Error", text: "Seleccione un banco", type: "error", confirmButtonText: "Aceptar"});
        } else 
        if (status != 200 && status != 201) {
            if (response.cause[0].code == 'E301') {
                swal({title: "Error", text: "Tarjeta inválida.", type: "error", confirmButtonText: "Aceptar"});
            } else {
                swal({title: "Error", text: "Tarjeta inválida.", type: "error", confirmButtonText: "Aceptar"});
            }
        } else {

            let calleT = $("#calleTarjeta").val();
            let cpTarjeta = $("#cpTarjeta").val();
            let  nTarjeta = $("#numeroTarjeta").val();

            if( calleT.trim() !== "" && cpTarjeta !== "" && nTarjeta !== "" ){
             

            doSubmit = true;
            console.log(status);
            console.log(response);
            console.log(response.id);

            var items = [];

            $('#tablaProd  > ul').each(function () {

                var descripcion = $(this).find("#nombreProducto").text();
                var precioUnitario = $(this).find("#precioProducto").text().replace("$", "");
                var cantidad = $(this).find("#cantidadProducto").text();

                items.push({
                    title: descripcion,
                    unit_price: parseFloat(precioUnitario),
                    quantity: parseInt(cantidad)
                });
            });

            items.push({
                title: "COSTO DE ENVÍO",
                unit_price: parseFloat($("#total-costoEnvio").text().replace("$", "").replace(/,/g, "")),
                quantity: parseInt(1)
            });

            var tipoEnvio = $("#total-costoEnvio").attr("data-tipoEnvio");



            console.log("calle: " + calleT);
            console.log("CP: " + cpTarjeta);
            console.log("no calle: " + nTarjeta);

            

            $("#btn-pago-mp").html('<i class="fa fa-spinner fa-spin fa-fw"></i>&nbsp;Procesando');
            const $token = window.sessionStorage.token;
            const $tokenC =window.localStorage.getItem("token_carrito")
            //console.log(response);
            //console.log(items);
            items.pop();
            items.reverse();
            items.pop();
            //console.log(items);
            //items = JSON.stringify(items);
            //console.log(items);
            let $envio = $("#envioProducto").text().replace("$", "").replace(/,/g, "") //
            if ($envio == "Free") {
                $envio = "0";
            }
            datos_env = JSON.stringify({
                costoEnvio: $envio,
                costoTotal: $("#totalPedido").text().replace("$", "").replace(/,/g, ""),
                idDireccion: $("#direccion-destino").attr("data-idDireccion"),
                installments: "1",
                issuerID: $("#issuer").val(),
                items: items,
                // items: [
                //     {
                //       "quantity": 1,
                //       "title": "string",
                //       "unit_price": 10
                //     }
                //   ],
                tipoEnvio: tipoEnvio,
                tipoTarjeta: tipoTarjeta,
                token: response.id,
                tokenCarrito : $tokenC
            });
            console.log(datos_env);
            $.ajax({
                url: `${$base_backend}/api/ventas/pagos/nueva/`,
                method: "POST",
                contentType: 'application/json',
                dataType: "json",
                headers: {
                    'Authorization': `Bearer ${$token}`
                },
                data:JSON.stringify({
                    costoEnvio: $envio,
                    costoTotal: $("#totalPedido").text().replace("$", "").replace(/,/g, ""),
                    idDireccion: window.sessionStorage.idDirEnvi,
                    installments: "1",
                    issuerID: $("#issuer").val(),
                    items: items,
                    tipoEnvio: "Terrestre",
                    tipoTarjeta: tipoTarjeta,
                    token: response.id,
                    tokenCarrito : $tokenC,
                    calleTarjeta: calleT,
                    cpTarjeta: cpTarjeta,
                    numCalleTarjera: nTarjeta
                })
            }).done((data, textStatus, jqXHR) => {
                console.log(data);
                $("#btn-pago-mp").html('<i class="fa fa-check"></i>&nbsp;Confirmar pago');
                let $operation = JSON.parse(data.operation);
                let $statusResponse = data.status;
                let $status_detail = data.status_detail;
                let $full_json = JSON.parse(data.paymentObject);
                let $paymentObject = data.paymentObject;
                let $paymentId = $full_json.id;

                console.log($paymentId);
                console.log("EMTRP AL DONE");
                console.log("OPERATION " + $operation);
                console.log("PAYMENT " + $paymentId);
                console.log("sTATUS RESPONSE " + $statusResponse);
                console.log("sTATUS DETAIL " + $status_detail);
                console.log("PAYMENT OBJECT " + $paymentObject);
                console.log("Json " + $full_json)

                if ($operation) {
                    //     console.log("paymentId: " + paymentId + " statusResponse: " + statusResponse + " status_detail: " + status_detail);

                    var statusES = "", detalleES = "", swalTitle = "", swalType = "", swalText = "", locationText = "", redirect = "";
                    console.log("ESTATUS RESPUESTA: " + $statusResponse);
                    if ($statusResponse == "approved") {
                        statusES = "Aprobada";
                        swalTitle = "Éxito";
                        swalType = "success";
                        swalText = "El pago ha sido aprobado.";
                        if ($status_detail == "accredited") {
                            detalleES = "Acreditada";
                            redirect = "lista-pedidos.html";
                        } else {
                            detalleES = $status_detail;
                            redirect = "confirmar-pago.html"
                        }
                    //         locationText = "./MisPedidos.do";
                    } else {
                        console.log("Entra aca");
                        swalTitle = "Error";
                        swalType = "error";
                        swalText = "Verifique los datos ingresados.";
                        statusES = $statusResponse;
                        detalleES = $status_detail;
                        locationText = "./ConfirmacionCompra.do";

                        redirect = "confirmar-pago.html"
                    }

                    swal({
                        title: swalTitle,
                        text: swalText + "<br/><strong>ID:</strong> " + $paymentId + "</br><strong>Estatus:</strong> " + statusES + " </br><strong>Detalle:</strong> " + detalleES,
                        type: swalType,
                        confirmButtonText: "Aceptar",
                        html: true
                    }, function () {
                        window.location.href = redirect;}
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

    $("#numeroTarjeta").ForceNumericOnly();
    $("#cpTarjeta").ForceNumericOnly();
});