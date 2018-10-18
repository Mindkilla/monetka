package kz.monetka.server.utils;

public final class Consts {
    // Название параметра хэдера запроса в которой храниться токен пользователя
    public static final String HEADER = "X-Auth-Token";

    // Общие
    public static final int UUID = 36;
    public static final int DEFAULT_TEXT = 255;
    public static final int LONG_TEXT = 4000;

    // Точность для днежных полей
    public static final int AMOUNT_PRECISION = 18;
    public static final int AMOUNT_SCALE = 2;

    private Consts() {
    }
}
