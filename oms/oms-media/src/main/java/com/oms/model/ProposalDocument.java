package com.oms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.tavant.api.auth.model.OMSUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oms.viewobjects.Document;

@Entity
@Table(name = "PROPOSAL_DOCUMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProposalDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2589649419145243830L;

	@Id
	@GeneratedValue
	@Column(name = "PROPOSAL_DOC_ID")
	private Long proposalDocId;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Column(name = "NAME")
	private String name;

	@NotNull(message = "Mandatory")
	@NotBlank(message = "Mandatory")
	@Column(name = "TYPE")
	private String type;

	@JsonIgnore
	@Column(name = "PATH")
	private String path;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", updatable = false, referencedColumnName = "USER_ID")
	private OMSUser createdBy;


	@Column(name = "created", updatable = false)
	private Date created;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "USER_ID")
	private OMSUser updatedBy;
	
	private Date updated;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "PROPOSAL_ID")
	private Proposal proposal;

	@JsonIgnore
	@Transient
	private byte[] content;

	public ProposalDocument() {
		super();
	}

	public ProposalDocument(OpportunityDocument opportunityDocument) {
		super();
		this.name = opportunityDocument.getName();
		this.type = opportunityDocument.getType();
		this.content = opportunityDocument.getContent();
	}

	public Long getProposalDocId() {
		return proposalDocId;
	}

	public void setProposalDocId(Long proposalDocId) {
		this.proposalDocId = proposalDocId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	public OMSUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(OMSUser createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(OMSUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public OMSUser getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@PrePersist
	public void beforeCreate() {
		Date date = new Date();
		this.created = date;
		this.updated = date;
	}

	@PreUpdate
	public void beforeUpdate() {
		this.updated = new Date();
	}

	public Document toDocument() {
		Document document = new Document();
		document.setName(this.name);
		document.setContent(this.content);
		document.setPath(this.path);
		if (this.proposal != null) {
			document.setLinkedWith(this.proposal.getProposalId().toString());
		}
		return document;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((proposalDocId == null) ? 0 : proposalDocId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProposalDocument other = (ProposalDocument) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (proposalDocId == null) {
			if (other.proposalDocId != null)
				return false;
		} else if (!proposalDocId.equals(other.proposalDocId))
			return false;
		return true;
	}

}
