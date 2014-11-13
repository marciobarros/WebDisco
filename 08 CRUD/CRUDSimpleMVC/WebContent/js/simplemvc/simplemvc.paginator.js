(function($) {

	$.widget("ui.paginator", {  

    	options: {
    		count: 0,
   			pageSize: 10,
   			currentPage: 1,
   			message: '{0} a {1} de {2} registros',
   			showRecordNumber: true,
   			onLoad: function (currentPage, pageSize) {}
    	}, 
        	
	 	_create : function() { 
	 		this._update();
	 	},
	 	
	 	destroy: function() {  
	 	},
	 	
	 	_setOption: function(option, value) {  
	 	    $.Widget.prototype._setOption.apply(this, arguments);  

	 	    if (option == "count") { 
 	        	this.options.count = value;
 	        	this._update();
	 	    }
	 	    else if (option == "currentPage") { 
 	        	this.options.currentPage = value;
 	        	this._update();
	 	    }  
	 	},
	 	
	 	_update : function() { 
	 		this.element.empty();
            var pageCount = this._calculatePageCount(this.options.count, this.options.pageSize);
            var pageNumbers = this._calculatePageNumbers(pageCount, this.options.currentPage);
            
            this._createFirstAndPreviousPageButtons(this.element, this.options.currentPage);
            this._createPageNumberButtons(this.element, this.options.currentPage, pageNumbers);
            this._createLastAndNextPageButtons(this.element, this.options.currentPage, pageCount);
            this._createPagingInfo(this.element, this.options.currentPage, this.options.count, this.options.pageSize);
            this._bindClickEventsToPageNumberButtons(this.element, this.options.onLoad);
	 	},  

        /* Calculates total page count according to page size and total record count.
        *************************************************************************/
        _calculatePageCount : function(recordCount, pageSize) {
            var pageCount = Math.floor(recordCount / pageSize);
            if (recordCount % pageSize != 0) {
                ++pageCount;
            }

            return pageCount;
        },

        /* Calculates page numbers and returns an array of these numbers.
        *************************************************************************/
        _calculatePageNumbers : function(pageCount, currentPageNo) {
            if (pageCount <= 6) {
                //Show all pages
                var pageNumbers = [];
                for (var i = 1; i <= pageCount; ++i) {
                    pageNumbers.push(i);
                }

                return pageNumbers;
            } else {
                //show first three, last three, current, previous and next page numbers
                var shownPageNumbers = [1, 2, 3, pageCount - 2, pageCount - 1, pageCount];
                var previousPageNo = this._normalizeNumber(currentPageNo - 1, 1, pageCount, 1);
                var nextPageNo = this._normalizeNumber(currentPageNo + 1, 1, pageCount, 1);

                this._insertToArrayIfDoesNotExists(shownPageNumbers, previousPageNo);
                this._insertToArrayIfDoesNotExists(shownPageNumbers, currentPageNo);
                this._insertToArrayIfDoesNotExists(shownPageNumbers, nextPageNo);

                shownPageNumbers.sort(function(a, b) { return a - b; });
                return shownPageNumbers;
            }
        },

        /* Creates and shows previous and first page links.
        *************************************************************************/
 		_createFirstAndPreviousPageButtons : function(area, currentPageNo) {
             if (currentPageNo > 1) {
                 $('<span class="jtable-page-number-first">|&lt</span>')
                     .attr('pageNumber', 1)
                     .appendTo(area);
                 
                 $('<span class="jtable-page-number-previous">&lt</span>')
                     .attr('pageNumber', currentPageNo - 1)
                     .appendTo(area);
             }
        },

        /* Creates and shows next and last page links.
        *************************************************************************/
        _createLastAndNextPageButtons : function(area, currentPageNo, pageCount) {
             if (currentPageNo < pageCount) {
                 $('<span class="jtable-page-number-next">&gt</span>')
                     .attr('pageNumber', currentPageNo + 1)
                     .appendTo(area);
                 
                 $('<span class="jtable-page-number-last">&gt|</span>')
                     .attr('pageNumber', pageCount)
                     .appendTo(area);
             }
         },

         /* Creates and shows page number links for given number array.
         *************************************************************************/
         _createPageNumberButtons : function(area, currentPageNo, pageNumbers) {
             var previousNumber = 0;

             for (var i = 0; i < pageNumbers.length; i++) {
                 if ((pageNumbers[i] - previousNumber) > 1) {
                     $('<span class="jtable-page-number-space">...</span>').appendTo(area);
                 }

                 this._createPageNumberButton(area, currentPageNo, pageNumbers[i]);
                 previousNumber = pageNumbers[i];
             }
         },

         /* Creates a page number link and adds to paging area.
         *************************************************************************/
         _createPageNumberButton : function(area, currentPageNo, pageNumber) {
             $('<span class="' + (currentPageNo == pageNumber ? 'jtable-page-number-active' : 'jtable-page-number') + '">' + pageNumber + '</span>')
                 .attr('pageNumber', pageNumber)
                 .appendTo(area);
         },

         /* Creates and shows paging informations.
         *************************************************************************/
         _createPagingInfo : function(area, currentPageNo, recordCount, pageSize) {
             var startNo = (currentPageNo - 1) * pageSize + 1;
             var endNo = currentPageNo * pageSize;
             endNo = this._normalizeNumber(endNo, startNo, this._totalRecordCount, 0);

             if (endNo >= startNo && this.options.showRecordNumber) {
                 var pagingInfoMessage = this._formatString(this.options.message, startNo, endNo, recordCount);
                 $('<span class="jtable-page-info">' + pagingInfoMessage + '</span>').appendTo(area);
             }
         },

         /* Binds click events of all page links to change the page.
         *************************************************************************/
         _bindClickEventsToPageNumberButtons : function(area, onLoad) {
        	 var self = this;
        	 
             self.element.find('.jtable-page-number,.jtable-page-number-previous,.jtable-page-number-next,.jtable-page-number-first,.jtable-page-number-last')
                 .click(function(e) {
                     e.preventDefault();
                     var currentPageNo = parseInt($(this).attr('pageNumber'));
                     self.options.currentPage = currentPageNo;
                	 onLoad(currentPageNo, self.options.pageSize);
                	 self._update();
                 });
         },

         /* Normalizes a number between given bounds or sets to a defaultValue
         *  if it is undefined
         *************************************************************************/
         _normalizeNumber : function(number, min, max, defaultValue) {
             if (number == undefined || number == null) {
                 return defaultValue;
             }

             if (number < min) {
                 return min;
             }

             if (number > max) {
                 return max;
             }

             return number;
         },

         /* Formats a string just like string.format in c#.
         *  Example:
         *  _formatString('Hello {0}','Halil') = 'Hello Halil'
         *************************************************************************/
         _formatString : function() {
             if (arguments.length == 0) {
                 return null;
             }

             var str = arguments[0];
             for (var i = 1; i < arguments.length; i++) {
                 var placeHolder = '{' + (i - 1) + '}';
                 str = str.replace(placeHolder, arguments[i]);
             }

             return str;
         },
         
         /* Inserts a value to an array if it does not exists in the array.
         *************************************************************************/
         _insertToArrayIfDoesNotExists : function(array, value) {
        	 if ($.inArray(value, array) < 0) {
        		 array.push(value);
             }
         }
	});
	
})(jQuery);