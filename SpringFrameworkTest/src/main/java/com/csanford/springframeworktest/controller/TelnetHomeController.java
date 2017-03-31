package com.csanford.springframeworktest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.csanford.springframeworktest.utility.TelnetSocketHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
@Controller
@RequestMapping( "/telnet" )
public class TelnetHomeController
{

    private final Logger LOG = Logger.getLogger( TelnetHomeController.class.
	    getName() );
    private TelnetSocketHelper telnetHelper;
    private static final String TELNET_SERVER = "localhost";

    @RequestMapping( value = "/", method = GET )
    public String display()
    {
	return "telnet";
    }

    private void doTelnetStuff() throws IOException
    {
	if ( telnetHelper == null )
	{
	    telnetHelper = new TelnetSocketHelper( TELNET_SERVER );
	}
	telnetHelper.telnetWriter().println( "ping" );
	LOG.info( telnetHelper.telnetReader().readLine() );
    }
}
