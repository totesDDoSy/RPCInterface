package com.csanford.rpcinterface.controller;

import com.csanford.rpcinterface.h2.RPCRepository;
import com.csanford.rpcinterface.model.Outlet;
import com.csanford.rpcinterface.utils.RPCHelper;
import java.util.ArrayList;
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
@RequestMapping( "/outlet" )
public class OutletController
{
	@Autowired
	RPCRepository rpcRepository;

    @RequestMapping( "/{rpcid}" )
    public String displayOutletList( @PathVariable( value = "rpcid" ) Long rpcId, Model model )
    {

		return "outletlist";
    }

	private List<Outlet> getOutletsForRpcId( Long rpcid )
	{
		List<Outlet> outlets = new ArrayList<>();
		/**
		 * This is the logic for connecting to the RPC and navigating the menu to get the list of
		 * outlets, then parsing the list into objects and returning it.
		 */
		RPCHelper rpcHelper = new RPCHelper(rpcRepository.findOne( rpcid ).getIpaddr());
		rpcHelper.getOutlets();

		return outlets;
	}

}
