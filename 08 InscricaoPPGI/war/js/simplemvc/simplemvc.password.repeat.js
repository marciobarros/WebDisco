(function($){ 
	$.fn.diffPass = "Diferente";
	$.fn.equalPass = "Igual";
	$.fn.resultStyle = "";
	
	$.fn.passRepetition = function(options) {  
	  
		 var defaults = {
				source: 		"",
				diffPass:		"diffPass",
				equalPass:		"equalPass",
				baseStyle:		"testresult"
			}; 
		 	var opts = $.extend(defaults, options);  

		 	return this.each(function() { 
		 		 var obj = $(this);
		 		
		 		$(obj).unbind().keyup(function()
		 		{					
					var results = $.fn.testrepeat($(this).val(), $(opts.source).val(), opts);
					$(this).next("." + opts.baseStyle).remove();
					$(this).after("<span class=\""+opts.baseStyle+"\"></span>");
					$(this).next("." + opts.baseStyle).addClass($(this).resultStyle).text(results);
		 		 });
		 		 
		 		$.fn.testrepeat = function(password,sourcepwd,option){
		 			  if (password === sourcepwd)
		 			  { 
		 				  this.resultStyle = option.equalPass; 
		 				  return $(this).equalPass; 
		 			  }
		 			   
		 			  this.resultStyle = option.diffPass; 
		 			  return $(this).diffPass;
		 		};
		  });  
	 };  
})(jQuery);