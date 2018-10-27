package se.intem.gauge.spring;

import org.junit.Before;
import org.junit.Test;

public class GaugeSpringContextFactoryTest {

    private GaugeSpringContextFactory factory;

    @Before
    public void setup() {
        this.factory = new GaugeSpringContextFactory();
    }

    @Test(expected = IllegalStateException.class)
    public void requireScanPackages() {
        factory.beforeSuite();
    }

}