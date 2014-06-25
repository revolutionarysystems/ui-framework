package uk.co.revsys.ui.framework;

import java.util.LinkedHashMap;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class OrderedPropertyMapFactoryBean extends AbstractFactoryBean<LinkedHashMap<String, String>> implements ResourceLoaderAware{

    private ResourceLoader resourceLoader;
    private String location;
    
    @Override
    public void setResourceLoader(ResourceLoader rl) {
        this.resourceLoader = rl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    protected LinkedHashMap<String, String> createInstance() throws Exception {
        LinkedHashMap<String, String> propertyMap = new LinkedHashMap<String, String>();
        Resource resource = resourceLoader.getResource(location);
        for(String property: IOUtils.readLines(resource.getInputStream())){
            String[] tokens = property.split("=");
            String propertyName = tokens[0].trim();
            String propertyValue = tokens[1].trim();
            propertyMap.put(propertyName, propertyValue);
        }
        return propertyMap;
    }

}
