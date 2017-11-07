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
import com.oms.model.Company_;
import com.oms.model.Contact;
import com.oms.model.Contact_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;

@Component
public class ContactSpecification {
	public static Specification<Contact> getContactSpecification(SearchRequestVO searchRequest) {
		return new Specification<Contact>() {

			@Override
			public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;
				try {
					final Predicate deletedPredicate = cb.equal(root.get(Contact_.deleted), false);
					predicates.add(deletedPredicate);
					
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("contactName")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Contact_.contactName)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("contactEmail")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Contact_.contactEmail)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("contactAddress")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Contact_.contactAddress)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("contactMobile")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Contact_.contactMobile)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("company.name")) {
							final Predicate localpredicate = cb.like(root.join(Contact_.company).get(Company_.name),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
					});
					
					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicate of Conatct Search",ex);
				}
				return predicateSearh;
			}
		};
	}
}