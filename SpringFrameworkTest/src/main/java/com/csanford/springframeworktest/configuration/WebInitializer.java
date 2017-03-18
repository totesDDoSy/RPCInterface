package com.csanford.springframeworktest.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
	return new Class[]
	{
	    WebConfig.class
	};
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
	return null;
    }

    @Override
    protected String[] getServletMappings()
    {
	return new String[]
	{
	    "/"
	};
    }

}
