package com.oms.repository;

import org.springframework.stereotype.Repository;

import com.oms.model.LineitemTarget;
@Repository
public interface LineItemTargetRepository extends SoftDeleteRepository<LineitemTarget, Long>{

}
