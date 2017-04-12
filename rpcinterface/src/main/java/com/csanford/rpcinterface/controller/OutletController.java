package com.csanford.rpcinterface.controller;

import static java.lang.Thread.sleep;

import com.csanford.rpcinterface.h2.RPCRepository;
import com.csanford.rpcinterface.model.Outlet;
import com.csanford.rpcinterface.utils.RPCHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	List<Outlet> outlets = getOutletsForRpcId( rpcId );
	model.addAttribute( "outletlist", outlets );
	return "outletList";
    }

    @RequestMapping( "/{rpcid}/on/{outletid}" )
    public String turnOnOutlet( @PathVariable( value = "rpcid" ) Long rpcId,
	    @PathVariable( value = "outletid" ) Integer outletId, Model model )
	    throws InterruptedException
    {
	powerOnOutlet( rpcId, outletId );
	sleep( 1500 );
	return "redirect:/outlets/" + rpcId;
    }

    @RequestMapping( "/{rpcid}/off/{outletid}" )
    public String turnOffOutlet( @PathVariable( value = "rpcid" ) Long rpcId,
	    @PathVariable( value = "outletid" ) Integer outletId, Model model )
	    throws InterruptedException
    {
	powerOffOutlet( rpcId, outletId );
	sleep( 1500 );
	return "redirect:/outlets/" + rpcId;
    }

    @RequestMapping( "/{rpcid}/reboot/{outletid}" )
    public String rebootOutlet( @PathVariable( value = "rpcid" ) Long rpcId,
	    @PathVariable( value = "outletid" ) Integer outletId, Model model )
	    throws InterruptedException
    {
	rebootOutlet( rpcId, outletId );
	sleep( 1500 );
	return "redirect:/outlets/" + rpcId;
    }

    private List<Outlet> getOutletsForRpcId( Long rpcid )
    {
	RPCHelper rpcHelper = new RPCHelper( rpcRepository.findOne( rpcid ).
		getIpaddr() );

	return rpcHelper.getOutlets();
    }

    private void powerOnOutlet( Long rpcId, Integer outletId )
    {
	RPCHelper rpcHelper = new RPCHelper( rpcRepository.findOne( rpcId ).
		getIpaddr() );
	rpcHelper.turnOnOutlet( outletId );
    }

    private void powerOffOutlet( Long rpcId, Integer outletId )
    {
	RPCHelper rpcHelper = new RPCHelper( rpcRepository.findOne( rpcId ).
		getIpaddr() );
	rpcHelper.turnOffOutlet( outletId );
    }

    private void rebootOutlet( Long rpcId, Integer outletId )
    {
	RPCHelper rpcHelper = new RPCHelper( rpcRepository.findOne( rpcId ).
		getIpaddr() );
	rpcHelper.rebootOutlet( outletId );
    }
}
