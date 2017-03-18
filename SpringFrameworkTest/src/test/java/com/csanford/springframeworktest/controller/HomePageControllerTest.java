package com.csanford.springframeworktest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 * @author Cody Sanford <cody.b.sanford@gmail.com>
 */
public class HomePageControllerTest
{

    @Test
    public void testHomePage() throws Exception
    {
	HomePageController controller = new HomePageController();
	MockMvc mockMvc = standaloneSetup( controller ).build();
	mockMvc.perform( get( "/" ) ).
		andExpect( view().name( "home" ) );
    }

}
