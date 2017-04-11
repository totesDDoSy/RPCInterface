package com.csanford.rpcinterface.utils;

import java.io.BufferedReader;

public class RPCHelper
{
	private String rpcIp;

	public RPCHelper( String rpcIp )
	{
		this.rpcIp = rpcIp;
	}
	public Boolean getOutlets()
	{
		SocketHelper socketHelper = new SocketHelper(23, rpcIp);
		BufferedReader reader = socketHelper.getReader();

		return Boolean.FALSE;
	}
	
}
