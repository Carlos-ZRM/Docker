
          $(".panel-collapse").on("show.bs.collapse", function() {
          $(this)
              .siblings(".panel-heading")
              .addClass("active");
          });
  
          $(".panel-collapse .title ").on("hide.bs.collapse", function() {
          $(this)
              .siblings(".panel-heading .title")
              .removeClass("active");
          });

          L.mapbox.accessToken = 'pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY5YzJzczA2ejIzM29hNGQ3emFsMXgifQ.az9JUrQP7klCgD3W-ueILQ';
          var geojson = [
              {
              "type": "FeatureCollection",
              "features": [
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.290935,
                      19.391286 
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "5087 1757",
                      "phone": "5087 1757",
                      "address": "Avenida Jesús del Monte 21",
                      "city": "Hacienda de las Palmas",
                      "country": "CDMX",
                      "crossStreet": "interlomas@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.171230,
                      19.345829, 
                      ],
                  },
                    
                  "properties": {
                      "phoneFormatted": "(55) 5554 1170",
                      "phone": "(55) 5554 1170",
                      "address": "Miguel Ángel de Quevedo,",
                      "city": "Coyoacán",
                      "country": "CDMX",
                      "crossStreet": "coyoacan@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.159019,
                      19.294589
                      ]
                  },
                    
                  "properties": {
                      "phoneFormatted": "(55) 5573 4791",
                      "phone": "(55) 5573 4791",
                      "address": "Viaducto Tlalpan 841,",
                      "city": "Tlalpan",
                      "country": "CDMX",
                      "crossStreet": "tlalpan@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.220797,
                      19.436248 
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(55) 5294 5652",
                      "phone": "(55) 5294 5652",
                      "address": "Fuente de Tritones 35-B",
                      "city": "Lomas de Tecamachalco",
                      "country": "CDMX",
                      "crossStreet": "tecamachalco@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.211242,
                      19.316613 
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(55) 5135 4763",
                      "phone": "(55) 5135 4753",
                      "address": "Avenida de las Fuentes 556",
                      "city": "Jardines del Pedregal México",
                      "country": "CDMX",
                      "crossStreet": "pedregal@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [
                        -99.210118,
                        19.428020 
                        ]
                    },
                    "properties": {
                        "phoneFormatted": "(55) 5520 8886",
                        "phone": "(55) 5520 8886",
                        "address": "Monte Altai 55",
                        "city": "Lomas de Chapultepec",
                        "country": "CDMX",
                        "crossStreet": "lomas@lacastellana.com",
                        "postalCode": "",
                        "state": ""
                    }
                    },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.190714,
                      19.351054 
                      ]
                  }, 
                  "properties": {
                      "phoneFormatted": "(55) 5616-2162 / (55) 5616-7386",
                      "phone": "(55) 5616-2162 / (55) 5616-7386",
                      "address": "Av. Revolución 1541 Esquina Ma. Luisa",
                      "city": "San Ángel",
                      "country": "CDMX",
                      "crossStreet": "sanangel@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.1910125,
                      19.4333628
                      ]
                  },  
                  "properties": {
                      "phoneFormatted": "(55) 7601 9728 ",
                      "phone": "(55) 7601 9728 ",
                      "address": "Horacio Esquina Newton, Polanco V Sección, 11570",
                      "city": "Miguel Hidalgo",
                      "country": "CDMX",
                      "crossStreet": "polanco@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -99.1388538,
                      19.3967388
                      ]
                  },  
                  "properties": {
                      "phoneFormatted": "56 9898 90  ",
                      "phone": "56 9898 90  ",
                      "address": "Bismarck #5, La Moderna, 03510",
                      "city": "Benito Juárez",
                      "country": "CDMX",
                      "crossStreet": "info@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {// *******Querétaro*******
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.4415252,
                      20.6941674
                      ]
                  }, 
                  "properties": {
                      "phoneFormatted": "(442) 393 0385 ",
                      "phone": "(442) 393 0385 ",
                      "address": "Boulevard de las Ciencias 200, Plaza Ihop Juriquilla",
                      "city": "Querétaro",
                      "country": "Querétaro",
                      "crossStreet": "juriquilla@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.4086586,
                      20.5714529
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(442) 193 5837",
                      "phone": "(442) 193 5837",
                      "address": " Paseo Constituyentes 45",
                      "city": "Lomas de Querétaro, Querétaro",
                      "country": "Querétaro",
                      "crossStreet": "campestre@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {// ***********Monterrey**********
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -103.430502,
                      25.562387 
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(87) 1717 7406",
                      "phone": "(87) 1717 7406",
                      "address": "Río Pánuco 790, Navarro",
                      "city": "Torreón",
                      "country": "Coahuila",
                      "crossStreet": "torreon@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.114143,
                      25.776822 
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(87) 1717 7406",
                      "phone": "(87) 1717 7406",
                      "address": "Aeropuerto Monterrey Terminal A",
                      "city": "Monterrey",
                      "country": "Monterrey",
                      "crossStreet": "aeropuertomty@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.3618569,
                      25.654459
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "(81) 2317 0029 al 32",
                      "phone": "(81) 2317 0029 al 32",
                      "address": "Calzada del Valle 350 Oriente, Del Valle",
                      "city": "Monterrey",
                      "country": "Monterrey",
                      "crossStreet": "valle@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.313963,
                      25.6348501
                      ]
                  },
                  "properties": {
                      "phoneFormatted": "1930 1900",
                      "phone": "1930 1900",
                      "address": "Calle del Refugio 4110, Colonia Alfareros, 64753",
                      "city": " Nuevo León ",
                      "country": " Nuevo León ",
                      "crossStreet": "martha@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  },
                  {//************Cancún***********
                //   "type": "Feature",
                //   "geometry": {
                //       "type": "Point",
                //       "coordinates": [
                //       -86.8544803,
                //       21.1119399
                //       ]
                //   }, 
                //   "properties": {
                //       "phoneFormatted": "(998) 886-9551 / (998) 886-9391",
                //       "phone": "(998) 886-9551 / (998) 886-9391",
                //       "address": "Av. Huayacán Lte-61, Mz-141, SM-310 Zona Comercial Residencial Palmaris",
                //       "city": "Cancún",
                //       "country": "Cancún",
                //       "crossStreet": "cancun@lacastellana.com",
                //       "postalCode": "",
                //       "state": ""
                //   }
                //   },
                //   {
                  "type": "Feature",
                  "geometry": {
                      "type": "Point",
                      "coordinates": [
                      -100.4415252,
                      20.6941674
                      ]
                  }, 
                  
                  "properties": {
                      "phoneFormatted": "(998) 886 9551 / (998) 886 9391",
                      "phone": "(998) 886 9551 / (998) 886 9391",
                      "address": "Av. Huayacán Lt 61, Mz 141, SM-310",
                      "city": "Cancún ",
                      "country": "Cancún",
                      "crossStreet": "cancun@lacastellana.com",
                      "postalCode": "",
                      "state": ""
                  }
                  }
              ]
              }
          ];
          var map = L.mapbox.map('map')
              .setView([19.345829, -99.171230], 12)
              .addLayer(L.mapbox.styleLayer('mapbox://styles/mapbox/light-v10'));
          
          map.scrollWheelZoom.disable();
          
          var listings = document.getElementById('listings');
          var locations = L.mapbox.featureLayer().addTo(map);
          
          locations.setGeoJSON(geojson);
          
          function setActive(el) {
              var siblings = listings.getElementsByTagName('div');
              for (var i = 0; i < siblings.length; i++) {
              siblings[i].className = siblings[i].className
              .replace(/active/, '').replace(/\s\s*$/, '');
              }
          
              el.className += ' active';
          }
          
          locations.eachLayer(function(locale) {
          
              // Shorten locale.feature.properties to just `prop` so we're not
              // writing this long form over and over again.
              var prop = locale.feature.properties;
          
              // Each marker on the map.
              var popup = '<h3>La Castellana</h3><div>' + prop.address;
          
              var listing = listings.appendChild(document.createElement('div'));
              listing.className = 'item';
          
              var link = listing.appendChild(document.createElement('a'));
              link.href = '#';
              link.className = 'title';
          
              link.innerHTML = prop.address;
              if (prop.crossStreet) {
              link.innerHTML += '<br /><small class="quiet">' + prop.crossStreet + '</small>';
              popup += '<br /><small class="quiet">' + prop.crossStreet + '</small>';
              }
          
              var details = listing.appendChild(document.createElement('div'));
              details.innerHTML = prop.city;
              if (prop.phone) {
              details.innerHTML += ' &middot; ' + prop.phoneFormatted;
              }
          
              link.onclick = function() {
              setActive(listing);
          
              // When a menu item is clicked, animate the map to center
              // its associated locale and open its popup.
              map.setView(locale.getLatLng(), 16);
              locale.openPopup();
              return false;
              };
          
              // Marker interaction
              locale.on('click', function(e) {
              // 1. center the map on the selected marker.
              map.panTo(locale.getLatLng());
          
              // 2. Set active the markers associated listing.
              setActive(listing);
              });
          
              popup += '</div>';
              locale.bindPopup(popup);
          
              locale.setIcon(L.icon({
              iconUrl: 'https://www.stickpng.com/assets/images/58889219bc2fc2ef3a1860aa.png',
              iconSize: [50, 70],
              iconAnchor: [28, 28],
              popupAnchor: [0, -34]
              }));
          });
          