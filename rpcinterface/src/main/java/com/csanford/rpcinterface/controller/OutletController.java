package com.csanford.rpcinterface.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
@Controller
@RequestMapping( "/outlet" )
public class OutletController
{

    @RequestMapping( "/" )
    public String displayOutletList( Model model )
    {
	return "outletlist";
    }

}
