(function ($) {
    "use strict";
	//===== jquery code for sidebar menu
	$('.menu-item.has-submenu .menu-link').on('click', function(e){
		e.preventDefault();
		if($(this).next('.submenu').is(':hidden')){
			$(this).parent('.has-submenu').siblings().find('.submenu').slideUp(200);
		} 
		$(this).next('.submenu').slideToggle(200);
	});

	// mobile offnavas triggerer for generic use
	$("[data-trigger]").on("click", function(e){
		e.preventDefault();
		e.stopPropagation();
		var offcanvas_id =  $(this).attr('data-trigger');
		$(offcanvas_id).toggleClass("show");
		$('body').toggleClass("offcanvas-active");
		$(".screen-overlay").toggleClass("show");

	}); 

	$(".screen-overlay, .btn-close").click(function(e){
		$(".screen-overlay").removeClass("show");
		$(".mobile-offcanvas, .show").removeClass("show");
		$("body").removeClass("offcanvas-active");
	}); 

	// minimize sideber on desktop

	$('.btn-aside-minimize').on('click', function(){
		if( window.innerWidth < 768) {
			$('body').removeClass('aside-mini');
			$(".screen-overlay").removeClass("show");
			$(".navbar-aside").removeClass("show");
			$("body").removeClass("offcanvas-active");
		} 
		else {
			// minimize sideber on desktop
			$('body').toggleClass('aside-mini');
		}
	});

	//Nice select
	if ($('.select-nice').length) {
    	$('.select-nice').select2();
	}
	// Perfect Scrollbar
	if ($('#offcanvas_aside').length) {
		const demo = document.querySelector('#offcanvas_aside');
		const ps = new PerfectScrollbar(demo);
	}

	// Dark mode toogle
	$('.darkmode').on('click', function () {
		$('body').toggleClass("dark");
	});
	
})(jQuery);
$('#exampleModal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget) // Button that triggered the modal
	var recipient = button.data('whatever') // Extract info from data-* attributes
	// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	var modal = $(this)
	modal.find('.modal-title').text('New message to ' + recipient)
	modal.find('.modal-body input').val(recipient)
})

