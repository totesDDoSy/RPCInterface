$(document).ready(function ()
{
    initRowSelecter();
    initButtonListeners();
    initForms();

    $(document).on('shown.bs.modal', function ()
    {
	console.log("modal shown");
    });

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

	    $(this).button('loading');

	    ajaxCommandCall({
		command: 'on',
		rpcid: rpcid,
		outletid: outletid
	    });
	});
	$('#off-button').on('click', function ()
	{
	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];

	    $(this).button('loading');

	    ajaxCommandCall({
		command: 'off',
		rpcid: rpcid,
		outletid: outletid
	    });
	});
	$('#reboot-button').on('click', function ()
	{
	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];

	    $(this).button('loading');

	    let successCallback = function (response)
	    {
		$('.info').children('td').eq(2).children('span').eq(0).text("Rbt").removeClass('text-success').addClass('text-info');
		setTimeout(function () {
		    location.reload();
		}, 12000);
	    };

	    ajaxCommandCall({
		command: 'reboot',
		rpcid: rpcid,
		outletid: outletid,
		successCallback: successCallback
	    });
	});
	$('#refresh-button').on('click', function ()
	{
	    location.reload();
	});
    }

    /**
     *
     * @param {Object} opts
     *	command, rpcid, outletid, newName, args, successCallback, errorCallback, completeCallback
     * @returns {undefined}
     */
    function ajaxCommandCall(opts)
    {
	let data = JSON.stringify({
	    command: opts.command,
	    rpcId: opts.rpcid,
	    outletId: opts.outletid,
	    newName: opts.newName,
	    args: opts.args
	});

	if (opts.successCallback === undefined)
	{
	    opts.successCallback = function ()
	    {
		setTimeout(function () {
		    location.reload();
		}, 1700);
	    };
	}

	$.ajax({
	    type: "POST",
	    contentType: "application/json",
	    url: "/outlets/command",
	    data: data,
	    dataType: "json",
	    timeout: 60000,
	    success: opts.successCallback,
	    error: opts.errorCallback,
	    complete: opts.completeCallback
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

	    $('#edit-button').button('loading');

	    let outletid = $('.info').children('td').eq(0).text();
	    let path = window.location.pathname.split('/');
	    let rpcid = path[path.length - 1];

	    let completeCallback = function () {
		$('#rename-close').click();
		$('#rename-submit').removeClass('disabled');
		$('#rename-name').val('');
	    };

	    ajaxCommandCall({
		command: 'rename',
		rpcid: rpcid,
		outletid: outletid,
		newName: $('#rename-name').val(),
		completeCallback: completeCallback
	    });
	});
    }
});