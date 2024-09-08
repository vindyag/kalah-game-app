package org.game.kalahcore;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.game.kalahcore"
)
@SpringBootTest(classes = KalahCoreApplication.class)
public class CucumberIntegrationTest {
}