$(document).ready(function()
{
	$('tbody tr').on( 'click', function()
	{
		let rpcid = $(this).children('td').last().children('.hidden-id').text();
		window.location.href = "/outlet"
	});
});