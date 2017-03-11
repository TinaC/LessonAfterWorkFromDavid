/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


function generateJson() {
    var object = {};
    object["HITOE_ID"] = "0";
    var time = new Date().getTime();
    object["TIME"] = '/Date(' + time + ')/';
    object["LATITUDE"] = "30"
    object["LONGITUDE"] = "120"
    object['LF_HF_DIVISION'] = "0";
    object['BPM'] = Math.random() * 10 + 0;
    object['SDNN'] = Math.random() * 20 + 0;
    return object;
}

var app = {
    // Application Constructor
    initialize: function() {
        var request = new XMLHttpRequest();
        // var params = "action=something";
        var url = "http://10.58.121.158:8001/ctsdemo_live/CTSDemo_Live/services/TRIP_DATA.xsodata/TRIP_DATA";
        request.open('POST', url, true);
        request.onreadystatechange = function() {if (request.readyState==4) alert("It worked!");};
        request.setRequestHeader ("Authorization", "Basic Q1RTX0xJVkU6QWExMTExMTE=")
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        
        // request.setRequestHeader("Content-length", params.length);
        request.setRequestHeader("Connection", "close");
        request.send();

        // function createCORSRequest(method, url){
        //     var xhr = new XMLHttpRequest();
        //     if ("withCredentials" in xhr){
        //         xhr.open(method, url, true);
        //     } else if (typeof XDomainRequest != "undefined"){
        //         xhr = new XDomainRequest();
        //         xhr.open(method, url);
        //     } else {
        //         xhr = null;
        //     }
        //     return xhr;
        // }

        // var request = createCORSRequest("post", "http://10.58.121.158:8001/ctsdemo_live/CTSDemo_Live/services/TRIP_DATA.xsodata/TRIP_DATA");
        // if (request){
        //     request.onload = function() {
        //         // ...
        //     };
        //     request.onreadystatechange = handler;
        //     request.send();
        // }

        // var posturl = "http://10.58.121.158:8001/ctsdemo_live/CTSDemo_Live/services/TRIP_DATA.xsodata/TRIP_DATA";
                    
        // var postdata = generateJson();
        // console.log(JSON.stringify(postdata));

        // $.ajax({
        //         type: 'POST',
        //         data: JSON.stringify(postdata),
        //         contentType: 'application/json',
        //         url: posturl,
        //         crossDomain: true,
        //         beforeSend: function (xhr) {
        //             xhr.setRequestHeader ("Authorization", "Basic Q1RTX0xJVkU6QWExMTExMTE=");
        //         },
        //         success: function()
        //         {
        //             alert("ajax success!");
        //         },
        //         error: function(jqXHR, textStatus, errorThrown) {
        //             alert("ajax error!");
        //             console.log(textStatus, errorThrown);
        //         }
        // })

        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();