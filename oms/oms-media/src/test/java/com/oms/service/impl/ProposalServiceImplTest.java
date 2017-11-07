/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.Role;

import com.oms.exceptions.OMSSystemException;
import com.oms.model.BillingSource;
import com.oms.model.Company;
import com.oms.model.LineItems;
import com.oms.model.Opportunity;
import com.oms.model.Proposal;
import com.oms.model.ProposalAudit;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.ProposalStatus;
import com.oms.model.ProposalVersion;
import com.oms.model.SalesCategory;
import com.oms.model.Terms;
import com.oms.repository.OMSUserRepository;
import com.oms.repository.ProposalDocumentRepository;
import com.oms.repository.ProposalRepository;
import com.oms.repository.ProposalStatusRepository;
import com.oms.repository.ProposalVersionRepository;
import com.oms.service.FileUploaderService;
import com.oms.viewobjects.Document;

/**
 * Tests the Proposal service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProposalServiceImplTest {

	@InjectMocks
	ProposalServiceImpl proposalServiceImpl;

	@Mock
	private ProposalRepository proposalRepository;

	@Mock
	private ProposalStatusRepository proposalStatusRepository;

	@Mock
	private OMSUserRepository userRepository;

	@Mock
	private ProposalDocumentRepository proposalDocumentRepository;

	@Mock
	private ProposalVersionRepository proposalVersionRepository;

	@Mock
	private FileUploaderService fileUploader;

	private List<Proposal> proposalLst;

	private Proposal proposal;

	private ProposalDocument propDoc;

	private List<ProposalDocument> propDocLst;

	private ProposalDocuments proposalDocuments;

	/**
	 * Sets up the test data.
	 */
	@Before
	public void setUp() {
		proposalLst = new ArrayList<Proposal>();
		propDocLst = new ArrayList<>();

		propDoc = new ProposalDocument();
		propDoc.setProposalDocId(12345L);
		propDoc.setProposal(proposal);
		propDoc.setProposalDocId(12345L);
		propDoc.setPath("D:\\dummy.txt");
		propDocLst.add(propDoc);

		proposal = new Proposal();
		proposal.setProposalId(12345L);
		proposal.setName("abc");
		proposal.setDeleted(false);
		proposal.setSubmitted(false);
		proposal.setAction("action");
		proposal.setStatus(new ProposalStatus());
		proposal.setAudits(new ArrayList<ProposalAudit>());
		proposal.setDocuments(propDocLst);
		proposal.setAdvertiserDiscount(10.0);
		proposal.setBudget(10.0);
		proposal.setCurrency("INR");
		proposal.setDescription("Proposal");
		proposal.setDueDate(null);
		proposal.setEndDate(null);
		proposal.setNotes("notes");
		proposal.setPercentageOfClose(2);
		proposal.setSalesCategory(new SalesCategory());
		proposal.setPricingModel("model");
		proposal.setStartDate(null);
		proposal.setAdvertiser(new Company());
		proposal.setAgency(new Company());
		proposal.setBillingSource(new BillingSource());
		proposal.setSalesPerson(new OMSUser());
		proposal.setTerms(new Terms());
		proposal.setTrafficker(new OMSUser());
		proposal.setOpportunity(new Opportunity());
		proposal.setLineItems(new HashSet<LineItems>());
		proposal.setCreatedBy(12345L);
		proposal.setUpdatedBy(12345L);
		proposal.setVersions(new HashSet<ProposalVersion>());
		proposalLst.add(proposal);

		proposalDocuments = new ProposalDocuments();
		proposalDocuments.setProposalId(12345L);
		proposalDocuments.setDocumentDetails(propDocLst);
		proposalDocuments.setUserId(12345L);
	}

	/**
	 * Tests positive scenario for getAllProposals() method.
	 */
	@Test
	public void testGetAllProposalsPositive() {
		when(proposalRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(proposalLst);
		List<Proposal> list = proposalServiceImpl.getAllProposals();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Tests positive scenario for getProposalById() method.
	 */
	@Test
	public void testGetProposalByIdPositive() {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		Proposal prop = proposalServiceImpl.getProposalById(12345L);
		Assert.assertNotNull(prop);
	}

	/**
	 * Tests positive scenario for addProposal() method.
	 * 
	 * @throws OMSSystemException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testAddProposalPositive() throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);
		Proposal prop = proposalServiceImpl.addProposal(proposal,1L);
		Assert.assertNotNull(prop);
	}

	/**
	 * Tests negative scenario for deleteProposal() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testDeleteProposalNegative() {
		when(proposalRepository.findById(any(Long.class))).thenReturn(null);
		proposalServiceImpl.deleteProposal(12345L);
	}

	/**
	 * Tests positive scenario for deleteProposal() method.
	 */
	@Test
	public void testDeleteProposalPositive() {
		when(proposalRepository.findById(any(Long.class))).thenReturn(proposal);
		Mockito.doNothing().when(proposalRepository).softDelete(any(Long.class));
		proposalServiceImpl.deleteProposal(12345L);
	}

	/**
	 * Tests negative scenario for updateProposal() method.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateProposalNegative() throws IllegalAccessException, InvocationTargetException {
		proposal.setProposalId(null);
		Proposal prop = proposalServiceImpl.updateProposal(proposal,1L);
	}

	/**
	 * Tests positive scenario for updateProposal() method.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testUpdateProposalPositive() throws IllegalAccessException, InvocationTargetException {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);
		Proposal prop = proposalServiceImpl.updateProposal(proposal,1L);
		Assert.assertNotNull(prop);
	}

	/**
	 * Tests negative scenario for getDocument() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetDocumentNegative() {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(null);
		ProposalDocument document = proposalServiceImpl.getDocument(12345L, 12345L);
	}

	/**
	 * Tests positive scenario for getDocument() method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetDocumentPositive() throws IOException {
		byte[] ba2 = { 1, 2, 3, 4, 5 };
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		propDoc.setPath("D:\\dummy.txt");
		propDoc.setContent(ba2);
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalDocumentRepository.findOne(any(Long.class))).thenReturn(propDoc);
		ProposalDocument document = proposalServiceImpl.getDocument(12345L, 12345L);
		Assert.assertNotNull(document);
		file.delete();
	}

	/**
	 * Tests negative scenario for getDocuments() method.
	 */
	@Test(expected = OMSSystemException.class)
	public void testGetDocumentsNegative() {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(null);
		Collection<ProposalDocument> documents = proposalServiceImpl.getDocuments(12345L);
	}

	/**
	 * Tests positive scenario for getDocuments() method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetDocumentsPositive() throws IOException {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		Collection<ProposalDocument> documents = proposalServiceImpl.getDocuments(12345L);
		Assert.assertNotNull(documents);
	}

	/**
	 * Tests getProposalPriceAdmins() method.
	 */
	@Test
	@Ignore
	public void testGetProposalPriceAdmins() {
		Role role = new Role();
		role.setRoleName("Pricing Manager");
		List<OMSUser> omsUserLst = new ArrayList<OMSUser>();
		OMSUser omsUser = new OMSUser();
		omsUser.setRole(role);
		omsUserLst.add(omsUser);
		when(userRepository.findByRoleName(any(String.class))).thenReturn(omsUserLst);
	//	List<String> lst = proposalServiceImpl.getProposalPriceAdmins();
		//Assert.assertNotNull(lst);
	}

	/**
	 * Tests getProposalMediaPlanners() method.
	 */
	@Test
	public void testGetProposalMediaPlanners() {
		Role role = new Role();
		role.setRoleName("Media Planner");
		List<OMSUser> omsUserLst = new ArrayList<OMSUser>();
		OMSUser omsUser = new OMSUser();
		omsUser.setRole(role);
		omsUserLst.add(omsUser);
		when(userRepository.findByRoleName(any(String.class))).thenReturn(omsUserLst);
		List<String> lst = proposalServiceImpl.getProposalMediaPlanners();
		Assert.assertNotNull(lst);
	}

	/**
	 * Tests getProposalAdmins() method.
	 */
	@Test
	public void testGetProposalAdmins() {
		Role role = new Role();
		role.setRoleName("Administrator");
		List<OMSUser> omsUserLst = new ArrayList<OMSUser>();
		OMSUser omsUser = new OMSUser();
		omsUser.setRole(role);
		omsUserLst.add(omsUser);
		when(userRepository.findByRoleName(any(String.class))).thenReturn(omsUserLst);
		List<String> lst = proposalServiceImpl.getProposalAdmins();
		Assert.assertNotNull(lst);
	}

	/**
	 * Tests getAllProposalStatus() method.
	 */
	@Test
	public void testGetAllProposalStatus() {
		when(proposalStatusRepository.findAll()).thenReturn(new ArrayList<ProposalStatus>());
		List<ProposalStatus> lst = proposalServiceImpl.getAllProposalStatus();
		Assert.assertNotNull(lst);
	}

	/**
	 * Tests copyProposal() method.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testCopyProposalVersion() throws IllegalAccessException, InvocationTargetException {
		OMSUser omsUser = new OMSUser();
		omsUser.setId(12345L);
		ProposalVersion proposalVersion = new ProposalVersion();
		proposalVersion.setProposal(proposal);
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);
		when(userRepository.findByUserId(any(Long.class))).thenReturn(omsUser);
		when(proposalVersionRepository.save(any(ProposalVersion.class))).thenReturn(proposalVersion);
		Proposal prop = proposalServiceImpl.copyProposal(12345L, true,1L);
		Assert.assertNotNull(prop);
	}

	/**
	 * Tests copyProposal() method.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testCopyProposal() throws IllegalAccessException, InvocationTargetException {
		OMSUser omsUser = new OMSUser();
		omsUser.setId(12345L);
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);
		when(userRepository.findByUserId(any(Long.class))).thenReturn(omsUser);
		Proposal prop = proposalServiceImpl.copyProposal(12345L, false,1L);
		Assert.assertNotNull(prop);
	}

	/**
	 * Tests negative scenario for addDocuments() method.
	 * 
	 * @throws IOException
	 */
	@Test(expected = OMSSystemException.class)
	public void testAddDocumentsNegative() throws IOException {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(null);
		Collection<ProposalDocument> propDocument = proposalServiceImpl.addDocuments(proposalDocuments);
	}

	/**
	 * Tests positive scenario for addDocuments() method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddDocumentsPositive() throws IOException {
		byte[] ba2 = { 1, 2, 3, 4, 5 };
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		propDoc.setPath("D:\\dummy.txt");
		propDoc.setContent(ba2);
		Document document = new Document();
		document.setPath("D:\\dummy.txt");
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalDocumentRepository.save(any(ProposalDocument.class))).thenReturn(propDoc);
		when(fileUploader.save(any(Document.class))).thenReturn(document);
		Collection<ProposalDocument> propDocument = proposalServiceImpl.addDocuments(proposalDocuments);
		Assert.assertNotNull(propDocument);
		file.delete();
	}

	/**
	 * Tests negative scenario for updateDocuments() method.
	 * 
	 * @throws IOException
	 */
	@Test(expected = OMSSystemException.class)
	public void testUpdateDocumentsNegative() throws IOException {
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(null);
		Collection<ProposalDocument> propDocument = proposalServiceImpl.updateDocuments(proposalDocuments);
	}

	/**
	 * Tests positive scenario for updateDocuments() method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUpdateDocumentsPositive() throws IOException {
		byte[] ba2 = { 1, 2, 3, 4, 5 };
		File file = new File("D:\\dummy.txt");
		FileOutputStream out = new FileOutputStream(file);
		out.write(ba2);
		out.close();
		propDoc.setPath("D:\\dummy.txt");
		propDoc.setContent(ba2);
		Document document = new Document();
		document.setPath("D:\\dummy.txt");
		when(proposalRepository.findByProposalId(any(Long.class))).thenReturn(proposal);
		when(proposalDocumentRepository.save(any(ProposalDocument.class))).thenReturn(propDoc);
		when(fileUploader.update(any(Document.class))).thenReturn(document);
		Collection<ProposalDocument> propDocument = proposalServiceImpl.updateDocuments(proposalDocuments);
		Assert.assertNotNull(propDocument);
		file.delete();
	}
}
