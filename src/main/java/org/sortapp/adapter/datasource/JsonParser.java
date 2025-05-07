package org.sortapp.adapter.datasource;

import org.sortapp.domain.exception.ResourceException;

class JsonParser {
    public int[] parse(String jsonContent) throws ResourceException {
        try {
            String elementsStr = jsonContent.replaceAll("\\s", "")
                    .replace("{\"elements\":[", "")
                    .replace("]}", "");

            String[] elements = elementsStr.split(",");
            int[] result = new int[elements.length];

            for (int i = 0; i < elements.length; i++) {
                result[i] = Integer.parseInt(elements[i].trim());
            }
            return result;
        } catch (Exception e) {
            throw new ResourceException("JSON parsing error", e);
        }
    }
}
