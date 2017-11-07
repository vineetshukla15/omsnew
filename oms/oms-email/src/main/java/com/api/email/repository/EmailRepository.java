package com.api.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.email.model.EmailEntity;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {



}
