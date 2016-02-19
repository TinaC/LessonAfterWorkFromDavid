sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast",
	"sap/ui/model/json/JSONModel"
], function (Controller, MessageToast, JSONModel) {
	"use strict";
// sap.ui.define allows us to specify dependencies we would like to use in our controller and have the framework load them asynchronously.


// We define the App controller in its own file by extending the "Controller" object of the OpenUI5 core. 
	return Controller.extend("sap.ui.demo.wt.controller.App", {

// onInit is one of OpenUI5’s lifecycle methods that is invoked by the framework when the controller is created, similar to a constructor function of a control.
		onInit : function () {
			// set data model on view
			var oData = {
				recipient : {
					name : "Tina"
				}
			};
			var oModel = new JSONModel(oData);
			// To be able to use this model from within the XML view we call the setModel function on the view and pass in our newly created model.
			this.getView().setModel(oModel);
		}

		onShowHello : function () {
			MessageToast.show("Hello World");
		}
	});

});