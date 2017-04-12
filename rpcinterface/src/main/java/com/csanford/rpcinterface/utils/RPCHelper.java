package com.csanford.rpcinterface.utils;

import com.csanford.rpcinterface.model.Outlet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPCHelper
{
    private final String rpcIp;
    private static final int OUTLETS_OPT = (int) '1';

    public RPCHelper( String rpcIp )
    {
	this.rpcIp = rpcIp;
    }

    public List<Outlet> getOutlets()
    {
	SocketHelper socketHelper = new SocketHelper( 23, rpcIp );
	BufferedReader reader = socketHelper.getReader();
	BufferedWriter writer = socketHelper.getWriter();
	List<Outlet> outlets = new ArrayList<>();

	if ( navigateToOutlets( reader, writer ) )
	{
	    outlets = getOutletsFromOutletScreen( reader );
	}

	try
	{
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}

	return outlets;
    }

    public void turnOnOutlet( Integer outletId )
    {
	SocketHelper socketHelper = new SocketHelper( 23, rpcIp );
	BufferedReader reader = socketHelper.getReader();
	BufferedWriter writer = socketHelper.getWriter();

	if ( navigateToOutlets( reader, writer ) )
	{
	    powerOnOutlet( outletId, reader, writer );
	}

	try
	{
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    public void turnOffOutlet( Integer outletId )
    {
	SocketHelper socketHelper = new SocketHelper( 23, rpcIp );
	BufferedReader reader = socketHelper.getReader();
	BufferedWriter writer = socketHelper.getWriter();

	if ( navigateToOutlets( reader, writer ) )
	{
	    powerOffOutlet( outletId, reader, writer );
	}

	try
	{
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    public void rebootOutlet( Integer outletId )
    {
	SocketHelper socketHelper = new SocketHelper( 23, rpcIp );
	BufferedReader reader = socketHelper.getReader();
	BufferedWriter writer = socketHelper.getWriter();

	if ( navigateToOutlets( reader, writer ) )
	{
	    rebootOutlet( outletId, reader, writer );
	}

	try
	{
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    private Boolean navigateToOutlets( BufferedReader reader,
	    BufferedWriter writer )
    {
	String line = "";
	try
	{
	    while ( !line.contains( "6)...Logout" ) )
	    {
		line = reader.readLine();
	    }
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( OUTLETS_OPT );
	    writer.newLine();
	    writer.flush();
	}
	catch ( IOException ex )
	{
	    return Boolean.FALSE;
	}
	return Boolean.TRUE;
    }

    private List<Outlet> getOutletsFromOutletScreen(
	    BufferedReader reader )
    {
	List<Outlet> outlets = new ArrayList<>();
	String line = "";
	Boolean parsingOutlets = false;
	try
	{
	    while ( !line.contains( "Type \"Help\" for a list of commands" ) )
	    {
		line = reader.readLine();

		if ( parsingOutlets && !(line.contains( "On" ) || line.
			contains( "Off" )) )
		{
		    parsingOutlets = false;
		}

		if ( parsingOutlets )
		{
		    final Outlet outlet = parseStringToOutlet( line );
		    outlets.add( outlet );
		}

		if ( line.contains( "Number" ) && line.contains( "Name" ) && line.
			contains( "Status" ) )
		{
		    parsingOutlets = true;
		}
	    }
	    while ( (char) reader.read() != '>' )
	    {
	    }
	}
	catch ( IOException ex )
	{

	}
	return outlets;
    }

    private Outlet parseStringToOutlet( String line )
    {
	Outlet outlet = new Outlet();
	Pattern outletPattern = Pattern.compile(
		"\\s+(\\d)\\s+(.{10})\\s+(\\d)\\s+(O.{1,2})" );
	Matcher outletMatcher = outletPattern.matcher( line );
	if ( outletMatcher.matches() )
	{
	    outlet.setName( outletMatcher.group( 2 ).trim() );
	    outlet.setId( Integer.parseInt( outletMatcher.group( 1 ) ) );
	    outlet.setStatus( outletMatcher.group( 4 ).trim().equals( "On" ) );
	}

	return outlet;
    }

    private void powerOnOutlet( Integer outletId, BufferedReader reader,
	    BufferedWriter writer )
    {
	String line = "";
	try
	{
	    while ( !line.contains( "Type \"Help\" for a list of commands" ) )
	    {
		line = reader.readLine();
	    }
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "on " + outletId );
	    writer.newLine();
	    writer.flush();
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "y" );
	    writer.newLine();
	    writer.flush();
	}
	catch ( IOException ex )
	{
	}
    }

    private void powerOffOutlet( Integer outletId, BufferedReader reader,
	    BufferedWriter writer )
    {
	String line = "";
	try
	{
	    while ( !line.contains( "Type \"Help\" for a list of commands" ) )
	    {
		line = reader.readLine();
	    }
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "off " + outletId );
	    writer.newLine();
	    writer.flush();
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "y" );
	    writer.newLine();
	    writer.flush();
	}
	catch ( IOException ex )
	{
	}
    }

    private void rebootOutlet( Integer outletId, BufferedReader reader,
	    BufferedWriter writer )
    {
	String line = "";
	try
	{
	    while ( !line.contains( "Type \"Help\" for a list of commands" ) )
	    {
		line = reader.readLine();
	    }
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "reboot " + outletId );
	    writer.newLine();
	    writer.flush();
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "y" );
	    writer.newLine();
	    writer.flush();
	}
	catch ( IOException ex )
	{
	}
    }

}
