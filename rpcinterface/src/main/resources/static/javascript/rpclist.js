$(document).ready(function ()
{
    if (getUrlVars()['error'])
    {
	$('#error-alert-connect').append("Could not connect to RPC with IP " + getUrlVars()['ip'].split('#')[0]);
	$('#error-alert').show();
    }

    $('tbody tr').on('click', function ()
    {
	let rpcid = $(this).children('td').last().children('.hidden-id').text();
	window.location.href = "/outlets/" + rpcid;
    });

    // Read a page's GET URL variables and return them as an associative array.
    function getUrlVars()
    {
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++)
	{
	    hash = hashes[i].split('=');
	    vars.push(hash[0]);
	    vars[hash[0]] = hash[1];
	}
	return vars;
    }
});