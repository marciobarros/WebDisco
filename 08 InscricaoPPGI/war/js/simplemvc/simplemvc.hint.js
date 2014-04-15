(function($) {

	$.widget("ui.hint", {  

    	options: {
   			hint: ""
    	}, 
        	
	 	_create : function() {
	 		var self = this;
	 		$("<div class='tooltipcontainer'><div class='tooltip'>" + this.options.hint + "</div></div>").appendTo(this.element);

	 		self.element.mouseenter(function() {
	 			self.element.find(".tooltipcontainer").attr("style", "display: inline;");
	 			self.element.find(".tooltip").attr("style", "display: block;");
 			});
	 		
	 		self.element.mouseleave(function() {
	 			self.element.find(".tooltipcontainer").attr("style", "display: none;");
	 			self.element.find(".tooltip").attr("style", "display: none;");
	 		});
	 	},
	 	
	 	destroy: function() {
	 		this.element.next().remove(); 
	 		this.element.unbind("mouseenter");
	 		this.element.unbind("mouseleave");
	 	},
	});
	
})(jQuery);