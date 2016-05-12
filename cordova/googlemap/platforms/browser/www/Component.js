sap.ui.define(['sap/ui/core/UIComponent'],
	function(UIComponent) {

	var Component = UIComponent.extend("sap.m.sample.ToggleButton.Component", {

		metadata : {
			rootView : "sap.m.sample.ToggleButton.Page",
			dependencies : {
				libs : [
					"sap.m"
				]
			},
			config : {
				sample : {
					stretch : true,
					files : [
						"Page.view.xml",
						"Page.controller.js"
					],
				}
			}
		}
	});

	return Component;

});