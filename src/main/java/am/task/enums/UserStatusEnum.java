package am.task.enums;

import java.util.Arrays;
import java.util.Optional;

public enum UserStatusEnum {
    ACTIVE(1),
    BLOCK(2);

    private final int mValue;

    UserStatusEnum(int mValue) {
        this.mValue = mValue;
    }

    public static Optional<UserStatusEnum> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.mValue == value)
                .findFirst();
    }

    public int getValue() {
        return ordinal() + 1;
    }
}
