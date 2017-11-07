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
import org.tavant.api.auth.model.OMSUser;
import org.tavant.api.auth.model.OMSUser_;
import org.tavant.api.auth.model.Role_;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;

@Component
public class UserSpecification {
	public static Specification<OMSUser> getUserSpecification(SearchRequestVO searchRequest) {
		return new Specification<OMSUser>() {

			@Override
			public Predicate toPredicate(Root<OMSUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				Predicate predicateSearh = null;
				List<Columns> columns = searchRequest.getColumns();
				try {
					final Predicate deletedPredicate = cb.equal(root.get(OMSUser_.deleted), false);
					predicates.add(deletedPredicate);

					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("firstName")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(OMSUser_.firstName)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("lastName")) {
							final Predicate localpredicate = cb.like(root.get(OMSUser_.lastName),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("username")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(OMSUser_.username)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("emailId")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(OMSUser_.emailId)),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("enabled") && !searchText.equals("false")) {
							Boolean enalbes = true;
							final Predicate localpredicate = cb.equal(root.get(OMSUser_.enabled), enalbes);
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("role.roleName")) {
							final Predicate localpredicate = cb.like(root.join(OMSUser_.role).get(Role_.roleName),
									"%" + searchText + "%");
							predicates.add(localpredicate);
						}

					});

					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));

				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Exception occured while creating predicate for User search", ex);
				}

				return predicateSearh;
			}

		};
	}
}