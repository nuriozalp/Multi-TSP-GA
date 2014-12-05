package mtsp;


public enum TaskType {    
    little(1),
    medium(2),
    large(3);

    private final int value;

    private TaskType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
