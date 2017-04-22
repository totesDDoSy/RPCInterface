package com.csanford.rpcinterface.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class SocketHelper
{

    private static final Logger LOG = LoggerFactory.getLogger(
	    SocketHelper.class );
    private final Socket socket;

    public SocketHelper( int port, String serverName ) throws IOException
    {
	LOG.info(
		"Getting a socket for [server=" + serverName + ", port=" + port + "]" );
	socket = new Socket( serverName, port );
    }

    public BufferedWriter getWriter()
    {

	BufferedWriter writer = null;
	try
	{
	    writer = new BufferedWriter( new OutputStreamWriter( socket.
		    getOutputStream() ) );
	}
	catch ( IOException ex )
	{
	    LOG.error( ex.getLocalizedMessage() );
	}

	return writer;
    }

    public BufferedReader getReader()
    {
	BufferedReader reader = null;

	try
	{
	    reader = new BufferedReader( new InputStreamReader( socket.
		    getInputStream() ) );
	}
	catch ( IOException ex )
	{
	    LOG.error( ex.getLocalizedMessage() );
	}

	return reader;
    }
}
