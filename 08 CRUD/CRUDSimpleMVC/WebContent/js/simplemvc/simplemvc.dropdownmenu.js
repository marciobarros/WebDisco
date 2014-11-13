(function($) {
	$.fn.dropdownMenu = function(options) {
	
		var defaults = {
			name: "",
			width: null,
			rightAlignment: false
		};

		var opts = $.extend(defaults, options);

		return this.each(function() {

			var container = $(this)
				.addClass('simplemvc-dropdownmenu-container');
			
			if (opts.width)
				container.css('width', opts.width);

			var menu = container.find("ul")
				.addClass('simplemvc-dropdownmenu');
			
			if (opts.rightAlignment)
				menu.css('left', -(menu.width() - container.width() - 8));
			else
				menu.css('left', '-1');

			menu.find("li").click(function() {
				if($(this).attr("link"))
					window.location = $(this).attr("link"); 
				$('.simplemvc-dropdownmenu').hide();
				return false;
			});

			var menuLink = $('<a>')
				.attr('href','javascript:void(0);')
				.addClass('menu')
				.text(opts.name)
				.css('width', '100%')
				.insertBefore(menu)
				.click(function() {
					$('.simplemvc-dropdownmenu').not(menu).hide();
					$('.simplemvc-dropdownmenu-container').not(container).removeClass("down");
					menu.toggle();
					container.toggleClass("down");
					return false;
				});

			$('body').click(function(){
				$('.simplemvc-dropdownmenu').hide();
				$('.simplemvc-dropdownmenu-container').removeClass("down");
			});

			$('<img>')
				.attr('src', '/img/simplemvc/downarrow.png')
				.css('float', 'right')
				.appendTo(menuLink);
		});
	};
})(jQuery);