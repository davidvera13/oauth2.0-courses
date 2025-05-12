package eu.dreamlabs.spi.domains;

public class VerifyPasswordResponse {
    private boolean result;

    public VerifyPasswordResponse() {
    }

    public VerifyPasswordResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
