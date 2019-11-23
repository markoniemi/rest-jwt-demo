package org.restjwtdemo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UserServiceRestTemplateIT.class,UserServiceFeignIT.class,UserServiceWsIT.class })
public class SpringBootIntegrationTestSuite {
}
