package com.csanford.rpcinterface.ajax;

import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class OutletCommandRequest
{
    @NotEmpty
    String command;
    @NotNull
    Long rpcId;
    @NotNull
    Integer outletId;
    @Size( max = 10 )
    String newName;
    Map<String, String> args;

    public String getCommand()
    {
	return command;
    }

    public void setCommand( String command )
    {
	this.command = command;
    }

    public Long getRpcId()
    {
	return rpcId;
    }

    public void setRpcId( Long rpcId )
    {
	this.rpcId = rpcId;
    }

    public Integer getOutletId()
    {
	return outletId;
    }

    public void setOutletId( Integer outletId )
    {
	this.outletId = outletId;
    }

    public String getNewName()
    {
	return newName;
    }

    public void setNewName( String newName )
    {
	this.newName = newName;
    }

    public Map<String, String> getArgs()
    {
	return args;
    }

    public void setArgs( Map<String, String> args )
    {
	this.args = args;
    }

}
