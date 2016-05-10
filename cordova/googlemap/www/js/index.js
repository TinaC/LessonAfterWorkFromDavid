sap.ui.getCore().attachInit(function() {
    new sap.m.App ({
        pages: [
            new sap.m.Page({
                title: "CTS", 
                enableScrolling : false,
                content: [ new sap.ui.core.ComponentContainer({
                    height : "100%", name : "sap.m.sample.ToggleButton"
                })]
            })
        ]
    }).placeAt("content");
});

// var app = {
//     // Application Constructor
//     initialize: function() {
//         this.bindEvents();
//     },
//     // Bind Event Listeners
//     //
//     // Bind any events that are required on startup. Common events are:
//     // 'load', 'deviceready', 'offline', and 'online'.
//     bindEvents: function() {
//         document.addEventListener('deviceready', this.onDeviceReady, false);
//     },
//     // deviceready Event Handler
//     //
//     // The scope of 'this' is the event. In order to call the 'receivedEvent'
//     // function, we must explicitly call 'app.receivedEvent(...);'
//     onDeviceReady: function() {
//         app.receivedEvent('deviceready');
//     },
//     // Update DOM on a Received Event
//     receivedEvent: function(id) {
//         var parentElement = document.getElementById(id);
//         var listeningElement = parentElement.querySelector('.listening');
//         var receivedElement = parentElement.querySelector('.received');

//         listeningElement.setAttribute('style', 'display:none;');
//         receivedElement.setAttribute('style', 'display:block;');

//         console.log('Received Event: ' + id);
//         window.init("test", function(m){});
//         setInterval(function(){
//             window.hitoe_echo("test", function(m) { alert(m);  });
//         }, 1000);
//         //window.hitoe_echo("test", function(m) { alert(m);  });
//     }
// };

// app.initialize();


