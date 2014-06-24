package uk.co.revsys.ui.framework;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import uk.co.revsys.resource.repository.model.Resource;
import uk.co.revsys.resource.repository.provider.handler.ResourceHandler;
import uk.co.revsys.resource.repository.provider.servlet.ServletContextAware;

public class WebappResourceLoader implements ResourceHandler, ServletContextAware {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void handle(String path, Resource resource, InputStream contents) throws IOException {
        String relativePath = resource.getPath().replace(path, "");
        File directory = new File(servletContext.getRealPath(relativePath));
        File file = new File(directory, resource.getName());
        FileUtils.copyInputStreamToFile(contents, file);
    }

}
