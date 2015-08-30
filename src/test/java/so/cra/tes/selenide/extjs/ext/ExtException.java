package so.cra.tes.selenide.extjs.ext;

public class ExtException extends RuntimeException {
  public ExtException() {
  }

  public ExtException(Throwable cause) {
    super(cause);
  }

  public ExtException(String message) {
    super(message);
  }

  public ExtException(String message, Throwable cause) {
    super(message, cause);
  }
}
