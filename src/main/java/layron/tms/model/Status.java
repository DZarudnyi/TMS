package layron.tms.model;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    INITIATED(1),
    IN_PROGRESS(2),
    COMPLETED(3);

    private static final Map<Integer, Status> map = new HashMap<>();

    private final int value;

    Status(int value) {
        this.value = value;
    }

    static {
        for (Status status : Status.values()) {
            map.put(status.value, status);
        }
    }

    public static Status valueOf(int status) {
        return map.get(status);
    }

    public int getValue() {
        return value;
    }
}
