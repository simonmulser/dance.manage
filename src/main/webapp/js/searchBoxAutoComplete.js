$(function () {
	$(document).ready(
		$("#searchQuery").autocomplete({
			
									minLength : 1,
									delay : 500,
									source : function(request, response) {
										$.getJSON("/dancemanage/admin/search/searchQuery",
											request,function(result) {
												response($.map(result,
													function(item) {
														return {
															label : item.name,
															value : item.name,
															id : item.id,
															image : item.icon,
															oname : item.objectName
														};
											}));
										});
									},

									select : function(event, ui) {
										if (ui.item) {
											window.location.href = '/dancemanage/admin/' + ui.item.oname + '/edit/' + ui.item.id + '#add';
										}
									}
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
        return $( "<li>" )
        	.data( "item.autocomplete", item )
        	.append( "<a> <i class='"+ item.image +"''></i>&nbsp;" + item.label + " </a>" )
        	.appendTo( ul );
    });
});