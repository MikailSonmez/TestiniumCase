package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        plugin = {
                "pretty",                                      // Konsolda renkli çıktı
                "html:target/cucumber-html-report.html",       // HTML raporu
                "json:target/cucumber.json",                   // JSON raporu
                "junit:target/cucumber.xml"                    // JUnit XML raporu
        },
        monochrome = true
)
public class TrelloTestRunner {}
