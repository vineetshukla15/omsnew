package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oms.model.Creative;
@Repository
public interface CreativeRepository extends SoftDeleteRepository<Creative,Long>, JpaSpecificationExecutor<Creative>,PagingAndSortingRepository<Creative,Long> {
	public List<Creative> findAllByOrderByCreativeIdDesc();

	public List<Creative> findByNameIgnoreCaseContainingOrderByName(String name);
	
	/*@Query("SELECT c from Creative c where c.deleted= :deleted order by creativeID desc")
	List<Creative> findAllCreatives(@Param("deleted") boolean deleted);
	
	@Query("SELECT c from Creative c where c.deleted= :deleted and c.creativeID=:creativeId")
	Creative findCreativeById(@Param("creativeId") Long creativeId, @Param("deleted") boolean deleted);*/
	
	@Query("SELECT c from Creative c where c.deleted= :deleted and c.name=:creativeName")
	List<Creative> findCreativeByName(@Param("creativeName") String creativeName, @Param("deleted") boolean deleted);
	
	@Query("SELECT c from Creative c where c.deleted= :deleted and lower(c.name)=:creativeName")
	Creative findCreativeByNameCaseInsensitive(@Param("creativeName") String creativeName, @Param("deleted") boolean deleted);

	public List<Creative> findByDeletedFalseOrderByNameAsc();
}
