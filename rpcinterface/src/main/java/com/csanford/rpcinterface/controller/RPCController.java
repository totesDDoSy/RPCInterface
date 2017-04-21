package com.csanford.rpcinterface.controller;

import com.csanford.rpcinterface.h2.RPC;
import com.csanford.rpcinterface.h2.RPCRepository;
import com.csanford.rpcinterface.model.RPCModel;
import java.util.ArrayList;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
@Controller
public class RPCController
{
    @Autowired
    RPCRepository rpcRepository;

    private static final Logger LOG = LoggerFactory.getLogger(
	    RPCController.class );

    @RequestMapping( "/" )
    public String displayRpcList( Model model )
    {
	ArrayList<RPC> rpcs = new ArrayList<>();
	rpcRepository.findAll().forEach( rpcs::add );

	LOG.info( "List of RPC entries: " + rpcs );

	model.addAttribute( "rpclist", rpcs );
	return "rpcList";
    }

    @GetMapping( "/add" )
    public String displayAddRpc( RPCModel rpcmodel )
    {
	return "rpcAdd";
    }

    @PostMapping( "/add" )
    public String addRpc( @Valid RPCModel rpcmodel, BindingResult bindingResult,
	    Model model )
    {
	if ( bindingResult.hasErrors() )
	{
	    LOG.info(
		    "Invalid RPC: " + rpcmodel + "\nErrors:\n" + bindingResult );
	    return "rpcAdd";
	}

	LOG.info( "Saving RPC: " + rpcmodel );
	rpcRepository.save( rpcmodel.convertToDAO() );
	return "redirect:/";
    }

    @GetMapping( "/edit/{rpcid}" )
    public String displayEditRpc( @PathVariable( value = "rpcid" ) Long rpcId,
	    Model model )
    {
	LOG.info( "Display edit page for RPC: " + rpcId );
	model.addAttribute( "RPCModel", RPCModel.convertFromDAO( rpcRepository.
		findOne( rpcId ) ) );
	return "rpcAdd";
    }

    @PostMapping( "/edit/{rpcid}" )
    public String editRpc( @PathVariable( value = "rpcid" ) Long rpcId,
	    @ModelAttribute RPCModel rpcModel )
    {
	LOG.info( "Saving edited RPC: " + rpcModel );
	RPC rpc = rpcModel.convertToDAO();
	rpc.setId( rpcId );
	rpcRepository.save( rpc );
	return "redirect:/";
    }

    @RequestMapping( "/delete/{rpcid}" )
    public String deleteRpc( @PathVariable( value = "rpcid" ) Long rpcid )
    {
	LOG.info( "Deleting RPC: " + rpcid );
	rpcRepository.delete( rpcid );
	return "redirect:/";
    }

}
