(function($) {

	$.widget("ui.rolloutbox", {  

		/**
		 * Options for the roll-out box
		 */
    	options: {
    		title: "",
    		autoOpen: true,
    		height: 300,
   			onOpen: function (pane) {},
			onClose: function (pane) {}
    	}, 
        	
    	/**
    	 * Creates the roll-out box
    	 */
	 	_create : function() { 
	 		this._update();
	 	},
	 	
    	/**
    	 * Destroy the roll-out box instance
    	 */
	 	destroy: function() {  
	 	},
	 	
    	/**
    	 * Changes options for the roll-out box
    	 */
	 	_setOption: function(option, value) {  
	 	    $.Widget.prototype._setOption.apply(this, arguments);  
        	this._update();
	 	},
	 	
    	/**
    	 * Updates the data in the roll-out box
    	 */
	 	_update : function() { 
	 		var self = this;
	 		var content = this.element.html();
	 		this.element.empty();
	 		this.element.attr('class', 'rolloutbox-major');
	 		
            var divTitle = $('<div class="rolloutbox-title">' + this.options.title + '</span>')
	            .appendTo(this.element);
            
            var divContent = $('<div class="rolloutbox-content"></span>')
	            .appendTo(this.element)
	            .css('height', this.options.height)
	            .css('display', this.options.autoOpen ? 'block' : 'none')
	            .html(content);

        	var imageName = this.options.autoOpen ? 'collapse' : 'expand';
            
            $('<div class="rolloutbox-collapse"><img src="/img/icons/' + imageName + '.png"></div>')
            	.appendTo(divTitle)
            	.click(function(e) {
                    e.preventDefault();
                    if (divContent.css('display') == 'none')
                    {
                    	self.options.onOpen(divContent);
                    	divContent.slideDown();
                    	divTitle.find('.rolloutbox-collapse').html('<img src="/img/icons/collapse.png">');
                    }
                    else
                    {
                    	self.options.onClose(divContent);
                    	divContent.slideUp();
                    	divTitle.find('.rolloutbox-collapse').html('<img src="/img/icons/expand.png">');
                    }
	               	return false;
            	});
            
            if (this.options.autoOpen)
            	this.options.onOpen(divContent);
	 	}
	});
	
})(jQuery);