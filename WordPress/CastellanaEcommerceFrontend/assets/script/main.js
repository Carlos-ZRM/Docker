
  // PRELOADER 
  $(document).ready(function () {  
    $(window).on("load",function(){
      $('.preloader').delay(100).fadeOut(500);
    })
  })
  //END OF PRELOADER

  //CLICK- MENU BURGER
  $(function() {
    $(".toggle-menu").click(function() {
      $(".exo-menu").toggleClass("display");
    });
  });
  //END OF MENU BURGER

  // MENU BURGER


  //END MENU BURGER 
  
  // HOVER DE LA IMAGEN DE GOURMET / SUCURSALES 
  $(".hover").mouseleave(
    function () {
      $(this).removeClass("hover");
    }
  );
  // END HOVER DE LA IMAGEN DE GOURMET / SUCURSALES 

 //DROPDOWN
  
 (function($) {
  // Begin jQuery
  $(function() {
  // DOM ready
    // Si un enlace tiene un menú desplegable, agregue el conmutador de submenú.
    $("nav ul li a:not(:only-child)").click(function(e) {
      $(this)
        .siblings(".nav-dropdown")
        .slideToggle("slow");
      //Cerrar un desplegable al seleccionar otro.
      $(".nav-dropdown")
        .not($(this).siblings())
        .hide();
      e.stopPropagation();
    });
    // Al hacer clic fuera del menú desplegable se eliminará la clase desplegable
    $("html").click(function() {
      $(".nav-dropdown-close").hide();
    });
    // Conmutar abrir y cerrar estilos de navegación al hacer clic
    $("#nav-toggle").click(function() {
      $("nav .ul").slideToggle();
    });
    // Hamburguesa para alternar X
    $("#nav-toggle").on("click", function() {
      this.classList.toggle("active");
    });
  }); // end DOM ready
  })(jQuery); // end jQuery

  //END DROPDOWN

  //DROPDOWN MEGAMENU

  $(".megadropdown").on("click", function(e) {
    e.preventDefault();

    if ($(this).hasClass("open")) {
      $(this).removeClass("open");
      $(this)
        .children("ul")
        .slideUp("fast");
    } else {
      $(this).addClass("open");
      $(this)
        .children("ul")
        .slideDown("fast");
    }
  });
  //DROPDOWN MEGAMENU

  // TIEMPO SLIDER PRINCIPAL

  setInterval(function(){$(".next-show").click();}, 8500);

  // TIEMPO SLIDER PRINCIPAL


      $(".close-div").click(function() {
        $(".close-div").fadeOut("normal", function() {
            $(this).remove();
        });
    }); 



    $(document).ready(function() {
      $(".contentBox").click(function(){
          var boxHeight = $(this).height();
          var minBoxHeight = 40;

          if (boxHeight <= minBoxHeight) {
              $(this).css('height', 'auto');
              var tmp_height = $(this).height();
              $(this).css('height', boxHeight+'px');
              
              $(this).animate({
              height : tmp_height+'px'
              
              }, 800);
          } else {
              $(this).css({
                  height: '40px',
                  transition: 'height 800ms, background 800ms, box-shadow 800ms'
              });
          }
      });
  });

  // Acción para disminuir botella en el carrito de compras
  $("body").on("click", ".btn-disminuir", function () {
    console.log("Entra para disminuir");
    $(this).prop("disabled", true);
    var cantidad = parseInt($(this).parent().next().val());
    var nuevaCantidad;
    if ((cantidad -1) < 1) {
        nuevaCantidad = 1;
    } else {
        nuevaCantidad = (cantidad - 1);
    }
    // $(this).parent().next().val(nuevaCantidad).trigger("change");
    $(this).parent().next().val(nuevaCantidad);
    let param1 = $(this);
    let param2 = nuevaCantidad;
    setTimeout(function(param1, param2){
      changeCantidad(param1, param2);
    },1000,param1, param2);
  });


// Acción para aumentar botella en el carrito de compras
  $("body").on("click", ".btn-aumentar", function () {
  // $(".btn-aumentar").mousedown(function () {
    $(this).prop("disabled", true);
      var cantidad = parseInt($(this).parent().prev().val());
      console.log("Parent "+$(this).parent().prev());
      cantidad += 1;
      $(this).parent().prev().val(cantidad);
      //$(this).parent().prev().val(cantidad + 1).trigger("change");
      let param1 = $(this);
      let param2 = cantidad;
      setTimeout(function(param1,param2){
        changeCantidad(param1,param2);
      },1000,param1,param2);
      
      // $(this).prop("disabled", false);
  });

  //Remover el articulo del carrito
  $( document ).ready(function(){
    
    $( ".removeItem" ).click(function() {
      $( ".contenedor-item" ).fadeOut( "slow", function() {
        // After animation completed:
        $( ".contenedor-item" ).remove();
      });
      $( this ).fadeOut( "slow", function() {
        // After animation completed:
        $( this ).remove();
      });
    });
    
  });

  // Media Query para la activación del acordeon de filtro de botellas
  jQuery(document).ready(function($) {
    var alterClass = function() {
      var ww = document.body.clientWidth;
      if (ww < 768) {
        $('.panel-collapse').addClass('collapse');
      } else if (ww >= 768) {
        $('.panel-collapse').removeClass('collapse');
      };
    };
    $(window).resize(function(){
      alterClass();
    });
    //Fire it when the page first loads:
    alterClass();
  });

  // Incluir fragmentos de HTML 

  function includeHTML() {
    var z, i, elmnt, file, xhttp;
    /*loop through a collection of all HTML elements:*/
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
      elmnt = z[i];
      /*search for elements with a certain atrribute:*/
      file = elmnt.getAttribute("include-html");
      if (file) {
        /*make an HTTP request using the attribute value as the file name:*/
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4) {
            if (this.status == 200) {elmnt.innerHTML = this.responseText;}
            if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
            /*remove the attribute, and call this function once more:*/
            elmnt.removeAttribute("include-html");
            includeHTML();
          }
        }      
        xhttp.open("GET", file, true);
        xhttp.send();
        /*exit the function:*/
        return;
      }
    }
  };

  includeHTML();

  // Efecto rubberBand al dar click en agregar al carrito 
  $(function () {
      $('.mini-contact').on('click', function () {
          var $el = $('#an'),
              c = 'rubberBand animated';
          $('.contact-section').fadeIn(1, function () {
              $el.addClass(c);
          }).delay(1000).show(0, function () {
              $el.removeClass(c)
          });
      });

  });

// LOGIN DE FACEBOOK

// $( document ).ready(function() {

//   $( "#showToast" ).click(function() {
//     $('.toast').toast('show');
//   });
    
// });

// BOTÓN CATÁLOGO

  (function() {
    var haveDarkHeader = false;
    
    $(window).scroll(function() {
      var wantDarkHeader = $(window).scrollTop() >= 500;
      
      if (wantDarkHeader != haveDarkHeader) {
      haveDarkHeader = wantDarkHeader;
      $(".clearHeader").toggleClass("darkHeader", haveDarkHeader);
      }
    });
  })();

