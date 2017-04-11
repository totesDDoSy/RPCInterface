package com.csanford.rpcinterface.model;

import com.csanford.rpcinterface.h2.RPC;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class RPCModel
{
	@NotEmpty
    String name;

	@NotEmpty
    String ipaddr;
    String username;
    String password;

    public String getName()
    {
	return name;
    }

    public void setName( String name )
    {
	this.name = name;
    }

    public String getIpaddr()
    {
	return ipaddr;
    }

    public void setIpaddr( String ipaddr )
    {
	this.ipaddr = ipaddr;
    }

    public String getUsername()
    {
	return username;
    }

    public void setUsername( String username )
    {
	this.username = username;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword( String password )
    {
	this.password = password;
    }

    public RPC convertToDAO()
    {
	return new RPC( name, ipaddr, username, password );
    }

    public static RPCModel convertFromDAO( RPC rpc )
    {
	RPCModel model = new RPCModel();
	model.setIpaddr( rpc.getIpaddr() );
	model.setName( rpc.getName() );
	model.setUsername( rpc.getUsername() );
	model.setPassword( rpc.getPassword() );

	return model;
    }

    @Override
    public String toString()
    {
	return "RPCModel{" + "name=" + name + ", ipaddr=" + ipaddr + ", username=" + username + ", password=" + password + '}';
    }

}
