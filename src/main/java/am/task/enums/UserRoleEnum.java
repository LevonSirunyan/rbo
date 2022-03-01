package am.task.enums;

import java.util.Arrays;
import java.util.Optional;

public enum UserRoleEnum {
    ROLE_ADMIN(1),
    ROLE_USER(2);

    private final int mValue;

    UserRoleEnum(int mValue) {
        this.mValue = mValue;
    }

    public static Optional<UserRoleEnum> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.mValue == value)
                .findFirst();
    }

    public int getValue() {
        return ordinal() + 1;
    }
}
