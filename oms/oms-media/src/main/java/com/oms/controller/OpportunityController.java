package com.oms.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oms.aspect.annotation.Auditable;
import com.oms.aspect.annotation.AuditingActionType;
import com.oms.commons.utils.FileUtil;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.Opportunity;
import com.oms.model.OpportunityDocument;
import com.oms.model.OpportunityDocuments;
import com.oms.model.OpportunityVersion;
import com.oms.service.OpportunityService;
import com.oms.settings.errors.GenericResponse;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchRequestVO;

@RestController
@RequestMapping(value = "/opportunity", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpportunityController extends BaseController {
	final static Logger logger = Logger.getLogger(CreativeController.class);

	@Autowired
	private OpportunityService opportunityService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public @ResponseBody List<Opportunity> getOpportunities() {
		List<Opportunity> opportunities = opportunityService.getAllOpportunity();
		return opportunities;
	}

	@RequestMapping(value = "/{opportunityId}", method = RequestMethod.GET)
	public @ResponseBody Opportunity getOpportunityById(@PathVariable Long opportunityId) throws OMSSystemException {
		return opportunityService.getOpportunityById(opportunityId);

	}

	@RequestMapping(method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.ADD_NEW_OPPORTUNITY)
	public @ResponseBody Opportunity addNewOpportunity(@RequestBody Opportunity opportunity)throws OMSSystemException {
		return opportunityService.addOpportunity(opportunity,findUserIDFromSecurityContext());
	}

	@RequestMapping(method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_OPPORTUNITY)
	public @ResponseBody Opportunity updateOpportunity(@RequestBody Opportunity opportunity) {
		return opportunityService.updateOpportunity(opportunity,findUserIDFromSecurityContext());
	}

	@RequestMapping(value ="/{opportunityId}",  method = RequestMethod.DELETE)
	@Auditable(actionType = AuditingActionType.DELETE_OPPORTUNITY)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeOpportunity(@PathVariable("opportunityId") Long opportunityId) {
		opportunityService.deleteOpportunity(opportunityId);
	}

	@RequestMapping(value = "/copy/{opportunityId}/{IsVersion}", method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.COPY_OPPORTUNITY)
	public @ResponseBody Opportunity copyOpportunity(@PathVariable Long opportunityId, @PathVariable Boolean IsVersion)
			throws OMSSystemException {
		return opportunityService.copyOpportunity(opportunityId, IsVersion,findUserIDFromSecurityContext());
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public PaginationResponseVO<Opportunity> searchOpportunity(@RequestBody SearchRequestVO searchRequest ){
		return opportunityService.searchOpportunity(searchRequest);
	}
	
	@RequestMapping(value = "/documents", method = RequestMethod.POST)
	@Auditable(actionType = AuditingActionType.UPLOAD_OPPORTUNITY_DOCUMENT)
	public GenericResponse<OpportunityDocument> uploadDocuments(
			@RequestPart("doc_details") OpportunityDocuments documentDetails,
			@RequestPart("docs") MultipartFile[] documents) {
		documentDetails.setDocuments(documents);
		documentDetails.setUserId(findUserIDFromSecurityContext());
		return new GenericResponse<OpportunityDocument>(Status.SUCCESS, "Documents uploaded successfully",
				this.opportunityService.addDocuments(documentDetails));
	}

	@RequestMapping(value = "/documents", method = RequestMethod.PUT)
	@Auditable(actionType = AuditingActionType.UPDATE_OPPORTUNITY_DOCUMENT)
	public GenericResponse<OpportunityDocument> updateDocuments(
			@RequestPart("doc_details") OpportunityDocuments documentDetails,
			@RequestPart("docs") MultipartFile[] documents) {
		documentDetails.setDocuments(documents);
		documentDetails.setUserId(findUserIDFromSecurityContext());
		return new GenericResponse<OpportunityDocument>(Status.SUCCESS, "Documents updated successfully",
				this.opportunityService.updateDocuments(documentDetails));
	}

	@RequestMapping(value = "/documents/{opportunityId}", method = RequestMethod.GET)
    public Collection<OpportunityDocument> getDocuments(@PathVariable Long opportunityId) {
		return opportunityService.getDocuments(opportunityId);
    }

	@RequestMapping(value = "{opportunityId}/documents/{documentId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable Long opportunityId, @PathVariable Long documentId) throws IOException {
		OpportunityDocument document = this.opportunityService.getDocument(opportunityId, documentId);
		return ResponseEntity.ok().headers(headers(document)).contentLength(document.getContent().length)
				.contentType(MediaType.parseMediaType(FileUtil.mimeType(document.getPath())))
				.body(new InputStreamResource(FileUtil.contentAsStream(document.getPath())));
	}

	private HttpHeaders headers(OpportunityDocument document) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"",
                document.getName()));
		return headers;
	}
	
	@RequestMapping(method = RequestMethod.PUT,value = "/versionUpdate/{opportunityId}")
	@Auditable(actionType = AuditingActionType.UPDATE_OPPORTUNITY_VERSION)
	public @ResponseBody Opportunity updateOpportunityVersion(@RequestBody OpportunityVersion opportunityVersion, @PathVariable Long opportunityId) {
		opportunityVersion.setUpdatedBy(findUserIDFromSecurityContext());
		return opportunityService.updateOpportunityVersion(opportunityVersion, opportunityId,findUserIDFromSecurityContext());
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/versionUpdate/{opportunityId}")
	@Auditable(actionType = AuditingActionType.ADD_NEW_OPPORTUNITY_VERSION)
	public @ResponseBody Opportunity createOpportunityVersion(@RequestBody OpportunityVersion opportunityVersion, @PathVariable Long opportunityId) {
		opportunityVersion.setUpdatedBy(findUserIDFromSecurityContext());
		opportunityVersion.setVersion(1L);
		return opportunityService.updateOpportunityVersion(opportunityVersion, opportunityId,findUserIDFromSecurityContext());
	}
}
