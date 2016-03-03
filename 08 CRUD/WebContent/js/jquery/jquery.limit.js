(function($){ 
     $.fn.extend({  
         limit: function(limit,element) {
			var interval;
			var self = $(this);

			$(this).focus(function(){
				interval = window.setInterval(substring,100);
			});
			
			$(this).blur(function(){
				clearInterval(interval);
				substring();
			});
			
			substringFunction = "function substring(){ var val = $(self).val();var length = val.length;if(length > limit){$(self).val($(self).val().substring(0,limit));}";

			if(typeof element != 'undefined')
			{
				substringFunction += "var count = (limit-length<=0) ? '0' : limit-length;";
				substringFunction += "var text = (count == 1) ? 'caractere dispon&iacute;vel' : 'caracteres dispon&iacute;veis';";
				substringFunction += "var message = count.toString() + \" \" + text;";
				substringFunction += "if($(element).html() != message){$(element).html(message);}";
			}
				
			substringFunction += "}";
			eval(substringFunction);
			substring();
        } 
    }); 
})(jQuery);