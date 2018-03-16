window.onload = function() {
	loaded();
}

function htmlvar(result) {
	var html = '';
	$.each(result.data, function(k, v) {
		html += '<div class="item"><h3 class="title">' + v.month + '</h3>' + htmllist(v.data) + '</div>';
	})
	$("#thelist").append(html);
}

function htmllist(result) {
	var html = '';
	$.each(result, function(k, v) {
		html += '<div nid="' + v.id + '" class="list">' +
			'<label style="display: none;" class="am-checkbox am-success"><input type="checkbox" value="" data-am-ucheck></label>' +
			'<span class="name">' + v.heartrate + '</span><span class="value">' + v.highv + '/' + v.lowv + '</span></div>';
	})
	return html;
}

function iScrollClick() {
	if (/iPhone|iPad|iPod|Macintosh/i.test(navigator.userAgent)) return false;
	if (/Chrome/i.test(navigator.userAgent)) return (/Android/i.test(navigator.userAgent));
	if (/Silk/i.test(navigator.userAgent)) return false;
	if (/Android/i.test(navigator.userAgent)) {
		var s = navigator.userAgent.substr(navigator.userAgent.indexOf('Android') + 8, 3);
		return parseFloat(s[0] + s[3]) < 44 ? false : true
	}
}
var items_per_page = 10,
	where = {},
	scroll_in_progress = false,
	myScroll;

function load_page(next_page) {
	if($("#on").data("status")==1){
	alert("请退到编辑时在下拉操作");
	$('.pullUp').removeClass('loading').html('');
	 return false;
	}
	next_page == null ? next_page = 1 : "";
	$.getJSON("json.json", {}, function(result) {
		if (next_page = 1) {
			$(".pullUp").removeClass("pull-top");
		}
		htmlvar(result);
		myScroll.refresh();
	})
}
load_content = function(refresh, next_page) {
	setTimeout(function() {
		if (!refresh) {
			load_page();
		} else if (refresh && next_page) {
			load_page(next_page);
		}

		function pullUpAction(callback) {
			if ($('#wrapper > #scroller > .page').data('page')) {
				var next_page = parseInt($('#wrapper > #scroller > .page').data('page'), 10) + 1;
			} else {
				var next_page = 2;
			}
			load_content('refresh', next_page);
			$('#wrapper > #scroller > .page').data('page', next_page);

			if (callback) {
				callback();
			}
		}

		function pullActionCallback() {
			if (pullUpEl && pullUpEl.className.match('loading')) {
				$('.pullUp').removeClass('loading').html('');
			}
		}
		var pullActionDetect = {
			count: 0,
			limit: 10,
			check: function(count) {
				if (count) {
					pullActionDetect.count = 0;
				}
				setTimeout(function() {
					if (myScroll.y <= (myScroll.maxScrollY + 200) && pullUpEl && !pullUpEl.className.match('loading')) {
						$('.pullUp').addClass('loading').html('<span class="pullUpIcon">&nbsp;</span>');
						pullUpAction();
					} else if (pullActionDetect.count < pullActionDetect.limit) {
						pullActionDetect.check();
						pullActionDetect.count++;
					}
				}, 200);
			}
		}

		function trigger_myScroll(offset) {
			pullUpEl = document.querySelector('#wrapper .pullUp');
			if (pullUpEl) {
				pullUpOffset = pullUpEl.offsetHeight;
			} else {
				pullUpOffset = 0;
			}
			if ($('#wrapper .page > .item').length < items_per_page) {
				offset = 0;
			} else if (!offset) {
				offset = pullUpOffset;
			}
			myScroll = new $.AMUI.iScroll('#wrapper', {
				probeType: 1,
				click: iScrollClick(),
				tap: true,
				click: false,
				preventDefaultException: {
					tagName: /.*/
				},
				mouseWheel: true,
				scrollbars: true,
				fadeScrollbars: true,
				interactiveScrollbars: false,
				keyBindings: false,
				deceleration: 0.0002,
				startY: (parseInt(offset) * (-1))
			});
			myScroll.on('scrollStart', function() {
				scroll_in_progress = true;
			});
			myScroll.on('scroll', function() {
				scroll_in_progress = true;
			});
			myScroll.on('scrollEnd', function() {
				setTimeout(function() {
					scroll_in_progress = false;
				}, 100);
				if ($('#wrapper .page > .item').length >= items_per_page) {
					pullActionDetect.check(0);
				}
			});
			setTimeout(function() {
				$('#wrapper').css({
					'left': 0
				});
			}, 100);
		}
		if (refresh) {
			myScroll.refresh();
			pullActionCallback();
		} else {

			if (myScroll) {
				myScroll.destroy();
				$(myScroll.scroller).attr('style', '');
				myScroll = null;
			}
			trigger_myScroll();
		}
	}, 2000);
};

function loaded() {
	load_content();
}
document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);