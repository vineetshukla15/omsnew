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
import java.lang.reflect.InvocationTargetException;
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

import com.oms.exceptions.OMSSystemException;
import com.oms.model.Proposal;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.ProposalStatus;
import com.oms.service.OMSUserService;
import com.oms.service.ProposalService;
import com.oms.settings.errors.GenericResponse;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Tests the Proposal controller.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProposalControllerTest {
	
	@InjectMocks
	ProposalController proposalController;
	
	@Mock
	private ProposalService proposalService;
	
	@Mock
	private OMSUserService userService;
	
	@Mock
	private BaseController baseController;
	
	private List<Proposal> proposalLst;
	
	private Proposal proposal;
	
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
		
		proposalLst = new ArrayList<Proposal>();
		proposal = new Proposal();
		proposal.setProposalId(12345L);
		proposalLst.add(proposal);
    }
	
	/**
	 * Tests getAllProposal() method.
	 */
	@Test
	public void testGetAllProposal(){
		when(proposalService.getAllProposals()).thenReturn(proposalLst);
		List<Proposal> list = proposalController.getAllProposal();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests getProposal() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testGetProposal() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		when(proposalService.getProposalById(any(Long.class))).thenReturn(proposal);
		Proposal prop = proposalController.getProposal(12345L);
		Assert.assertNotNull(prop);
	}
	
	/**
	 * Tests addNewProposal() method.
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testAddNewProposal() throws IllegalAccessException, InvocationTargetException, Exception {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(proposalService.addProposal(any(Proposal.class), 1L)).thenReturn(proposal);
		Proposal prop = proposalController.addNewProposal(proposal);
		Assert.assertNotNull(prop);
	}
	
	/**
	 * Tests updateProposal() method.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testUpdateProposal() throws IllegalAccessException, InvocationTargetException {
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(proposalService.updateProposal(any(Proposal.class), 1L)).thenReturn(proposal);
		Proposal prop = proposalController.updateProposal(proposal);
		Assert.assertNotNull(prop);
	}
	
	/**
	 * Tests deleteProposal() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testDeleteProposal() throws IllegalAccessException, InvocationTargetException, OMSSystemException{
		Mockito.doNothing().when(proposalService).deleteProposal(any(Long.class));
	}
	
	/**
	 * Tests copyProposal() method.
	 * @throws OMSSystemException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testCopyProposal() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(proposalService.copyProposal(any(Long.class), any(Boolean.class), 1L)).thenReturn(proposal);
		Proposal prop = proposalController.copyProposal(12345L, false);
		Assert.assertNotNull(prop);
	}
	
	/**
	 * Tests searchProposal() method.
	 */
	@Test
	public void testSearchProposal() {
		SearchRequestVO searchRequest = new SearchRequestVO();
		searchRequest.setLength(10);
		searchRequest.setDraw(10);
		when(proposalService.searchProposal(any(SearchRequestVO.class))).thenReturn(new PaginationResponseVO<Proposal>());
		PaginationResponseVO<Proposal> opp = proposalController.searchProposal(searchRequest);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests getProposalDocuments() method.
	 */
	@Test
	public void testGetProposalDocuments(){
		Collection<ProposalDocument> doc = new ArrayList<>();
		when(proposalService.getDocuments(any(Long.class))).thenReturn(doc);
		Collection<ProposalDocument> list = proposalController.getProposalDocuments(12345L);
		Assert.assertNotNull(list);
	}
	
	/**
	 * Tests getAllProposalStatus() method.
	 */
	@Test
	public void testGetAllProposalStatus(){
		List<ProposalStatus> statusList = new ArrayList<>();
		ProposalStatus status = new ProposalStatus();
		status.setStatusId(12345L);
		statusList.add(status);
		when(proposalService.getAllProposalStatus()).thenReturn(statusList);
		List<ProposalStatus> list = proposalController.getAllProposalStatus();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests downloadDocument() method.
	 * @throws IOException 
	 */
	@Test
	public void testDownloadDocument() throws IOException{
		ProposalDocument document =  new ProposalDocument();
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		document.setPath("D:\\dummy.txt");
		document.setContent(ba2);
		when(proposalService.getDocument(any(Long.class),any(Long.class))).thenReturn(document);
		ResponseEntity<InputStreamResource> resp = proposalController.downloadDocument(12345L, 12345L);
		Assert.assertNotNull(resp);
		file.delete();
	}
	
	/**
	 * Tests uploadDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testUploadDocuments() throws IOException {
		Collection<ProposalDocument> doc = new ArrayList<>();
		List<ProposalDocument> docLst = new ArrayList<>();
		ProposalDocument propDoc = new ProposalDocument();
		propDoc.setProposalDocId(12345L);
		propDoc.setName("Doc1");
		doc.add(propDoc);
		docLst.add(propDoc);
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\abc.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		ProposalDocuments docs = new ProposalDocuments();
		docs.setDocumentDetails(docLst);
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file",
	            file.getName(), "text/plain", IOUtils.toByteArray(input));
		MultipartFile[] documents = {multipartFile};
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(proposalService.addDocuments(any(ProposalDocuments.class))).thenReturn(doc);
		GenericResponse<ProposalDocument> resp = proposalController.uploadDocuments(docs, documents);
		Assert.assertNotNull(resp);
		file.delete();
	}
	
	/**
	 * Tests updateDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testUpdateDocuments() throws IOException {
		Collection<ProposalDocument> doc = new ArrayList<>();
		List<ProposalDocument> docLst = new ArrayList<>();
		ProposalDocument propDoc = new ProposalDocument();
		propDoc.setProposalDocId(12345L);
		propDoc.setName("Doc1");
		doc.add(propDoc);
		docLst.add(propDoc);
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\abc.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		ProposalDocuments docs = new ProposalDocuments();
		docs.setDocumentDetails(docLst);
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file",
	            file.getName(), "text/plain", IOUtils.toByteArray(input));
		MultipartFile[] documents = {multipartFile};
		when(userService.findByUserName(any(String.class))).thenReturn(user);
		when(proposalService.updateDocuments(any(ProposalDocuments.class))).thenReturn(doc);
		GenericResponse<ProposalDocument> resp = proposalController.updateDocuments(docs, documents);
		Assert.assertNotNull(resp);
		file.delete();
	}

}
