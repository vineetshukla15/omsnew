package com.oms;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * An abstract class to run the test throw JUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)  
public abstract class AbstractTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


}