package se.intem.gauge.spring;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.ClassInstanceManager;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GaugeSpringContextFactory {

    private static final String KEY_SCAN_PACKAGES = "spring.component.scan.packages";

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(GaugeSpringContextFactory.class);

    private ConfigurableApplicationContext context;

    @BeforeSuite
    public void beforeSuite() {
        log.debug("Creating Spring application context...");

        String packages = Optional.fromNullable(System.getenv(KEY_SCAN_PACKAGES)).or("");
        List<String> split = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(packages);

        if (split.isEmpty()) {
            throw new IllegalStateException("Cannot create Spring context. Environment property " + KEY_SCAN_PACKAGES
                    + " was empty.");
        }

        this.context = new AnnotationConfigApplicationContext(split.toArray(new String[] {}));
        context.registerShutdownHook();

        ClassInstanceManager.setClassInitializer(new SpringClassInitializer(context));
    }

    @AfterSuite
    public void afterSuite() {

        if (context == null) {
            return;
        }

        log.info("Closing created drivers...");

        Map<String, WebDriver> drivers = context.getBeansOfType(WebDriver.class);
        for (WebDriver driver : drivers.values()) {
            driver.close();
        }

        /* Close context so that @PreDestroy methods will get called. */
        context.close();
    }
}
