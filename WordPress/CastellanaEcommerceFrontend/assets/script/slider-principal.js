var swiper = new Swiper('.swiper-container', {
  spaceBetween: 0,
  slidesPerView: 4,
  loop: true,
  speed: 4000,
  autoplay: {
    delay: 800,
  },
  effect: 'fade',
  fadeEffect: {
    crossFade: true
  },
  pagination: {
    el: '.swiper-pagination',
    clickable: true,
  },
  // navigation: {
  //     nextEl: '.swiper-button-next',
  //     prevEl: '.swiper-button-prev',
  //   },

});

// SLIDER RECOMENDACIONES

const carouselMasVendidos_options = {
  rtl: false, // If RTL Make it true & .slick-slide{float:right;}
  autoplay: true,
  autoplaySpeed: 3000, //  Slide Delay
  speed: 400, // Transition Speed
  slidesToShow: 4, // Number Of Carousel
  slidesToScroll: 1, // Slide To Move
  pauseOnHover: false,
  appendArrows: $(".containerReco .contentReco .arrowsReco"), // Class For Arrows Buttons
  prevArrow: '<span class="Slick-Prev"></span>',
  nextArrow: '<span class="Slick-Next"></span>',
  easing: "linear",
  responsive: [
    {
      breakpoint: 801,
      settings: {
        slidesToShow: 3
      }
    },
    {
      breakpoint: 641,
      settings: {
        slidesToShow: 1
      }
    },
    {
      breakpoint: 481,
      settings: {
        slidesToShow: 1
      }
    }
  ]
}

// SLIDER PROMOCIONES

const carouselPromociones_options = {
  rtl: false, // If RTL Make it true & .slick-slide{float:right;}
  autoplay: true,
  autoplaySpeed: 3000, //  Slide Delay
  speed: 400, // Transition Speed
  slidesToShow: 4, // Number Of Carousel
  slidesToScroll: 1, // Slide To Move
  pauseOnHover: false,
  appendArrows: $(".containerPromo .contentPromo .arrowsPromo"), // Class For Arrows Buttons
  prevArrow: '<span class="Slick-Prev"></span>',
  nextArrow: '<span class="Slick-Next"></span>',
  responsive: [
    {
      breakpoint: 801,
      settings: {
        slidesToShow: 4
      }
    },
    {
      breakpoint: 641,
      settings: {
        slidesToShow: 1
      }
    },
    {
      breakpoint: 481,
      settings: {
        slidesToShow: 1
      }
    }
  ]
}

// SLIDER NUEVOS PRODUCTOS

const carouselNuevos_options = {
  rtl: false, // If RTL Make it true & .slick-slide{float:right;}
  autoplay: false,
  autoplaySpeed: 5000, //  Slide Delay
  speed: 400, // Transition Speed
  slidesToShow: 4, // Number Of Carousel
  slidesToScroll: 1, // Slide To Move
  pauseOnHover: false,
  appendArrows: $(".containerReco .contentReco .arrowsReco3"), // Class For Arrows Buttons
  prevArrow: '<span class="Slick-Prev"></span>',
  nextArrow: '<span class="Slick-Next"></span>',
  easing: "linear",
  responsive: [
    {
      breakpoint: 1051,
      settings: {
        slidesToShow: 3
      }
    },
    {
      breakpoint: 801,
      settings: {
        slidesToShow: 3
      }
    },
    {
      breakpoint: 681,
      settings: {
        slidesToShow: 1
      }
    },
    {
      breakpoint: 481,
      settings: {
        slidesToShow: 1
      }
    }
  ]
}


















