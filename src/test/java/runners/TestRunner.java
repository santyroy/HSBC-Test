package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = "steps", tags = "@test", monochrome = true, plugin = {"pretty", "html:target/cucumber"})
public class TestRunner {
}
