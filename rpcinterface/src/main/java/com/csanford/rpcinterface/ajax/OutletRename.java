package com.csanford.rpcinterface.ajax;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class OutletRename
{
    @NotBlank( message = "New name cannot be blank." )
    String newName;

    public String getNewName()
    {
	return newName;
    }

    public void setNewName( String newName )
    {
	this.newName = newName;
    }

}
