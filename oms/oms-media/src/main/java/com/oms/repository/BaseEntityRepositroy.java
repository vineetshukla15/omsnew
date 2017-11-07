package com.oms.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.oms.model.BaseEntity;


@NoRepositoryBean
public interface BaseEntityRepositroy<T extends BaseEntity, E extends Serializable> extends
        CrudRepository<T, E> {
}
