package Utility;

/**
 * Created by Necho on 29/04/2016.
 */
public class ErrorConfirmation {
    boolean result;
    String message;

    public ErrorConfirmation(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
