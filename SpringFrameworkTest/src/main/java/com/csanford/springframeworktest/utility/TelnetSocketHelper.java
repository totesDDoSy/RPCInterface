package com.csanford.springframeworktest.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class TelnetSocketHelper
{

    private Socket telSocket;

    public TelnetSocketHelper( String serverName )
    {
	try
	{
	    telSocket = new Socket( serverName, 23 );
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( TelnetSocketHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    public PrintWriter telnetWriter()
    {
	PrintWriter writer = null;
	try
	{
	    writer = new PrintWriter( telSocket.getOutputStream(), true );
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( TelnetSocketHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}

	return writer;
    }

    public BufferedReader telnetReader()
    {
	BufferedReader reader = null;

	try
	{
	    reader = new BufferedReader( new InputStreamReader( telSocket.
		    getInputStream() ) );
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( TelnetSocketHelper.class.getName() ).log(
		    Level.SEVERE, null, ex );
	}

	return reader;
    }
}
