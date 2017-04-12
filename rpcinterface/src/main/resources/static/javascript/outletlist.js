$(document).ready(function ()
{
    initRowSelecter();
    initButtonListeners();

    function initRowSelecter()
    {
	$('tr').on('click', function ()
	{
	    $('tr.info').removeClass('info');
	    $(this).addClass('info');
	});
    }

    function initButtonListeners()
    {
	$('#on-button').on('click', function ()
	{
	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];
	    window.location.href = '/outlets/' + rpcid + '/on/' + outletid;
	});
	$('#off-button').on('click', function ()
	{
	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];
	    window.location.href = '/outlets/' + rpcid + '/off/' + outletid;
	});
	$('#reboot-button').on('click', function ()
	{
	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];
	    window.location.href = '/outlets/' + rpcid + '/reboot/' + outletid;
	});
	$('#refresh-button').on('click', function ()
	{
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];
	    window.location.href = '/outlets/' + rpcid;
	});
    }
});