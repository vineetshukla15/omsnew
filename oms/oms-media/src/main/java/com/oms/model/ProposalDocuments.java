package com.oms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.api.email.exceptions.OMSSystemException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.exceptions.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposalDocuments {
	private Long proposalId;
	private Long userId;
	private List<ProposalDocument> documentDetails;

	public ProposalDocuments() {
		super();
	}

	public ProposalDocuments(List<ProposalDocument> documents, Proposal proposal, Long userId) {
		this.proposalId = proposal.getProposalId();
		this.userId = userId;
		this.documentDetails = documents;
	}
	
	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	public List<ProposalDocument> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<ProposalDocument> documentDetails) {
		this.documentDetails = documentDetails;
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
	
	private void setContent(ProposalDocument doc, MultipartFile attachment) {
		try {
			doc.setContent(IOUtils.toByteArray(attachment.getInputStream()));
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Error getting file content");
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
