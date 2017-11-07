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
import com.oms.model.Creative;
import com.oms.model.Creative_;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;

public class CreativeSpecification {
	public static Specification<Creative> getCreativesSpecification(SearchRequestVO searchRequest) {
		return new Specification<Creative>() {

			@Override
			public Predicate toPredicate(Root<Creative> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;

				try {
					final Predicate deletedPredicate = cb.equal(root.get(Creative_.deleted), false);
					predicates.add(deletedPredicate);
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("name")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Creative_.name)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("height2")) {
							final Predicate localpredicate = cb.like(root.get(Creative_.description), "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("width1")) {
							final Predicate localpredicate = cb.ge(root.get(Creative_.width1), Double.valueOf(searchText));
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("width2")) {
							final Predicate localpredicate = cb.ge(root.get(Creative_.width2), Double.valueOf(searchText));
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("height1")) {
							final Predicate localpredicate = cb.ge(root.get(Creative_.height1), Double.valueOf(searchText));
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("height2")) {
							final Predicate localpredicate = cb.ge(root.get(Creative_.height2), Double.valueOf(searchText));
							predicates.add(localpredicate);
						}
					});
					
					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicate for Creative Search !",ex);
				}
				return predicateSearh;
			}
		};
	}
}
