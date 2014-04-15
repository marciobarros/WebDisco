/**
 * jquery.meiomask.js
 *
 * @version 1.0.1
 * Created by Fabio M. Costa on 2008-09-16. Please report any bug at http://www.meiocodigo.com
 *
 * Copyright (c) 2008 Fabio M. Costa http://www.meiocodigo.com
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

(function($){

	$.extend({
		maskerade : {
			
			// the mask rules. You may add yours!
			// number rules will be overwritten
			rules : {
				'z': /[a-z]/,
				'Z': /[A-Z]/,
				'a': /[a-zA-Z]/,
				'*': /[0-9a-zA-Z]/,
				'@': /[0-9a-zA-ZçÇá� ãéèíìóòõúùü]/,
				'N': /[NS]/,
				'W': /[WE]/
			},
			
			// fixed chars to be used on the masks. You may change it for your needs!
			fixedChars : '[(),.:/ \'-]',
			
			// these keys will be ignored by the mask. You may add yours!
			keys : {
				BKSPACE	: 8,
				TAB		: 9,
				ENTER	: 13,
				SHIFT	: 16,
				CTRL	: 17,
				ALT		: 18,
				SPACE	: 32,
				PGUP	: 33,
				PGDOWN	: 34,
				END		: 35,
				HOME	: 36,
				LEFT	: 37,
				UP		: 38,
				RIGHT	: 39,
				DOWN	: 40,
				INSERT	: 45,
				DELETE	: 46,
				METAKEY : 91, // Command key on Mac. '[' key haves this same keycode but on keypress... and this list is used on keydown. So '[' still works as it haves code 221 on keydown, at least at firefox.
				F5		: 116
				
			},
			
			// default settings
			options : {
				attr: 'alt' // an attr to look for the mask name or the mask itself
			},
	
			// masks. You may add yours!
			// Ex: $.fn.setMask.masks.msk = {mask: '999'}
			// and then if the 'attr' options value is 'alt', your input shoul look like:
			// <input type="text" name="some_name" id="some_name" alt="msk" />
			masks : {
				'phone'		: { mask : '(99) 9999-9999' },
				'phone-us'	: { mask : '(999) 9999-9999' },
				'cpf'		: { mask : '999.999.999-99' }, // cadastro nacional de pessoa fisica
				'cnpj'		: { mask : '99.999.999/9999-99' },
				'date'		: { mask : '39/19/9999' }, //uk date
				'date-us'	: { mask : '19/39/9999' },
				'cep'		: { mask : '99999-999' },
				'time'		: { mask : '29:69' },
				'cc'		: { mask : '9999 9999 9999 9999' }, //credit card mask
				'integer'	: { mask : '999.999.999.999', type : 'reverse' },
				'decimal'	: { mask : '99,999.999.999.999', type : 'reverse' },
				'decimal-us': { mask : '99.999,999,999,999', type : 'reverse' }
			},
			
			init : function(options){
				var i;
				this.ignore = false;
				this.fixedCharsReg = new RegExp(this.fixedChars);
				this.fixedCharsRegG = new RegExp(this.fixedChars,'g');
				this.ignoreArray = new Array();
				this.setOptions(options);
				// constructs number rules
				for(i=0; i<=9; i++){
					this.rules[i] = new RegExp('[0-'+i+']');
				}
				//we gonna ignore these keys while using the mask
				for( i in this.keys ){
					this.ignoreArray.push(this.keys[i]);
				}
				this.hasInit = true;
			},
			
			setOptions : function(options){
				this.options = $.extend({},this.options, options);
			},
			
			set: function(el,options){
				
				var maskObj = this,
					$el = $(el),
					mlStr = 'maxlength';
				// if has not inited...
				// you could call init before the first setMask, what is best for your purpose
				if( this.hasInit )
					this.setOptions(options);	
				else
					this.init(options);
					
				return $el.each(function(){
					
					var $this = $(this),
						attrValue = $this.attr(maskObj.options.attr),
						tmpMask = '',
						pasteEvent,
						o = {
							mask : null,
							type : 'fixed',
							suffix: '',
						};
					
					// then we look for the 'attr' option
					tmpMask = ( typeof options == 'string' )?options:( attrValue != '' )?attrValue:null;
					o.mask = tmpMask;
					// then we see if it's a defined mask
					if((maskObj.masks[tmpMask])) o = $.extend(o,maskObj.masks[tmpMask]);
					// then we look for some metadata on the input
					if(($.metadata)) o = $.extend(o,$this.metadata());
					var mask = o.mask;
					var suff, pref;
					if(o.type == "fixed") {
						suff = o.suffix;
						pref = "";
					}
					else {
						suff = "";
						pref = o.suffix;
					}
					if( mask != null ){
						maskObj.unset($this);
						$this.data('mask',{
							maxlength: $this.attr(mlStr),
							mask : mask,
							maskArray : mask.split(''),
							maskNonFixedCharsArray : mask.replace(maskObj.fixedCharsRegG,'').split(''),
							type: o.type,
							suffix: suff,
							prefix: pref
						});
						
						if( o.type == 'reverse' ){
							$this.css('text-align','right');
						}
						
						$this.bind('keydown',{funcFixed:maskObj._keyDown,funcReverse:maskObj._keyDown,thisObj:maskObj},maskObj._onMask);
						$this.bind('keyup',{funcFixed:maskObj._keyUp,funcReverse:maskObj._keyUp,thisObj:maskObj},maskObj._onMask);
						$this.bind('keypress',{funcFixed:maskObj._keyPressFixed,funcReverse:maskObj._keyPressReverse,thisObj:maskObj},maskObj._onMask);
						$this.bind('paste',{funcFixed:maskObj._paste,funcReverse:maskObj._paste,thisObj:maskObj},maskObj._delayedOnMask);
					}
				});
			},
			
			unset : function(el){
				var $el = $(el),
					_this = this;
				return $el.each(function(){
					var $this = $(this);
					if( $this.data('mask') ){
						var maxLength = $this.data('mask').maxlength;
						if(maxLength == -1)
							$this.removeAttr('maxlength');
						else
							$this.attr('maxlength',maxLength);
						$this.unbind('keydown',_this._onMask).unbind('keypress',_this._onMask).unbind('keyup',_this._onMask);
						$this.unbind('paste',_this._delayedOnMask);
						$this.removeData('mask');
					}
				});
			},
			
			_onMask : function(e){
				var thisObj = e.data.thisObj,
					o = {};
				o._this = e.target;
				o.$this = $(o._this);
				o.value = o.$this.val();
				o.valueArray = o.value.split('');
				o.data = o.$this.data('mask');
				o.nKey = thisObj.__getKeyNumber(e);
				o.caret = thisObj.__getRangePosition(o._this);
				o.reverse = (o.data.type == 'reverse');
				if( o.reverse ){
					return e.data.funcReverse.call(thisObj,e,o);
				}else{
					return e.data.funcFixed.call(thisObj,e,o);
				}
				return true;
			},
			// the timeout is set because on ie we can't get the value from the input without it
			_delayedOnMask : function(e){
				setTimeout(function(){ e.data.thisObj._onMask(e) },1);
			},
			
			_keyDown : function(e,o){
				if( $.inArray(o.nKey,this.ignoreArray) > -1 ){
					this.ignore = true;
				}
				else{
					this.ignore = false;
				}
				return true;
			},
			
			_keyUp : function(e,o){
				switch(o.data.type){
					case 'reverse':
						if( o.nKey == this.keys.BKSPACE || o.nKey == this.keys.DELETE ){
							this.ignore = false;
							this._keyPressReverse(e,o);
						}
						else{
							this._paste(e,o);
						}
						break;
					case 'fixed':
						this._paste(e,o);
						break;
				}
				return true;
			},
			
			_paste : function(e,o){				
				var	valueArray = o.valueArray;
				valueArray = this.__removeInvalidChars(valueArray,o.data.maskNonFixedCharsArray);
				if(o.reverse) valueArray.reverse();
				valueArray = this.__applyMask(valueArray,o.data.maskArray,0);
				if(o.reverse){
					valueArray.reverse();
					valueArray = valueArray.join('').substring(valueArray.length-o.data.mask.length);
				}
				else
					valueArray = valueArray.join('').substring(0,o.data.mask.length);
				var newVal = o.data.prefix+valueArray+o.data.suffix;
				o.$this.val(newVal);
				var caret_start = o.caret.start+o.data.prefix.length;
				var caret_end = o.caret.end+o.data.prefix.length;
				this.__setRange(o._this,caret_start,caret_end );
				return true;
			},
			
			_keyPressReverse : function(e,o){
				
				if( this.ignore ) return true;
				if( e.ctrlKey || e.metaKey || e.altKey ) return true;
				
				var	c = String.fromCharCode(o.nKey),
					caretStart = o.caret.start+o.data.prefix.length,
					rawValue = o.data.prefix+o.value,
					// the input value from the range start to the value start
					valueStart = rawValue.substr(0,caretStart),
					// the input value from the range end to the value end
					valueEnd = rawValue.substr(o.caret.end+o.data.prefix.length,rawValue.length),
					value = (valueStart+c+valueEnd).replace(this.fixedCharsRegG,''),
					valueArray = value.split(''),
					// searches for fixed chars begining from the caret start position, till it finds a non fixed
					extraPos = this.__extraPositionsTill(caretStart,o.data.maskArray);
					
				if( !this.rules[o.data.maskArray[caretStart+extraPos]] ||
					!this.rules[o.data.maskArray[caretStart+extraPos]].test(c) &&
					( o.nKey != this.keys.BKSPACE && o.nKey != this.keys.DELETE ) ) //so it can remask after a backspace or delete
						return false;
						
				valueArray = this.__removeInvalidChars(valueArray,o.data.maskNonFixedCharsArray);
				valueArray.reverse();
				valueArray = this.__applyMask(valueArray,o.data.maskArray);
				valueArray.reverse();
				o.$this.val(o.data.prefix+valueArray.join('').substring(valueArray.length-o.data.mask.length));
				return false;
			},
			
			_keyPressFixed : function(e,o){
				
				if( this.ignore ) return true;
				if( e.ctrlKey || e.metaKey || e.altKey ) return true;
				
				var	c = String.fromCharCode(o.nKey),
					rawValue = o.value+o.data.suffix,
					value = rawValue.replace(this.fixedCharsRegG,''),
					valueArray = value.split(''),
					caretStart = o.caret.start,
					extraPos = this.__extraPositionsTill(caretStart,o.data.maskArray);
					
				if( !this.rules[o.data.maskArray[caretStart+extraPos]] ||
					!this.rules[o.data.maskArray[caretStart+extraPos]].test(c) )
						return false;
						
				valueArray = this.__removeInvalidChars(valueArray,o.data.maskNonFixedCharsArray);
				valueArray = this.__applyMask(valueArray,o.data.maskArray,extraPos);
				
				o.$this.val(valueArray.join('').substr(0,o.data.mask.length)+o.data.suffix);
				
				if(caretStart==o.caret.end){
					if(caretStart+extraPos<rawValue.length)
						this.__setRange(o._this,caretStart+extraPos,caretStart+extraPos+1);
				}
				else
					this.__setRange(o._this,caretStart,o.caret.end);
					
				return true;
			},
			
			__getKeyNumber : function(e){
				return (e.charCode||e.keyCode||e.which);
			},
				
			// Removes values that doesnt match the mask from the valueArray
			// Returns the array without the invalid chars.
			__removeInvalidChars : function(valueArray,maskNonFixedCharsArray){
				// removes invalid chars
				for(var i=0; i<valueArray.length; i++ ){
					if( this.rules[maskNonFixedCharsArray[i]] && !this.rules[maskNonFixedCharsArray[i]].test(valueArray[i]) ){
						valueArray.splice(i,1);
						i--;
					}
				}
				return valueArray;
			},
			
			// Apply the current input mask to the valueArray and returns it. 
			__applyMask : function(valueArray,maskArray,plus){
				if( typeof plus == 'undefined' ) plus = 0;
				// apply the current mask to the array of chars
				for(var i=0; i<valueArray.length+plus; i++ ){
					if( this.fixedCharsReg.test(maskArray[i]) )
						valueArray.splice(i,0,maskArray[i]);
				}
				return valueArray;
			},
			
			// searches for fixed chars begining from the caret start position, till it finds a non fixed
			__extraPositionsTill : function(caretStart,maskArray){
				var extraPos = 0;
				while( this.fixedCharsReg.test(maskArray[caretStart]) ){
					caretStart++;
					extraPos++;
				}
				return extraPos;
			},
			
			// http://www.bazon.net/mishoo/articles.epl?art_id=1292
			__setRange : function(input,start, end) {
				if(typeof end == 'undefined') end = start;
				if (input.setSelectionRange) {
					input.setSelectionRange(start, end);
				} else {
					// assumed IE
					var range = input.createTextRange();
					range.collapse();
					range.moveStart("character", start);
					range.moveEnd("character", end - start);
					range.select();
				}
			},
			
			// a comment at http://www.bazon.net/mishoo/articles.epl?art_id=1292
			__getRangePosition : function(input){
				var result = { start: 0, end: 0 };
				if (input.setSelectionRange){
					result.start = input.selectionStart;
					result.end = input.selectionEnd;
				}
				else if (document.selection && document.selection.createRange){
					var range = document.selection.createRange();
					var r2 = range.duplicate();
					result.start = 0 - r2.moveStart('character', -100000);
					result.end = result.start + range.text.length;
				}
				return result;
			}
		}
	});
	
	$.fn.setMask = function(options){
		return $.maskerade.set(this,options);
	};
	
	$.fn.unsetMask = function(){
		return $.maskerade.unset(this);
	};
	
})(jQuery);
