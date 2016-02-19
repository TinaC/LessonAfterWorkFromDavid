sap.ui.define([
	"sap/ui/core/mvc/Controller"
], function (Controller) {
	"use strict";

// We define the App controller in its own file by extending the "Controller" object of the OpenUI5 core. 
	return Controller.extend("sap.ui.demo.wt.controller.App", {

		onShowHello : function () {
			// show a native JavaScript alert
			alert("Hello World");
		}
	});

});