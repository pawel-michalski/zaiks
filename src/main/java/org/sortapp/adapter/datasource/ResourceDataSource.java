package org.sortapp.adapter.datasource;

import org.sortapp.domain.exception.ResourceException;
import org.sortapp.domain.port.DataSource;

import java.util.ArrayList;
import java.util.List;

public class ResourceDataSource implements DataSource {
    private final List<ResourceStrategy> resourceStrategies;

    public ResourceDataSource() {
        JsonParser jsonParser = new JsonParser();
        resourceStrategies = new ArrayList<>();
        resourceStrategies.add(new LocalFileResourceStrategy(jsonParser));
        resourceStrategies.add(new HttpResourceStrategy(jsonParser));
    }

    @Override
    public int[] fetchData() throws ResourceException {
        for (ResourceStrategy strategy : resourceStrategies) {
            if (strategy.canFetch()) {
                return strategy.fetchData();
            }
        }
        throw new ResourceException("Failed to retrieve data from any available source");
    }
}
