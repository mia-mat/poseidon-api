package ws.mia.poseidon.api;

public class PoseidonClientException extends RuntimeException {
  public PoseidonClientException() {
  }

  public PoseidonClientException(String message) {
    super(message);
  }

  public PoseidonClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
