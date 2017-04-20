package com.csanford.rpcinterface.controller;

import com.csanford.rpcinterface.ajax.AjaxResponseBody;
import com.csanford.rpcinterface.ajax.OutletCommandRequest;
import com.csanford.rpcinterface.h2.RPCRepository;
import com.csanford.rpcinterface.model.Outlet;
import com.csanford.rpcinterface.utils.RPCHelper;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
@Controller
@RequestMapping( "/outlets" )
public class OutletController
{
    @Autowired
    RPCRepository rpcRepository;

    @RequestMapping( "/{rpcid}" )
    public String displayOutletList( @PathVariable( value = "rpcid" ) Long rpcId,
	    Model model )
    {
	final String ipaddr = rpcRepository.findOne( rpcId ).
		getIpaddr();
	RPCHelper rpcHelper = new RPCHelper( ipaddr );
	List<Outlet> outlets = rpcHelper.getOutlets();
	if ( outlets == null )
	{
	    return "redirect:/?error=true&ip=" + ipaddr;
	}
	model.addAttribute( "outletlist", outlets );
	return "outletList";
    }

    @PostMapping( "/command" )
    public ResponseEntity<?> outletCommand(
	    @Valid @RequestBody OutletCommandRequest request, Errors errors )
    {
	AjaxResponseBody response = new AjaxResponseBody();

	if ( errors.hasErrors() )
	{

	    response.setMsg( errors.getAllErrors()
		    .stream().map( x -> x.getDefaultMessage() )
		    .collect( Collectors.joining( "," ) ) );

	    return ResponseEntity.badRequest().body( response );

	}

	RPCHelper rpcHelper = new RPCHelper(
		rpcRepository.findOne( request.getRpcId() ).getIpaddr() );
	switch ( request.getCommand().toLowerCase() )
	{
	    case "on":
		rpcHelper.turnOnOutlet( request.getOutletId() );
		break;
	    case "off":
		rpcHelper.turnOffOutlet( request.getOutletId() );
		break;
	    case "restart":
	    case "reboot":
		rpcHelper.rebootOutlet( request.getOutletId() );
		break;
	    case "rename":
		rpcHelper.renameOutlet( request.getOutletId(),
			request.getNewName() );
		break;
	    default:
		return ResponseEntity.badRequest().body( response );
	}

	response.setMsg( "Command Completed" );

	return ResponseEntity.ok( response );
    }
}
