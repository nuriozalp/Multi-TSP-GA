package mtsp;


public enum UAVType {    
    miniUAV(1),
    tacticUAV(2),
    strategicUAV(3),
    operatifUAV(4);

    private final int value;

    private UAVType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}