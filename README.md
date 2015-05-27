# Gauge Spring integration

Supports using Spring to initialize Gauge step implementation classes. Helps migrating from Twist.

## How to use

Create Spring @Configuration class and use component scan to find other beans.

    @Configuration
    @ComponentScan(basePackageClasses = { SomeStepImplementation.class })
    public class SpringConfiguration {

        @Bean
        public void WebDriver driver() {
            // Create driver, likely based on active Spring profile or Gauge environment
        }
    }
    
Add package where configuration class can be found to env/default/java.properties.

    spring.component.scan.packages=configuration.spring


Add @Component to step implementation classes and autowire WebDriver and other required beans


    @Component
    public class AtBookingPage {

        private WebDriver browser;

        @Autowired
        public AtBookingPage(final WebDriver browser) {
            this.browser = browser;
        }
        
        // ... @Step implementations
    }

