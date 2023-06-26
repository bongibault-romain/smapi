package lt.bongibau.smapi.api.exception;

public class InitiatedException extends Throwable {

    private final IdentifiedException initiator;

    public InitiatedException(IdentifiedException initiator) {
        this.initiator = initiator;
    }

    public IdentifiedException getInitiator() {
        return initiator;
    }

    public String getIdentifier() {
        return this.getInitiator().getIdentifier();
    }
}
