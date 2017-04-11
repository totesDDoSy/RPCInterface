package com.csanford.rpcinterface.model;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class Outlet
{
    Integer id;
    String name;
    Boolean status;

    public Outlet( Integer id, String name, Boolean status )
    {
	this.id = id;
	this.name = name;
	this.status = status;
    }

    public Integer getId()
    {
	return id;
    }

    public void setId( Integer id )
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName( String name )
    {
	this.name = name;
    }

    public Boolean getStatus()
    {
	return status;
    }

    public void setStatus( Boolean status )
    {
	this.status = status;
    }

    @Override
    public String toString()
    {
	return "Outlet{" + "id=" + id + ", name=" + name + ", status=" + status + '}';
    }

}
