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
import com.oms.model.Company;
import com.oms.model.CompanyStatus_;
import com.oms.model.Company_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;
@Component
public class CompanySpecification {
	public static Specification<Company> getCompanySpecification(SearchRequestVO searchRequest) {
		return new Specification<Company>() {

			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;
				try {
					final Predicate deletedPredicate = cb.equal(root.get(Company_.deleted), false);
					predicates.add(deletedPredicate);
					
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("name")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Company_.name)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("type")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Company_.type)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("address")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Company_.address)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("companyStatus.name")) {
							final Predicate localpredicate = cb.like(root.join(Company_.companyStatus).get(CompanyStatus_.name),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
					});

					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicate for Company Search !",ex);
				}
				return predicateSearh;
			}

		};
	}
}