(function($) {

	$.widget("ui.box", {  

		/**
		 * Options for the roll-out box
		 */
    	options: {
    		css_title: "rolloutbox-title",
    		css_major: "rolloutbox-major",
    		css_content: "rolloutbox-content",
    		css_commands: "rolloutbox-commands",
    		title: "",
    		height: 300,
    		commands: []
    	}, 
        	
    	/**
    	 * Creates the box
    	 */
	 	_create : function() { 
	 		this._update();
	 	},
	 	
    	/**
    	 * Destroy the box
    	 */
	 	destroy: function() {  
	 	},
	 	
    	/**
    	 * Changes options for the box
    	 */
	 	_setOption: function(option, value) {  
	 	    $.Widget.prototype._setOption.apply(this, arguments);  
        	this._update();
	 	},
	 	
    	/**
    	 * Updates the data in the box
    	 */
	 	_update : function() { 
	 		var content = this.element.html();
	 		this.element.empty();
	 		this.element.attr('class', this.options.css_major);
	 		
            var header = $('<div class="' + this.options.css_title + '">' + this.options.title + '</div>')
	            .appendTo(this.element);
            
	 		if (this.options.commands.length > 0)
 			{
	 			var sComandos = "";
	            var len = this.options.commands.length;

	            for (var i = 0; i < len; i++)
	            {
	            	var comando = this.options.commands[i];
	            	
	            	if (sComandos) 
	            		sComandos += ' | ';
	            	
	            	sComandos += '<a ';
	            	
	            	if (comando.link)
	            		sComandos += 'href="' + comando.link + '" ';

	            	if (comando.id)
	            		sComandos += 'id="' + comando.id + '" ';

	            	sComandos += '>' + comando.name + '</a>';
	            }
	            
	 			$('<div class="' + this.options.css_commands + '">' + sComandos + '</div>')
	 				.appendTo(header);
 			}
	 		
            var painel = $('<div class="' + this.options.css_content + '"></div>')
	            .appendTo(this.element)
	            .css('display', 'block')
	            .html(content);
            
            if (this.options.height > 0)
            	painel.css('height', this.options.height);
	 	}
	});
	
})(jQuery);