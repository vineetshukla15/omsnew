package com.oms.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@Transactional
@NoRepositoryBean
public interface SoftDeleteRepository<Entity, ID extends Serializable> extends JpaRepository<Entity, Long> {

	@Override
	@Query("select e from #{#entityName} e where e.deleted=false")
	public List<Entity> findAll();
    
	
	
	@Query("select e from #{#entityName} e where e.deleted=false and e.id=?1")
	public Entity findById(long id);

	// Check entity in cycle bin.
	@Query("select e from #{#entityName} e where e.deleted=true")
	public List<Entity> recycleBin();

	// Some logic delete method, or override the original delete method
	@Query("update #{#entityName} e set e.deleted=true where e.id=?1")
	@Modifying
	public void softDelete(long id);

}
