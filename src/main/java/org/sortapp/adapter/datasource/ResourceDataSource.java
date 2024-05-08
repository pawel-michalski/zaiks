package org.sortapp.adapter.datasource;

import java.util.ArrayList;
import java.util.List;
import org.sortapp.domain.exception.ResourceException;
import org.sortapp.domain.port.DataSource;

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
