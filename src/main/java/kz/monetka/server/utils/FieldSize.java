package kz.monetka.server.utils;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public final class FieldSize {
    // Общие
    public static final int UUID         = 36;
    public static final int DEFAULT_TEXT = 255;
    public static final int LONG_TEXT    = 4000;

    // Точность для днежных полей
    public static final int AMOUNT_PRECISION = 18;
    public static final int AMOUNT_SCALE     = 2;
}
