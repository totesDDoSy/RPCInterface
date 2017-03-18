package com.csanford.springframeworktest.utility;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class TelnetUtils
{

    private final String remoteIp;
    private final Integer remotePort;
    private static final Logger LOG = Logger.getLogger( TelnetUtils.class.
	    getName() );
    private TelnetClient tc;

    public TelnetUtils( String remoteIp, Integer remotePort )
    {
	this.remoteIp = remoteIp;
	this.remotePort = remotePort;

	tc = new TelnetClient();
	setupTelnetHandlers();
    }

    public void connect() throws IOException
    {
	tc.connect( remoteIp, remotePort );
    }

    private void setupTelnetHandlers()
    {
	TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler( "VT100",
		false, false, true, false );
	EchoOptionHandler echoopt = new EchoOptionHandler( true, false, true,
		false );
	SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler( true, true,
		true, true );
	try
	{
	    tc.addOptionHandler( ttopt );
	    tc.addOptionHandler( echoopt );
	    tc.addOptionHandler( gaopt );
	}
	catch ( InvalidTelnetOptionException | IOException e )
	{
	    System.err.println( "Error registering option handlers: " + e.
		    getMessage() );
	}
    }

}
