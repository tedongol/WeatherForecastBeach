package parcelpoint.runner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class) 
@CucumberOptions(
		plugin ={"pretty","json:target/cucumber-reports/Cucumber.json",
				 "junit:target/cucumber-reports/Cucumber.xml",
				 "html:target/cucumber-reports"},
		monochrome = true, 
		dryRun = false, features = "src/test/java/parcelpoint/features/", glue = {
				"parcelpoint.seleniumgluecode" }, tags = { "	\n" + 
						"	\n" + 
						"	\n" + 
						"	\n" +
						"    \n" + 
						"	\n" + 
						"	\n" + 
						"	\n" + 
						"\n" + 
						"\n" + 
						"\n" +
		"@Weather"})



public class TestRunner extends AbstractTestNGCucumberTests {
}