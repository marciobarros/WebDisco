(function($){
	$.extend({
		internalModalDialog : {
			
			options: {
				afterCommandFunction: null,
				confirmationMessageFunction: null,
				confirmationMessage: null,
				width: 0
			},
			
			modalDialogField: null,
			
			errorDiv: null,
			
			form: null,
			
			window: null,
			
			action: null,
			
			/*
			 * Programa a estrutura da janela
			 */
			set: function(el, opt){
				modalDialogField = this;

				return $(el).each(function(){
					var $this = $(this);	
					this.options = $.extend({}, modalDialogField.options, opt);

					this.errorDiv = $("<div class='errorMessage' style='display: none;'></div>");
					$this.prepend(this.errorDiv);

					this.modalDialogField = modalDialogField;
					this.window = $this;
					this.form = $this.find("form");
					this.action = this.form.attr("action");
					this.form.attr("action", "javascript:void(0);");
	
					$this.dialog({
						autoOpen: false,
						width: /*modalDialogField*/this.options.width,
						modal: true,
						buttons: { "OK": modalDialogField._okButton, "Cancela": modalDialogField._cancelButton }
					});
					
					this.form.find("input").keydown(function(e) {
						if (e.which == $.ui.keyCode.ESCAPE)
						{
							$this.dialog("close");
							return false;
						}
						else if (e.which == $.ui.keyCode.ENTER)
						{
							$this.siblings('.ui-dialog-buttonpane').find('button:eq(0)').click();
							return false;
						}
					});
				});
			},
			
	        /*
	         * Executa o comando quando o botão de OK é pressionado 
	         */
			_okButton : function() {
        		this.errorDiv.hide();

        		var message = this.modalDialogField._getConfirmationMessage(this);
        		var self = this;
        		
	        	if (message)
	        	{
	    			jConfirm(message, 'Confirma\u00E7\u00E3o', function(result) {
	    				if (result)
	    					self.modalDialogField._executeCommand(self);
	    			});
	        	}
	        	else
	        		self.modalDialogField._executeCommand(self);
	        },
	        
	        /*
	         * Fecha a janela quando o botão de CANCEL é pressionado 
	         */
	        _cancelButton: function() {
	        	this.window.dialog("close");
	        },

	        /*
	         * Retorna a mensagem de confirmacao usada na janela 
	         */
	        _getConfirmationMessage: function (target) {
	        	if (/*this*/target.options.confirmationMessageFunction)
	        		return /*this*/target.options.confirmationMessageFunction();
	        	
	        	if (/*this*/target.options.confirmationMessage)
	        		return /*this*/target.options.confirmationMessage;
	        	
	        	return target.form.attr("confirm");
	        },
	       
	        /*
	         * Executa o comando associado à submissão do formulário
	         */
	        _executeCommand: function(target) {
	        	if (target.action)
	        	{
		        	var postData = target.form.serialize();
		        	var self = target;
		        	
		        	ajaxCall(target.action, postData, false, function (data) {
	        			self.errorDiv.hide();
	        			self.window.dialog("close");
	        		    
	        			if (self./*modalDialogField.*/options.afterCommandFunction) 
	        				self./*modalDialogField.*/options.afterCommandFunction(data);
	        		},
	        		function(message) {
	        			self.errorDiv.html(message);
	        			self.errorDiv.show();
		        	});
	        	}
	        	else
        		{
	        		target.errorDiv.hide();
	        		target.window.dialog("close");
        		    
	        		if (/*this.*/target.options.afterCommandFunction) 
	        			/*this.*/target.options.afterCommandFunction();
        		}
	        }
		}
	});
	
	$.fn.modalDialog = function(options){
		return $.internalModalDialog.set(this, options);
	};
	
})(jQuery);