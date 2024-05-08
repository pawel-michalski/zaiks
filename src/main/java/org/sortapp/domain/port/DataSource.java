package org.sortapp.domain.port;

import org.sortapp.domain.exception.ResourceException;

public interface DataSource {
  int[] fetchData() throws ResourceException;
}
