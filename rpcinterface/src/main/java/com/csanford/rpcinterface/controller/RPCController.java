package com.csanford.rpcinterface.controller;

import com.csanford.rpcinterface.h2.RPC;
import com.csanford.rpcinterface.h2.RPCRepository;
import com.csanford.rpcinterface.model.RPCModel;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping( "/" )
    public String displayRpcList( Model model )
    {
	ArrayList<RPC> rpcs = new ArrayList<>();
	rpcRepository.findAll().forEach( rpcs::add );
	model.addAttribute( "rpclist", rpcs );
	return "rpcList";
    }

    @GetMapping( "/add" )
    public String displayAddRpc( Model model )
    {
	model.addAttribute( "rpcmodel", new RPCModel() );
	return "rpcAdd";
    }

    @PostMapping( "/add" )
    public String addRpc( @ModelAttribute RPCModel rpc )
    {
	rpcRepository.save( rpc.convertToDAO() );
	return "redirect:/";
    }

    @GetMapping( "/edit/{rpcid}" )
    public String displayEditRpc( @PathVariable( value = "rpcid" ) Long rpcId,
	    Model model )
    {
	model.addAttribute( "rpcmodel", RPCModel.convertFromDAO( rpcRepository.
		findOne( rpcId ) ) );
	return "rpcAdd";
    }

    @PostMapping( "/edit/{rpcid}" )
    public String editRpc( @PathVariable( value = "rpcid" ) Long rpcId,
	    @ModelAttribute RPCModel rpcModel )
    {
	RPC rpc = rpcModel.convertToDAO();
	rpc.setId( rpcId );
	rpcRepository.save( rpc );
	return "redirect:/";
    }

}
