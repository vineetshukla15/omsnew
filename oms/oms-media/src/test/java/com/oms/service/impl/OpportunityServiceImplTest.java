package com.oms.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.tavant.api.auth.model.OMSUser;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.BillingSource;
import com.oms.model.Company;
import com.oms.model.Opportunity;
import com.oms.model.OpportunityDocument;
import com.oms.model.OpportunityDocuments;
import com.oms.model.OpportunityVersion;
import com.oms.model.SalesCategory;
import com.oms.model.Terms;
import com.oms.repository.OpportunityDocumentRepository;
import com.oms.repository.OpportunityRepository;
import com.oms.repository.OpportunityVersionRepository;
import com.oms.repository.ProposalRepository;
import com.oms.repository.ProposalStatusRepository;
import com.oms.service.FileUploaderService;
import com.oms.service.ProposalService;
import com.oms.viewobjects.Document;

/**
 * Tests the Opportunity service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OpportunityServiceImplTest {

	@InjectMocks
	OpportunityServiceImpl opportunityServiceImpl;
	
	@Mock
	OpportunityRepository opportunityRepository;
	
	@Mock
	private ProposalRepository proposalRepository;

	@Mock
	private ProposalStatusRepository proposalStatusRepository;

	@Mock
	private OpportunityDocumentRepository opportunityDocumentRepository;
	
	@Mock
	private OpportunityVersionRepository opportunityVersionRepository;

	@Mock
	ProposalService proposalService;
	
	@Mock
	private FileUploaderService fileUploader;
	
	private List<Opportunity> opportunityLst;
	
	private Opportunity opportunity;
	
	private OpportunityDocument oppDoc;
	
	private List<OpportunityDocument> oppDocLst;
	
	private OpportunityDocuments opportunityDocuments;
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {
		opportunityLst = new ArrayList<Opportunity>();
		
		oppDocLst =  new ArrayList<>();
		oppDoc = new OpportunityDocument();
		oppDoc.setOpportinutyDocId(12345L);
		oppDoc.setPath("D:\\dummy.txt");
		oppDoc.setOpportunity(opportunity);
		oppDocLst.add(oppDoc);
		
		opportunity = new Opportunity();
		opportunity.setOpportunityId(12345L);
		opportunity.setName("abc");
		opportunity.setDeleted(false);
		opportunity.setSubmitted(false);
		opportunity.setDocuments(oppDocLst);
		opportunity.setAdvertiser(new Company());
		opportunity.setAdvertiserDiscount(10.0);
		opportunity.setAgency(new Company());
		opportunity.setBillingSource(new BillingSource());
		opportunity.setBudget(10.0);
		opportunity.setCurrency("INR");
		opportunity.setDescription("Opp");
		opportunity.setDueDate(null);
		opportunity.setEndDate(null);
		opportunity.setNotes("notes");
		opportunity.setPercentageOfClose(2);
		opportunity.setSalesCategory(new SalesCategory());
		opportunity.setPricingModel("model");
		opportunity.setSalesPerson(new OMSUser());
		opportunity.setStartDate(null);
		opportunity.setTrafficker(new OMSUser());
		opportunity.setTerms(new Terms());
		opportunity.setVersions(new HashSet<OpportunityVersion>());
		opportunity.setDocuments(oppDocLst);
		
		opportunityLst.add(opportunity);
		
		opportunityDocuments = new OpportunityDocuments();
		opportunityDocuments.setOpportunityId(12345L);
		opportunityDocuments.setDocumentDetails(oppDocLst);
		opportunityDocuments.setUserId(12345L);
    }
	
	/**
	 * Tests positive scenario for getAllOpportunity() method.
	 */
	@Test
	public void testGetAllOpportunityPositive() {
		when(opportunityRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(opportunityLst);
		List<Opportunity> list = opportunityServiceImpl.getAllOpportunity();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}
	
	/**
	 * Tests positive scenario for getOpportunityById() method.
	 */
	@Test
	public void testGetOpportunityByIdPositive() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		Opportunity opp = opportunityServiceImpl.getOpportunityById(12345L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests negative scenario for getOpportunityById() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetOpportunityByIdNegative() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(null);
		Opportunity opp = opportunityServiceImpl.getOpportunityById(12345L);
	}
	
	/**
	 * Tests positive scenario for addOpportunity() method.
	 */
	@Test
	public void testAddOpportunityPositive() {
		when(opportunityRepository.save(any(Opportunity.class))).thenReturn(opportunity);
		Opportunity opp = opportunityServiceImpl.addOpportunity(opportunity, 1L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests negative scenario for deleteOpportunity() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteOpportunityNegative() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(null);
		opportunityServiceImpl.deleteOpportunity(12345L);
	}
	
	/**
	 * Tests positive scenario for deleteOpportunity() method.
	 */
	@Test
	public void testDeleteOpportunityPositive() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		Mockito.doNothing().when(opportunityRepository).softDelete(any(Long.class));
		opportunityServiceImpl.deleteOpportunity(12345L);
	}
	
	/**
	 * Tests negative scenario for updateOpportunity() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateOpportunityNegative() {
		opportunity.setOpportunityId(null);
		Opportunity opp = opportunityServiceImpl.updateOpportunity(opportunity,1L);
	}
	
	/**
	 * Tests positive scenario for updateOpportunity() method.
	 */
	@Test
	public void testUpdateOpportunityPositive() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityRepository.save(any(Opportunity.class))).thenReturn(opportunity);
		Opportunity opp = opportunityServiceImpl.updateOpportunity(opportunity,1L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests copyOpportunity() method.
	 */
	@Test
	public void testCopyOpportunityVersion() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityRepository.save(any(Opportunity.class))).thenReturn(opportunity);
		when(opportunityVersionRepository.findMaxVersionOfOpportunity(any(Long.class))).thenReturn(null);
		Opportunity opp = opportunityServiceImpl.copyOpportunity(12345L, true,1L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests copyOpportunity() method.
	 */
	@Test
	public void testCopyOpportunity() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityRepository.save(any(Opportunity.class))).thenReturn(opportunity);
		Opportunity opp = opportunityServiceImpl.copyOpportunity(12345L, false,1L);
		Assert.assertNotNull(opp);
	}
	
	/**
	 * Tests negative scenario for getDocument() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetDocumentNegative() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityDocumentRepository.findOne(any(Long.class))).thenReturn(null);
		OpportunityDocument document = opportunityServiceImpl.getDocument(12345L, 12345L);
	}
	
	/**
	 * Tests positive scenario for getDocument() method.
	 * @throws IOException 
	 */
	@Test
	public void testGetDocumentPositive() throws IOException {
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		oppDoc.setPath("D:\\dummy.txt");
		oppDoc.setContent(ba2);
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityDocumentRepository.findOne(any(Long.class))).thenReturn(oppDoc);
		OpportunityDocument document = opportunityServiceImpl.getDocument(12345L, 12345L);
		Assert.assertNotNull(document);
		file.delete();
	}
	
	/**
	 * Tests getDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testGetDocuments() {
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		Collection<OpportunityDocument> document = opportunityServiceImpl.getDocuments(12345L);
		Assert.assertNotNull(document);
	}
	
	/**
	 * Tests positive scenario for updateDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testUpdateDocumentsPositive() throws IOException {
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		oppDoc.setPath("D:\\dummy.txt");
		oppDoc.setContent(ba2);
		Document document = new Document();
		document.setPath("D:\\dummy.txt");
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityDocumentRepository.save(any(OpportunityDocument.class))).thenReturn(oppDoc);
		when(fileUploader.update(any(Document.class))).thenReturn(document);
		Collection<OpportunityDocument> oppDocument = opportunityServiceImpl.updateDocuments(opportunityDocuments);
		Assert.assertNotNull(oppDocument);
		file.delete();
	}
	
	/**
	 * Tests negative scenario for updateDocuments() method.
	 * @throws IOException 
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateDocumentsNegative() throws IOException {
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		oppDoc.setPath("D:\\dummy.txt");
		oppDoc.setContent(null);
		oppDoc.setOpportinutyDocId(null);
		Document document = new Document();
		document.setPath("D:\\dummy.txt");
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityDocumentRepository.save(any(OpportunityDocument.class))).thenReturn(oppDoc);
		when(fileUploader.update(any(Document.class))).thenReturn(document);
		Collection<OpportunityDocument> oppDocument = opportunityServiceImpl.updateDocuments(opportunityDocuments);
		Assert.assertNotNull(oppDocument);
		file.delete();
	}
	
	/**
	 * Tests positive scenario for addDocuments() method.
	 * @throws IOException 
	 */
	@Test
	public void testAddDocumentsPositive() throws IOException {
		byte[] ba2 = {1,2,3,4,5};
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		oppDoc.setPath("D:\\dummy.txt");
		oppDoc.setContent(ba2);
		Document document = new Document();
		document.setPath("D:\\dummy.txt");
		when(opportunityRepository.findById(any(Long.class))).thenReturn(opportunity);
		when(opportunityDocumentRepository.save(any(OpportunityDocument.class))).thenReturn(oppDoc);
		when(fileUploader.save(any(Document.class))).thenReturn(document);
		Collection<OpportunityDocument> oppDocument = opportunityServiceImpl.addDocuments(opportunityDocuments);
		Assert.assertNotNull(oppDocument);
		file.delete();
	}
	
}
