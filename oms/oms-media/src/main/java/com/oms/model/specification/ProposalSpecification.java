package com.oms.model.specification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.oms.model.Opportunity_;
import com.oms.model.Proposal;
import com.oms.model.Proposal_;
import com.oms.model.SalesCategory_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.Filters;
import com.oms.viewobjects.SearchRequestVO;
@Component
public class ProposalSpecification {
	public static Specification<Proposal> getProposalSpecification(SearchRequestVO searchRequest) {
		return new Specification<Proposal>() {

			@Override
			public Predicate toPredicate(Root<Proposal> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();				
				Predicate predicateSearh = null;
				
				try {
					final Predicate deletedPredicate = cb.equal(root.get(Proposal_.deleted), false);
					predicates.add(deletedPredicate);
					List<Filters> filters = searchRequest.getFilters();
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					if (!filters.isEmpty()) {
						filters.forEach(item -> {
							String searchText = item.getValue();
							String colName = item.getColumn();
							if (!StringUtils.isEmpty(searchText) && colName.equals("name")) {
								final Predicate localpredicate = cb.like(cb.lower(root.get(Proposal_.name)),
										"%" + searchText + "%");
								predicates.add(localpredicate);
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("startDate")) {
								try {
									Date date= formatter.parse(searchText);
									final Predicate localpredicate = cb.equal(root.get(Proposal_.startDate), date);
									predicates.add(localpredicate);
								} catch (Exception e) {
									throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
											"Unable to format date ", e);
								}
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("endDate")) {
								try {
									Date date= formatter.parse(searchText);
									final Predicate localpredicate = cb.equal(root.get(Proposal_.endDate), date);
									predicates.add(localpredicate);
								} catch (Exception e) {
									throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
											"Unable to format date ", e);
								}
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("dueDate")) {
								try {
									Date date= formatter.parse(searchText);
									final Predicate localpredicate = cb.equal(root.get(Proposal_.dueDate), date);
									predicates.add(localpredicate);
								} catch (Exception e) {
									throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
											"Unable to format date ", e);
								}
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("salesCategory")) {
								final Predicate localpredicate = cb.equal(
										root.join(Proposal_.salesCategory).get(SalesCategory_.salesCatagoryId),searchText);
								predicates.add(localpredicate);
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("advertiser")) {
								final Predicate localpredicate = cb.equal(
										root.join(Proposal_.advertiser).get(Company_.companyId), searchText);
								predicates.add(localpredicate);
							}
							if (!StringUtils.isEmpty(searchText) && colName.equals("agency")) {
								final Predicate localpredicate = cb.equal(
										root.join(Proposal_.agency).get(Company_.name), searchText );
								predicates.add(localpredicate);
							}
						});
					} else {
					List<Columns> columns = searchRequest.getColumns();
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("name")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Proposal_.name)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("budget")) {
							final Predicate localpredicate = cb.equal(root.get(Proposal_.budget), searchText);
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("percentageOfClose")) {
							final Predicate localpredicate = cb.equal(root.get(Proposal_.percentageOfClose),  searchText);
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("submitted")) {
							boolean enabled = searchText.equals("true");
							final Predicate localpredicate = cb.equal(root.get(Proposal_.submitted),enabled );
							predicates.add(localpredicate);
						}
					});
					}
					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicte for Proposal Search ", ex);
				}
				return predicateSearh;
			}
		};
	}
}