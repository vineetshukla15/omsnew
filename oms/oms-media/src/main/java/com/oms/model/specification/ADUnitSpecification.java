package com.oms.model.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.ADUnit;
import com.oms.model.ADUnit_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;
@Component
public class ADUnitSpecification {
	public static Specification<ADUnit> getADUnitSpecification(SearchRequestVO searchRequest) {
		return new Specification<ADUnit>() {

			@Override
			public Predicate toPredicate(Root<ADUnit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;
				try {
					final Predicate deletedPredicate = cb.equal(root.get(ADUnit_.deleted), false);
					predicates.add(deletedPredicate);
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("name")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(ADUnit_.name)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("displayName")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(ADUnit_.displayName)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("isActive")) {
							boolean enabled = searchText.equals("true");
							final Predicate localpredicate = cb.equal(root.get(ADUnit_.isActive),enabled );
							predicates.add(localpredicate);
						}
					});
					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Exception occured while creating predicate for AdUnit search",ex);
				}
				return predicateSearh;
			}
		};
	}
}