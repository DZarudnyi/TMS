package layron.tms.model;

import java.util.HashMap;
import java.util.Map;

public enum Priority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private static final Map<Integer, Priority> map = new HashMap<>();

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    static {
        for (Priority priority : Priority.values()) {
            map.put(priority.value, priority);
        }
    }

    public static Priority valueOf(int priority) {
        return map.get(priority);
    }

    public int getValue() {
        return value;
    }
}
