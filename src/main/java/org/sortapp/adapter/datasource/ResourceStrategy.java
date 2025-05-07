package org.sortapp.adapter.datasource;

import org.sortapp.domain.exception.ResourceException;

interface ResourceStrategy {
    int[] fetchData() throws ResourceException;
    boolean canFetch();
}
