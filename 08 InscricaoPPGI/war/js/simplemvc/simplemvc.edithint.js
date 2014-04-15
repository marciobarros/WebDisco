(function ($) {

	$.fn.edithint = function () {
		return this.each(function () {
			var $input = $(this),
			title = $input.attr('title');
			
			if (title) {
				var div = $('<div>')
					.css('position', 'relative')
					.css('float', 'left')
					.insertBefore($input);
				
				$input.appendTo(div);
				
				var label = $('<label>')
					.attr('for', $input.attr('name'))
					.text(title)
					.css('position', 'absolute')
					.css('top', $input.position().top)
					.css('left', $input.position().left)
					.addClass('edithint-label')
					.appendTo($input.parent())
					.hide();					
				
				label.click(function () {
					$input.focus();
				});
				
				$input.blur(function () {
					if (this.value == '')
						label.show();
				});
				
				$input.focus(function() {
					label.hide();
				});
				
				$input.blur();
				
				$input.change(function() {
					if ($input.val() === '' && !$input.is(":focus"))
						label.show();
					else
						label.hide();
				});
			}
		});
	};

})(jQuery);