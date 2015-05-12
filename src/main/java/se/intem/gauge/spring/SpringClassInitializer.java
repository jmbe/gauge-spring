package se.intem.gauge.spring;

import com.thoughtworks.gauge.ClassInitializer;

import org.springframework.context.ApplicationContext;

public class SpringClassInitializer implements ClassInitializer {

    private ApplicationContext context;

    public SpringClassInitializer(final ApplicationContext context) {
        this.context = context;
    }

    public Object initialize(final Class<?> classToInitialize) throws Exception {
        return context.getBean(classToInitialize);
    }

}
