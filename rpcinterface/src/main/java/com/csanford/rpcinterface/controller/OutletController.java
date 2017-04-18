package com.csanford.rpcinterface.controller;

import static java.lang.Thread.sleep;

import com.csanford.rpcinterface.ajax.AjaxResponseBody;
import com.csanford.rpcinterface.ajax.OutletRename;
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

    @RequestMapping( "/{rpcid}/{cmd}/{outletid}" )
    public String outletCommand( @PathVariable( value = "rpcid" ) Long rpcId,
	    @PathVariable( value = "cmd" ) String cmd,
	    @PathVariable( value = "outletid" ) Integer outletId, Model model )
	    throws InterruptedException
    {
	RPCHelper rpcHelper = new RPCHelper( rpcRepository.findOne( rpcId ).
		getIpaddr() );
	switch ( cmd.toLowerCase() )
	{
	    case "on":
		rpcHelper.turnOnOutlet( outletId );
		break;
	    case "off":
		rpcHelper.turnOffOutlet( outletId );
		break;
	    case "reboot":
		rpcHelper.rebootOutlet( outletId );
		break;
	    default:
		return "redirect:/outlets/" + rpcId;
	}
	sleep( 1500 );
	return "redirect:/outlets/" + rpcId;
    }

    @PostMapping( "/{rpcId}/rename/{outletId}" )
    public ResponseEntity<?> renameOutlet(
	    @PathVariable( value = "rpcId" ) Long rpcId,
	    @PathVariable( value = "outletId" ) Integer outletId,
	    @Valid @RequestBody OutletRename rename, Errors errors )
    {
	AjaxResponseBody result = new AjaxResponseBody();

	if ( errors.hasErrors() )
	{

	    result.setMsg( errors.getAllErrors()
		    .stream().map( x -> x.getDefaultMessage() )
		    .collect( Collectors.joining( "," ) ) );

	    return ResponseEntity.badRequest().body( result );

	}

	result.setMsg( rename.getNewName() );

	return ResponseEntity.ok( result );
    }
}
