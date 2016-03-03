(function($){ 
     $.fn.extend({  
         phoneField: function() {
			var self = $(this);
			self.mask("(99) 9999-9999?9");
			
			self.blur(function(){
				self.unmask();
			    var phone = self.val().replace(/\D/g, '');
			    self.mask((phone.length > 10) ? "(99) 99999-999?9" : "(99) 9999-9999?9");
			});
			
			self.trigger('blur');
        } 
    }); 
})(jQuery);