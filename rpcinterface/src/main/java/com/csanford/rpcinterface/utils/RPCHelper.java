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

    private static final int CONFIG_OPT = (int) '3';
    private static final int CONFIG_OUTLETS_OPT = (int) '6';
    private static final int RENAME_CONFIG_OPT = (int) '4';

    private static final String COMMAND_ON = "on";
    private static final String COMMAND_OFF = "off";
    private static final String COMMAND_REBOOT = "reboot";
    private static final int TCP_PORT = 23;

    public RPCHelper( String rpcIp )
    {
	this.rpcIp = rpcIp;
    }

    public List<Outlet> getOutlets()
    {
	try
	{
	    SocketHelper socketHelper = new SocketHelper( TCP_PORT, rpcIp );
	    BufferedReader reader = socketHelper.getReader();
	    BufferedWriter writer = socketHelper.getWriter();
	    List<Outlet> outlets = new ArrayList<>();

	    if ( navigateToOutlets( reader, writer ) )
	    {
		outlets = getOutletsFromOutletScreen( reader );
	    }
	    reader.close();
	    writer.close();

	    return outlets;
	}
	catch ( IOException ex )
	{
	    return null;
	}
    }

    public void turnOnOutlet( Integer outletId )
    {
	outletCommand( outletId, COMMAND_ON );
    }

    public void turnOffOutlet( Integer outletId )
    {
	outletCommand( outletId, COMMAND_OFF );
    }

    public void rebootOutlet( Integer outletId )
    {
	outletCommand( outletId, COMMAND_REBOOT );
    }

    public void renameOutlet( Integer outletId, String newName )
    {
	try
	{
	    SocketHelper socketHelper = new SocketHelper( TCP_PORT, rpcIp );
	    BufferedReader reader = socketHelper.getReader();
	    BufferedWriter writer = socketHelper.getWriter();

	    if ( navigateToOutletRename( reader, writer ) )
	    {
		executeRename( reader, writer, outletId, newName );
	    }
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    // ----------- HELPER METHODS -------------- //
    private void navigateTo( BufferedReader reader, BufferedWriter writer,
	    String lastLine, int command_opt ) throws
	    IOException
    {
	String line = "";
	while ( !line.contains( lastLine ) )
	{
	    line = reader.readLine();
	}
	while ( (char) reader.read() != '>' )
	{
	}
	if ( command_opt > -1 )
	{
	    writer.write( command_opt );
	}
	writer.write( "\r\n" );
	writer.flush();
    }

    private Boolean navigateToOutlets( BufferedReader reader,
	    BufferedWriter writer )
    {
	try
	{
	    navigateTo( reader, writer, "6)...Logout", OUTLETS_OPT );
	}
	catch ( IOException ex )
	{
	    return Boolean.FALSE;
	}
	return Boolean.TRUE;
    }

    private boolean navigateToOutletRename( BufferedReader reader,
	    BufferedWriter writer )
    {
	String line = "";
	try
	{
	    navigateTo( reader, writer, "6)...Logout", CONFIG_OPT );
	    navigateTo( reader, writer, "6)...Outlets", CONFIG_OUTLETS_OPT );
	    navigateTo( reader, writer, "6)...Display Outlet Users",
		    RENAME_CONFIG_OPT );
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
	    Logger.getLogger( RPCHelper.class
		    .getName() ).
		    log( Level.SEVERE, null, ex );
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

    private void executeOutletCommand( Integer outletId, BufferedReader reader,
	    BufferedWriter writer, String command )
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
	    writer.write( command + " " + outletId );
	    writer.write( "\r\n" );
	    writer.flush();
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "y" );
	    writer.write( "\r\n" );
	    writer.flush();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class
		    .getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    public void outletCommand( Integer outletId, String command )
    {
	try
	{
	    SocketHelper socketHelper = new SocketHelper( TCP_PORT, rpcIp );
	    BufferedReader reader = socketHelper.getReader();
	    BufferedWriter writer = socketHelper.getWriter();

	    if ( navigateToOutlets( reader, writer ) )
	    {
		executeOutletCommand( outletId, reader, writer, command );
	    }
	    reader.close();
	    writer.close();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class.getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

    private void executeRename( BufferedReader reader, BufferedWriter writer,
	    Integer outletId, String newName )
    {
	try
	{
	    navigateTo( reader, writer, "8)...", (int) outletId.toString().
		    charAt( 0 ) );
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( newName );
	    writer.write( "\r\n" );
	    writer.flush();
	    navigateTo( reader, writer, "8)...", -1 );
	    navigateTo( reader, writer, "6)...Display Outlet Users", -1 );
	    navigateTo( reader, writer, "6)...Outlets", -1 );
	    while ( (char) reader.read() != '>' )
	    {
	    }
	    writer.write( "y" );
	    writer.write( "\r\n" );
	    writer.flush();
	}
	catch ( IOException ex )
	{
	    Logger.getLogger( RPCHelper.class
		    .getName() ).
		    log( Level.SEVERE, null, ex );
	}
    }

}
