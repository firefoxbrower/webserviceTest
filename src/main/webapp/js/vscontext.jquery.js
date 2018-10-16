(function($){
    jQuery.fn.vscontext = function(options){
        var defaults = {
            menuBlock: null,
            offsetX : 8,
            offsetY : 8,
            speed : 'fast'
        };
        var options = $.extend(defaults, options);
        var menu_item = '#' + options.menuBlock;
        return this.each(function(){
            	$(this).bind("contextmenu",function(e){
				return false;
		});
            	$(this).mousedown(function(e){
            			//获取显示框的高度
            			//var menuHeight = $("#menuHight").val();
            			var menuHeight = $(menu_item).height();
            			var wh = document.body.clientWidth;
            			var ht = document.body.clientHeight
                        var offsetX = e.pageX  + options.offsetX;
                        var offsetY = e.pageY + options.offsetY;
			if(($.browser.msie && e.button == "1") || (!$.browser.msie && e.button == "0")){
                            $(menu_item).show(options.speed);
                            $(menu_item).css('display','block');
                            if(eval(menuHeight+"+"+offsetY+"+"+150) > ht){//当前Y轴高度+显示框高度+50大于body的高度时
                            	$(menu_item).css('top',offsetY-menuHeight-30);
                                $(menu_item).css('left',offsetX-140);		
                            }else{
                            	$(menu_item).css('top',offsetY);
                                $(menu_item).css('left',offsetX-140);
                            }
			}else {
                            $(menu_item).hide(options.speed);
                        }
		});
                $(menu_item).hover(function(){}, function(){$(menu_item).hide(options.speed);})
                
        });
    };
})(jQuery);
