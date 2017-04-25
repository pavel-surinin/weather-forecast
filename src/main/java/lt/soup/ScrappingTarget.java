package lt.soup;

import lt.soup.web.resources.Lithuania;
import lt.soup.web.resources.WebResource;

/**
 * Created by Pavel on 2017-04-25.
 */
public class ScrappingTarget {

    private WebResource resource;
    private String[] cities;

    public ScrappingTarget(WebResource resource, String... cities) {
        this.resource = resource;
        this.cities = cities;
    }
}
