package io.jenkins.blueocean.service.embedded.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.commons.stapler.JsonBody;
import io.jenkins.blueocean.rest.model.BOOrganization;
import io.jenkins.blueocean.rest.model.BOPipelineContainer;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.WebMethod;
import org.kohsuke.stapler.verb.DELETE;
import org.kohsuke.stapler.verb.PUT;

import java.io.IOException;

/**
 * {@link BOOrganization} implementation for the embedded use.
 *
 * @author Vivek Pandey
 * @author Kohsuke Kawaguchi
 */
public class OrganizationImpl extends BOOrganization {
    /**
     * In embedded mode, there's only one organization
     */
    public static final OrganizationImpl INSTANCE = new OrganizationImpl();

    private OrganizationImpl() {
    }

    /**
     * In embedded mode, there's only one organization
     */
    @JsonProperty
    public String getName() {
        return Jenkins.getInstance().getDisplayName().toLowerCase();
    }

    @Override
    public BOPipelineContainer getPipelines() {
        return new PipelineContainerImpl();
    }

    @WebMethod(name="") @DELETE
    public void delete() {
        throw new ServiceException.NotImplementedException("Not implemented yet");
    }

    @WebMethod(name="") @PUT
    public void update(@JsonBody OrganizationImpl given) throws IOException {
        given.validate();
        throw new ServiceException.NotImplementedException("Not implemented yet");
//        getXmlFile().write(given);
    }

    private void validate() {
//        if (name.length()<2)
//            throw new IllegalArgumentException("Invalid name: "+name);
    }
}
