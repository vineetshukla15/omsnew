/**
 * 
 */
package com.oms.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.tavant.api.auth.model.OMSUser;

import com.oms.model.Opportunity;
import com.oms.model.OpportunityDocument;
import com.oms.model.OpportunityDocuments;
import com.oms.service.OMSUserService;
import com.oms.service.OpportunityService;
import com.oms.settings.errors.GenericResponse;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the Opportunity controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OpportunityControllerTest {

	@InjectMocks
	OpportunityController opportunityController;
	
	@Mock
	private OpportunityService opportunityService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<Opportunity> opportunityLst;
	
	private Opportunity opportunity;
	
    OMSUser user;
	
	Authentication authentication;
	
	SecurityContext securityContext;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn("abc");
		securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		user = new OMSUser();
		user.setUsername("abc");
		user.setId(12345L);
		
		opportunityLst = new ArrayList<Opportunity>();
		opportunity = new Opportunity();
		opportunity.setOpportunityId(12345L);
		opportunityLst.add(opportunity);
    }
	
	/**
	 * Tests getOpportunities() method.
	 */
	@Test
	public void testGetOpportunities(){
		when(opportunityService.getAllOpportunity()).thenReturn(opportunityLst);
		List<Opportunity> list = opportunityController.getOpportunities();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getOpportunityById() method.
	 */
	@Test
	public void testGetOpportunityById(){
		when(opportunityService.getOpportunityById(any(Long.class))).thenReturn(opportunity);
		Opportunity opp = opportunityController.getOpportunityById(12345L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests addNewOpportunity() method.
	 */
	@Test
	public void testAddNewOpportunity() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(opportunityService.addOpportunity(any(Opportunity.class), 1L)).thenReturn(opportunity);
		Opportunity opp = opportunityController.addNewOpportunity(opportunity);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests updateOpportunity() method.
	 */
	@Test
	public void testUpdateOpportunity() {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(opportunityService.updateOpportunity(any(Opportunity.class), 1L)).thenReturn(opportunity);
		Opportunity opp = opportunityController.updateOpportunity(opportunity);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests removeOpportunity() method.
	 */
	@Test
	public void testRemoveOpportunity(){
		Mockito.doNothing().when(opportunityService).deleteOpportunity(any(Long.class));
	}
	
	/**
	 * Tests copyOpportunity() method.
	 */
	@Test
	public void testCopyOpportunity() {
		when(opportunityService.copyOpportunity(any(Long.class), any(Boolean.class), 1L)).thenReturn(opportunity);
		Opportunity opp = opportunityController.copyOpportunity(12345L, false);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests searchOpportunity() method.
	 */
	@Test
	public void testSearchContact() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(opportunityService.searchOpportunity(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Opportunity>());
		PaginationResponseVO<Opportunity> opp = opportunityController.searchOpportunity(searchRequest);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests getDocuments() method.
	 */
	@Test
	public void testGetDocuments(){
		Collection<OpportunityDocument> doc = new ArrayList<>();
		when(opportunityService.getDocuments(any(Long.class))).thenReturn(doc);
		Collection<OpportunityDocument> list = opportunityController.getDocuments(12345L);
		Assert.assertNotNull(list);
	}
	
	/**
	 * Tests downloadDocument() method.
	 * @throws IOException 
	 */
	@Test
	public void testDownloadDocument() throws IOException{
		OpportunityDocument document =  new OpportunityDocument();
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		document.setPath("D:\\dummy.txt");
		document.setContent(ba2);
		when(opportunityService.getDocument(any(Long.class),any(Long.class))).thenReturn(document);
		ResponseEntity<InputStreamResource> resp = opportunityController.downloadDocument(12345L, 12345L);
		Assert.assertNotNull(resp);
		file.delete();
	}
	
	/**
	 * Tests uploadDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testUploadDocuments() throws IOException {
		Collection<OpportunityDocument> doc = new ArrayList<>();
		List<OpportunityDocument> docLst = new ArrayList<>();
		OpportunityDocument oppDoc = new OpportunityDocument();
		oppDoc.setOpportinutyDocId(12345L);
		oppDoc.setName("Doc1");
		doc.add(oppDoc);
		docLst.add(oppDoc);
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\abc.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		OpportunityDocuments docs = new OpportunityDocuments();
		docs.setDocumentDetails(docLst);
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file",
	            file.getName(), "text/plain", IOUtils.toByteArray(input));
		MultipartFile[] documents = {multipartFile};
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(opportunityService.addDocuments(any(OpportunityDocuments.class))).thenReturn(doc);
		GenericResponse<OpportunityDocument> resp = opportunityController.uploadDocuments(docs, documents);
		Assert.assertNotNull(resp);
		file.delete();
	}
	
	/**
	 * Tests updateDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testUpdateDocuments() throws IOException {
		Collection<OpportunityDocument> doc = new ArrayList<>();
		List<OpportunityDocument> docLst = new ArrayList<>();
		OpportunityDocument oppDoc = new OpportunityDocument();
		oppDoc.setOpportinutyDocId(12345L);
		oppDoc.setName("Doc1");
		doc.add(oppDoc);
		docLst.add(oppDoc);
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\abc.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		OpportunityDocuments docs = new OpportunityDocuments();
		docs.setDocumentDetails(docLst);
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file",
	            file.getName(), "text/plain", IOUtils.toByteArray(input));
		MultipartFile[] documents = {multipartFile};
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(opportunityService.updateDocuments(any(OpportunityDocuments.class))).thenReturn(doc);
		GenericResponse<OpportunityDocument> resp = opportunityController.uploadDocuments(docs, documents);
		Assert.assertNotNull(resp);
		file.delete();
	}
}
