(function($){
	$.extend({
		internalPanelDialog : {
			
			options: {
				width: 0,
				height: 0
			},
			
			set: function(el, opt){
				var panelDialogField = this;

				return $(el).each(function(){
					this.options = $.extend({}, panelDialogField.options, opt);

					$(this).dialog({
						autoOpen: false,
						/*height: this.options.height,*/
						minHeight: this.options.height,
						width: this.options.width,
						modal: true
					});
				});
			}
		}
	});
	
	$.fn.panelDialog = function(options){
		return $.internalPanelDialog.set(this, options);
	};
	
})(jQuery);