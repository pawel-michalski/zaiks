package org.sortapp.domain.exception;

public class ResourceException extends Exception {
  public ResourceException(String message) {
    super(message);
  }

  public ResourceException(String message, Throwable cause) {
    super(message, cause);
  }
}
