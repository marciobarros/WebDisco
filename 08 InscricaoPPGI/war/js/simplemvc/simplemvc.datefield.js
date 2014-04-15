/*
 * Extensão JQuery para campos do tipo DATA. Pode ser aplicada sobre um campo do tipo:
 * 
 * <input type="text" id="minhaData"/>
 * 
 * da seguinte forma:
 * 
 * $("#minhaData").setDateField()
 * 
 * Utiliza JQUERY.UI (para o datepcker) e JQUERY.MEIOMASK (para a edicao com mascara).
 */

(function($){
	$.extend({
		date_maskerade : {
			
			field : null,
			
			set: function(el, options){
				field = this;
				
				return $(el).each(function(){
					var $this = $(this);				
					var d = $("<div style='float:left;margin-right:8px;'></div>").insertBefore($this);
					$this.appendTo(d);
					$this.setMask("date");
					$this.datepicker({ dateFormat: "dd/mm/yy", showOn: "button" });
				});
			}
		}
	});
	
	$.fn.setDateField = function(options){
		return $.date_maskerade.set(this, options);
	};
	
})(jQuery);