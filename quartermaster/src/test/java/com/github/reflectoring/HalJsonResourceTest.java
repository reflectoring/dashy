package com.github.reflectoring;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HalJsonResourceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getPropertyReturnsStoredPropertyValue() {
        HalJsonResource resource = new HalJsonResource();
        String key = "name";
        String value = "First";

        resource.addProperty(key, value);
        Object object = resource.getProperty(key);

        assertThat(object, is(value));
    }

    @Test
    public void addPropertyThrowsExceptionOnExistingKey() {
        HalJsonResource resource = new HalJsonResource();
        String key = "name";
        String value = "First";

        resource.addProperty(key, value);

        exception.expect(IllegalArgumentException.class);

        resource.addProperty(key, value);
    }

}
