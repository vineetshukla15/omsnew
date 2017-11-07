package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.oms.model.Contact;

@Repository
public interface ContactRepository extends SoftDeleteRepository<Contact, Long>, JpaSpecificationExecutor<Contact>, PagingAndSortingRepository<Contact, Long> {

	List<Contact> findByDeletedFalseOrderByContactNameAsc();

}
