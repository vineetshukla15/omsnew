/**
 * 
 */
package com.oms.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tavant.api.auth.model.HighLevelPermission;

import com.oms.model.SpecType;
import com.oms.repository.SpecTypeRepository;

/**
 * Tests the SpecType service.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SpecTypeServiceImplTest {
	
	@InjectMocks
	SpecTypeServiceImpl specTypeServiceImpl;
	
	@Mock
	private SpecTypeRepository specTypeRepository;
	
	private List<SpecType> specTypeList;
	
	private SpecType specType;
	
	/**
	 * Sets up the test data.
	 */
	@Before
    public void setUp() {		
		specTypeList = new ArrayList<SpecType>();
		specType = new SpecType();
		specType.setSpec_Id(12345L);
		specType.setDeleted(false);
		specType.setName("Type1");
		specTypeList.add(specType);
    }
	
	/**
	 * Tests positive scenario for listSpecType() method.
	 */
	@Test
	public void testListSpecTypePositive() {
		when(specTypeRepository.findByDeletedFalseOrderByNameAsc()).thenReturn(specTypeList);
		List<SpecType> list = specTypeServiceImpl.listSpecType();
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

}
