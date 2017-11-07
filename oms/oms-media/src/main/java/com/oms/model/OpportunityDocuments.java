package com.oms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityDocuments {
	private Long userId;
	private Long opportunityId;
	private List<OpportunityDocument> documentDetails;
	
	public void setDocumentDetails(List<OpportunityDocument> documentDetails) {
		this.documentDetails = documentDetails;
	}

	public List<OpportunityDocument> getDocumentDetails() {
		return documentDetails;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public void setDocuments(MultipartFile[] attachments) {
			if (attachments != null) {
				final Map<String, MultipartFile> docMap = Arrays.stream(attachments)
						.filter(attachment -> !attachment.isEmpty())
						.collect(Collectors.toMap(MultipartFile::getOriginalFilename, attachment -> attachment));
				this.getDocumentDetails().forEach(doc -> {
					MultipartFile attachment = docMap.get(doc.getName());
					if (attachment != null) {
						setContent(doc, attachment);
					}
				});
				
			}
		}

		private void setContent(OpportunityDocument doc, MultipartFile attachment) {
			try {
				doc.setContent(IOUtils.toByteArray(attachment.getInputStream()));
			} catch (Exception e) {
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
						"Error getting file content");
			}
		}
	}