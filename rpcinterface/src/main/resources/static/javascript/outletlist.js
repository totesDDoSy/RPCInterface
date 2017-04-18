$(document).ready(function ()
{
    initRowSelecter();
    initButtonListeners();
    initForms();

    function initRowSelecter()
    {
	$('tr').on('click', function ()
	{
	    $('tr.info').removeClass('info');
	    $(this).addClass('info');
	    $('#edit-button').removeClass('disabled');
	    let status = $(this).children('td').last().text();
	    if (status === "On")
	    {
		$('#on-button').addClass('disabled');
		$('#off-button').removeClass('disabled');
		$('#reboot-button').removeClass('disabled');
	    } else if (status === "Off")
	    {
		$('#on-button').removeClass('disabled');
		$('#off-button').addClass('disabled');
		$('#reboot-button').addClass('disabled');
	    }
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

    function initForms()
    {
	$('#rename-form').submit(function (event)
	{
	    // Prevent form submit
	    event.preventDefault();
	    // Disable submit when it's clicked
	    $('#rename-submit').addClass('disabled');

	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];
	    let data = JSON.stringify($('#rename-name').val());

	    $.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/outlets/" + rpcid + "/rename/" + outletid,
		data: data,
		dataType: 'json',
		timeout: 60000,
		success: function (response) {
		    console.dir(response);
		},
		error: function (response) {
		    console.dir(response);
		},
		complete: function () {
		    $('#rename-close').click();
		    $('#rename-submit').removeClass('disabled');
		    $('#rename-name').val('');
		}
	    });
	});
    }
});