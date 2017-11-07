package com.oms.model.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tavant.api.auth.model.Permissions;
import org.tavant.api.auth.model.Permissions_;
import org.tavant.api.auth.model.Role;
import org.tavant.api.auth.model.Role_;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.SearchRequestVO;
@Component
public class RoleSpecification {
	public static Specification<Role> getRoleSpecification(SearchRequestVO searchRequest) {
		return new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Collection<Predicate> predicates = new ArrayList<>();
				List<Columns> columns = searchRequest.getColumns();
				Predicate predicateSearh = null;
				
				try {
					final Predicate deletedPredicate = cb.equal(root.get(Role_.deleted), false);
					predicates.add(deletedPredicate);
					
					columns.forEach(item -> {
						String searchText = item.getSearch().getValue();
						String columnName = item.getData();
						if (!StringUtils.isEmpty(searchText) && columnName.equals("roleName")) {
							final Predicate localpredicate = cb.like(cb.lower(root.get(Role_.roleName)),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
						if (!StringUtils.isEmpty(searchText) && columnName.equals("active")) {
							boolean enabled = searchText.equals("true");
							final Predicate localpredicate = cb.equal(root.get(Role_.active),enabled );
							predicates.add(localpredicate);
						}if (!StringUtils.isEmpty(searchText) && columnName.equals("permissions.PermissionName")) {
							final CollectionJoin<Role, Permissions> roleJoin = root.join(Role_.permissions, JoinType.LEFT);
							final Predicate localpredicate = cb.like(roleJoin.get(Permissions_.permissionName),  "%"+searchText+ "%");
							predicates.add(localpredicate);
						}
					});

					predicateSearh = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} catch (Exception ex) {
					throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
							"Unable to create predicte for Role Search ", ex);
				}
				return predicateSearh;
			}

		};
	}
}