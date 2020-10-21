package imburseTest;

import org.junit.runner.RunWith; 
// import cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

//When we specify json:target/Destination/cucumber.json - It will generate the JSON  
// report inside the Destination folder, in the target folder of the maven project.
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", plugin = { "json:target/cucumber.json", "pretty","html:target/cucumber-reports" })
public class RunTest {
    
}
