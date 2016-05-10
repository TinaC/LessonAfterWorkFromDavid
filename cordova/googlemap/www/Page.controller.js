sap.ui.define(['sap/m/MessageToast','sap/ui/core/mvc/Controller'],
	function(MessageToast, Controller) {

	var PageController = Controller.extend("sap.m.sample.ToggleButton.Page", {
		onPress: function (evt) {
			if (evt.getSource().getPressed()) {

		    document.addEventListener("deviceready", onDeviceReady, false);
		    function onDeviceReady() {
		        alert("navigator.geolocation works well");

				if(!navigator.geolocation) {
					alert("Geolocation is not supported by this browser.");
				}

				var Latitude = undefined;
				var Longitude = undefined;

				getMapLocation();
				// Get geo coordinates

				function getMapLocation() {

				    navigator.geolocation.getCurrentPosition
				    (onMapSuccess, onMapError, { enableHighAccuracy: true });
				}

				// Success callback for get geo coordinates

				var onMapSuccess = function (position) {

				    Latitude = position.coords.latitude;
				    Longitude = position.coords.longitude;

				    getMap(Latitude, Longitude);

				}

				// Get map by using coordinates

				function getMap(latitude, longitude) {

				    var mapOptions = {
				        center: new google.maps.LatLng(0, 0),
				        zoom: 1,
				        mapTypeId: google.maps.MapTypeId.ROADMAP
				    };

				    map = new google.maps.Map
				    (document.getElementById("map"), mapOptions);


				    var latLong = new google.maps.LatLng(latitude, longitude);

				    var marker = new google.maps.Marker({
				        position: latLong
				    });

				    marker.setMap(map);
				    map.setZoom(15);
				    map.setCenter(marker.getPosition());
				}

				// Success callback for watching your changing position

				var onMapWatchSuccess = function (position) {

				    var updatedLatitude = position.coords.latitude;
				    var updatedLongitude = position.coords.longitude;

				    if (updatedLatitude != Latitude && updatedLongitude != Longitude) {

				        Latitude = updatedLatitude;
				        Longitude = updatedLongitude;

				        getMap(updatedLatitude, updatedLongitude);
				    }
				}

				// Error callback

				function onMapError(error) {
				    console.log('code: ' + error.code + '\n' +
				        'message: ' + error.message + '\n');
				}

				// Watch your changing position

				function watchMapPosition() {

				    return navigator.geolocation.watchPosition
				    (onMapWatchSuccess, onMapError, { enableHighAccuracy: true });  
				}

		    }
				//bluetooth
			    // window.init("test", function(m){});

			    // get hitoe data
		        // setInterval(function(){
		        //     window.hitoe_echo("test", function(m) { alert(m);  });
		        // }, 1000);
				document.getElementById("__button0-content").innerHTML = "Stop";
				console.log(evt.getSource().getPressed())
			} else {
				document.getElementById("__button0-content").innerHTML = "Start";
				console.log("stop post");
			}	
		}
	});

	return PageController;

});
