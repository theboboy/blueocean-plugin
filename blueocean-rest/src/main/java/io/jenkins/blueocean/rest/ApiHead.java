package io.jenkins.blueocean.rest;

import hudson.Extension;
import hudson.ExtensionList;
import io.jenkins.blueocean.RootRoutable;
import io.jenkins.blueocean.rest.pageable.Pageable;
import io.jenkins.blueocean.rest.pageable.Pageables;
import io.jenkins.blueocean.rest.pageable.PagedResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.WebMethod;
import org.kohsuke.stapler.verb.GET;

import java.util.HashMap;
import java.util.Map;

/**
 * Entrypoint for blueocean REST apis. $CONTEXT_PATH/rest being root. e.g. /jenkins/rest
 *
 *
 * @author Vivek Pandey
 */
@Extension
public final class ApiHead implements RootRoutable  {

    private final Map<String,ApiRoutable> apis = new HashMap<>();

    public ApiHead() {
        for ( ApiRoutable api : ExtensionList.lookup(ApiRoutable.class)) {
            apis.put(api.getUrlName(),api);
        }
    }

    /**
     * Search API
     */
    @WebMethod(name="search") @GET @PagedResponse
    public Pageable<?> search(@QueryParameter("q") Query query) {
        for (OmniSearch os : OmniSearch.all()) {
            if (os.getType().equals(query.type))
                return os.search(query);
        }

//        throw new ServiceException.BadRequestExpception("Unknown query type: "+query.type);
        return Pageables.empty();
    }

    /**
     * This {@link ApiHead} gets bound to "/rest"
     */
    @Override
    public String getUrlName() {
        return "rest";
    }

    /**
     * Exposes all {@link ApiRoutable}s to URL space.
     */
    public ApiRoutable getDynamic(String route) {
        return apis.get(route);
    }

}
