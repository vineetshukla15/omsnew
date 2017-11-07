package com.oms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tavant.api.auth.model.Role;

import com.oms.exceptions.OMSSystemException;
import com.oms.exceptions.Status;
import com.oms.model.specification.RoleSpecification;
import com.oms.repository.RoleRepository;
import com.oms.service.RoleService;
import com.oms.viewobjects.Columns;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.SearchOrderVO;
import com.oms.viewobjects.SearchRequestVO;

/**
 * Manage the data from database from Role table user
 */
@Service
public class RoleServiceImpl implements RoleService {

	final static Logger LOGGER = Logger.getLogger(RoleServiceImpl.class);
	/**
	 * The Spring Data repository for Account entities.
	 */
	@Autowired
	private RoleRepository roleRepository;

	@Transactional
	@Override
	public Role addRole(Role role) throws OMSSystemException {
		try {
			Role existingRole = roleRepository.findRoleByName(role.getRoleName().toLowerCase(), false);
			if(null != existingRole){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Role with name " + role.getRoleName() + " already exist");
			}
			return roleRepository.save(role);
		} catch (Exception e) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Transactional
	@Override
	public Role updateRole(Role roleDTO) throws OMSSystemException {
		try {
			Role existingRole = roleRepository.findRoleByName(roleDTO.getRoleName().toLowerCase(), false);
			if(null != existingRole && roleDTO.getRoleName().equals(existingRole.getRoleName()) && !roleDTO.getRoleId().equals(existingRole.getRoleId())){
				throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
						"Role with name " + roleDTO.getRoleName() + " already exist");
			}
			if (roleDTO.getRoleId() != null && Boolean.FALSE.equals(roleDTO.isDeleted())) {
				roleDTO.setCreated(existingRole.getCreated());
				roleDTO.setCreatedBy(existingRole.getCreatedBy());
				return roleRepository.save(roleDTO);
			}
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Role with id {" + roleDTO.getRoleId() + "} does not exist.");

		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT, ex.getMessage());
		}
	}

	@Transactional
	@Override
	public List<Role> listRoles() {
		List<Role> roles = null;
		try {
			roles = roleRepository.findByDeletedFalseOrderByRoleNameAsc();
		} catch (Exception ex) {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrive Roles information",ex);
		}
		return roles;
	}

	@Transactional
	@Override
	public Role getRoleById(Long roleID) throws OMSSystemException {
		if (roleRepository.findById(roleID) != null) {
			return roleRepository.findById(roleID);
		}
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
				"Role with id {" + roleID + "} does not exist.");
	}

	@Transactional
	@Override
	public void removeRole(Long roleId) throws OMSSystemException {
		Role role = roleRepository.findById(roleId);
		if (role != null && Boolean.FALSE.equals(role.isDeleted())) {
			roleRepository.softDelete(roleId);
		} else {
			throw new OMSSystemException(Status.FAILED.name(), HttpStatus.CONFLICT,
					"Role with Id {" + role.getRoleId() + "} does not exist or already deleted.");
		}

	}

	@Override
	public PaginationResponseVO<Role> searchRole(SearchRequestVO searchRequest) {
		Page<Role> pageResponse = null;
		PaginationResponseVO<Role> response = null;
		try {
			// users =
			// userRepository.findAll(UserSpecification.userNameIs(searchRequest.getColumns().get(0).getSearch().getValue()));
			pageResponse = roleRepository.findAll(RoleSpecification.getRoleSpecification(searchRequest),
					getPagination(searchRequest));
			response = new PaginationResponseVO<Role>(pageResponse.getTotalElements(), searchRequest.getDraw(),
					pageResponse.getTotalElements(), pageResponse.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("");
		return response;
	}

	private static Pageable getPagination(SearchRequestVO searchRequest) {
		List<Columns> columns = searchRequest.getColumns();
		List<SearchOrderVO> orders = searchRequest.getOrder();
		List<Order> orderList = new ArrayList<>();

		orders.forEach(item -> {
			int columnIndex = item.getColumn();
			String orderedByColumn = columns.get(columnIndex).getData();
			Direction dir = "asc".equals(item.getDir()) ? Direction.ASC : Direction.DESC;
			orderList.add(new Order(dir, orderedByColumn));
		});
		if (orderList.size() == 0) {
			orderList.add(new Order(Direction.DESC, "updated"));
		}
		int pageNumber = searchRequest.getStart() / searchRequest.getLength();
		PageRequest request = new PageRequest(pageNumber, searchRequest.getLength(), new Sort(orderList));
		return request;
	}
}
