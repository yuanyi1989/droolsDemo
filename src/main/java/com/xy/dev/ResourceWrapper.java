package com.xy.dev;
import org.kie.api.io.Resource;
/**
 * Created by 袁意 on 2017/1/6.
 */
public class ResourceWrapper {
    private Resource resource;

    private String   targetResourceName;

    public ResourceWrapper(Resource resource, String targetResourceName) {
        this.resource = resource;
        this.targetResourceName = targetResourceName;
    }

    public Resource getResource() {
        return resource;
    }

    public String getTargetResourceName() {
        return targetResourceName;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setTargetResourceName(String targetResourceName) {
        this.targetResourceName = targetResourceName;
    }
}
