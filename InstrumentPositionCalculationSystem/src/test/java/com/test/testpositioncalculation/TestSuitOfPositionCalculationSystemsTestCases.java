package com.test.testpositioncalculation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		TestController.class, 			//test case 1
		TestPositionCalculation.class,     //test case 2
		TestService.class				//test case 3
})
public class TestSuitOfPositionCalculationSystemsTestCases {

}
