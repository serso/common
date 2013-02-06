package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

public interface MessageLevel {

    public static final int INFO_LEVEL = 100;
    public static final int WARNING_LEVEL = 500;
    public static final int ERROR_LEVEL = 1000;

    /**
     *
     * @return int message level
     */
    int getMessageLevel();

    @NotNull
    String getName();
}
