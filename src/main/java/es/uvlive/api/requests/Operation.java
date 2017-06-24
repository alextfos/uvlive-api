package es.uvlive.api.requests;

public class Operation {

    private String type;
    private String operation;

    public Operation(String type, String operation) {
        this.type = type;
        this.operation = operation;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
