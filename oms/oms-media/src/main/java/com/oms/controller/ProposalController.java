package com.oms.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.commons.utils.FileUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Proposal;
import com.oms.model.ProposalDocument;
import com.oms.model.ProposalDocuments;
import com.oms.model.ProposalStatus;
import com.oms.model.ProposalVersion;
import com.oms.service.ProposalService;
import com.oms.settings.errors.GenericResponse;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/proposal", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProposalController extends BaseController {

	@Autowired
	private ProposalService proposalService;

	@RequestMapping(method = RequestMethod.GET)
	public  List<Proposal> getAllProposal() {
		return proposalService.getAllProposals();
	}

	@RequestMapping(value = "/{proposalId}", method = RequestMethod.GET)
	public  Proposal getProposal(@PathVariable Long proposalId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return proposalService.getProposalById(proposalId);
	}

	@RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_NEW_PROPOSAL)
	public  Proposal addNewProposal(@RequestBody Proposal proposal)
			throws IllegalAccessException, InvocationTargetException, Exception {
		return proposalService.addProposal(proposal,findUserIDFromSecurityContext());
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_PROPOSAL)
	public  Proposal updateProposal(@RequestBody Proposal proposalDTO) throws IllegalAccessException, InvocationTargetException{
		proposalDTO.setUpdatedBy(findUserIDFromSecurityContext());
		proposalService.addProposalAudit(proposalDTO,null);
		return proposalService.updateProposal(proposalDTO,findUserIDFromSecurityContext());
	}

	@RequestMapping(value = "/{proposalId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Auditable(actionType = AuditingActionType.DELETE_PROPOSAL)
	public  void deleteProposal(@PathVariable("proposalId") Long proposalId) throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		proposalService.deleteProposal(proposalId);
	}

	@RequestMapping(value = "/copy/{proposalId}/{IsVersion}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.COPY_PROPOSAL)
	public  Proposal copyProposal(@PathVariable Long proposalId, @PathVariable Boolean IsVersion)
			throws IllegalAccessException, InvocationTargetException, OMSSystemException {
		return proposalService.copyProposal(proposalId, IsVersion, findUserIDFromSecurityContext());
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Proposal> searchProposal(@RequestBody SearchRequestVO searchRequest) {
		return proposalService.searchProposal(searchRequest);
	}

	@RequestMapping(value = "/documents", method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.UPLOAD_PROPOSAL_DOCUMENT)
	public GenericResponse<ProposalDocument> uploadDocuments(
			@RequestPart("doc_details") ProposalDocuments documentDetails,
			@RequestPart("docs") MultipartFile[] documents) {
		documentDetails.setDocuments(documents);
		documentDetails.setUserId(findUserIDFromSecurityContext());
		return new GenericResponse<ProposalDocument>(Status.SUCCESS, "Documents uploaded successfully",
				proposalService.addDocuments(documentDetails));
	}

	@RequestMapping(value = "/status/list", method = RequestMethod.GET)
	public List<ProposalStatus> getAllProposalStatus() {
		return proposalService.getAllProposalStatus();

	}

	@RequestMapping(value = "/documents/{proposalId}", method = RequestMethod.GET)
	public Collection<ProposalDocument> getProposalDocuments(@PathVariable Long proposalId) {
		return proposalService.getDocuments(proposalId);
	}

	@RequestMapping(value = "/documents", method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_PROPOSAL_DOCUMENT)
	public GenericResponse<ProposalDocument> updateDocuments(
			@RequestPart("doc_details") ProposalDocuments documentDetails,
			@RequestPart("docs") MultipartFile[] documents) {
		documentDetails.setDocuments(documents);
		documentDetails.setUserId(findUserIDFromSecurityContext());
		return new GenericResponse<ProposalDocument>(Status.SUCCESS, "Documents updated successfully",
				this.proposalService.updateDocuments(documentDetails));
	}

	@RequestMapping(value = "/{proposalId}/documents/{documentId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable Long proposalId,
			@PathVariable Long documentId) throws IOException {
		ProposalDocument document = this.proposalService.getDocument(proposalId, documentId);
		return ResponseEntity.ok().headers(headers(document)).contentLength(document.getContent().length)
				.contentType(MediaType.parseMediaType(FileUtil.mimeType(document.getPath())))
				.body(new InputStreamResource(FileUtil.contentAsStream(document.getPath())));
	}

	private HttpHeaders headers(ProposalDocument document) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", document.getName()));
		return headers;
	}
	
	@RequestMapping(value = "updateVersion/{proposalId}",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.UPDATE_PROPOSAL_VERSION)
	public  Proposal updateProposalVersion(@RequestBody ProposalVersion proposalVersion,@PathVariable Long proposalId)
			throws IllegalAccessException, InvocationTargetException{
		Long user = findUserIDFromSecurityContext();
		return proposalService.updateProposalVersion(proposalVersion, proposalId, user);
	}
	
	@RequestMapping(value = "updateVersion/{proposalId}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Auditable(actionType = AuditingActionType.ADD_NEW_PROPOSAL_VERSION)
	public  Proposal createProposalVersion(@RequestBody ProposalVersion proposalVersion,@PathVariable Long proposalId)
			throws IllegalAccessException, InvocationTargetException{
		Long user = findUserIDFromSecurityContext();
		return proposalService.updateProposalVersion(proposalVersion, proposalId, user);
	}
}
