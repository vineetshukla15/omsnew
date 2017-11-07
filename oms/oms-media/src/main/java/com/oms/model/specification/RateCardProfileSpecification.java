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
import org.springframework.util.StringUtils;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.RateCard;
import com.oms.model.RateCardProfile_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;

public class RateCardProfileSpecification {
	public static Specification<RateCard> getRateCardSpecification(SearchRequestVO searchRequest) {
		return new Specification<RateCard>() {

			@Override
			public Predicate toPredicate(Root<RateCard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;

				try {
					final Predicate deletedPredicate = cb.equal(root.get(RateCardProfile_.deleted), false);
					predicates.add(deletedPredicate);

					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("basePrice")) {
							final Predicate localpredicate = cb.equal(root.get(RateCardProfile_.basePrice), searchText);
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("sectionsName")) {
							final Predicate localpredicate = cb.like(root.get(RateCardProfile_.sectionsName),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("notes")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(RateCardProfile_.notes)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("isActive")) {
							boolean isActive = searchText.equals("true");
							final Predicate localpredicate = cb.equal(root.get(RateCardProfile_.isActive), isActive);
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("isRatecardrounded")) {
							boolean enabled = searchText.equals("true");
							final Predicate localpredicate = cb.equal(root.get(RateCardProfile_.isRatecardrounded),
									enabled);
							predicates.add(localpredicate);
						}
					});

					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicte for RateCard Search ", ex);
				}
				return predicateSearh;
			}

		};
	}
}
