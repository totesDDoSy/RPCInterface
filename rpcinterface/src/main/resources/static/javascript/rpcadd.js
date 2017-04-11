$(document).ready(function()
{
	applyErrors();

	function applyErrors()
	{
		let errors = [];
		$( '.form-group' ).each( function ()
		{
			if( $(this).children('div').first().children('input').hasClass('error') )
			{
				errors.push(
						{
							id : $(this).children('div').first().children('input').attr( 'id' ),
							msg : $(this).children('div').first().children('span.hidden-error').text()
						} );
				$(this).addClass( 'has-error' );
				$(this).children('div').first().children('span.glyphicon-remove').show();
			}
		});
		$( '.form-group' ).each( function ()
		{
			if( errors.length > 0 && !$(this).children('div').first().children('input').hasClass('error') )
			{
				$(this).addClass( 'has-success' );
				$(this).children('div').first().children('span.glyphicon-ok').show();
			}
		});
		if(errors.length > 0)
		{
			$('#error-alert').show();
			errors.forEach(function( element )
			{
				$( '#error-alert-' + element.id ).append( element.msg ).show();
			});
		}
	}
});
