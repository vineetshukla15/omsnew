package com.oms.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.oms.AbstractControllerTest;
import com.oms.service.OMSUserService;

/**
 * Test the authentication controller
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationControllerTest extends AbstractControllerTest { 

    @Autowired
    private OMSUserService accountService;  

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testSample() throws Exception {

        String uri = "/api/user/ritesh.nailwal";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoicGFwaWRha29zIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTQ1NjcxODIwNSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjQ1NjczY2Y0LWYwNDYtNGQxZi04NGE5LTA1NjVmZjhhZThiNyIsImNsaWVudF9pZCI6ImNsaWVudGFwcCJ9.Au9dJencn68uCSoy7w8CrTCLoT2KTAB8gDKYrww9KxA");

        MvcResult mvcResult = mvc.perform(
          MockMvcRequestBuilders.get(uri).headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content("")
        ).andReturn();

        // Collect the data
        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected Http response body to have a value", content.trim().length() > 0);

    }

}
