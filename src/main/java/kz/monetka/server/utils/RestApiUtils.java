package kz.monetka.server.utils;

import kz.monetka.server.services.UserService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;


/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public class RestApiUtils {
    private static final Logger LOGGER = Logger.getLogger(RestApiUtils.class);

    @Autowired
    private static UserService userService;

    public static String encodeId(String id, String userId) {
        // идентификатор пользователя
        //String userId = getUserId(token);
        // шифрованный идентификатор
        byte[] encodedId = xor(id.getBytes(StandardCharsets.UTF_8), userId.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(encodedId);
    }

    public static String decodeId(String encoded, String userId) {
        // идентификатор пользователя
        byte[] decodeBase64 = Base64.decodeBase64(encoded);
        //String userId = getUserId(token);
        byte[] value = xor(decodeBase64, userId.getBytes(StandardCharsets.UTF_8));
        return new String(value);
    }

    private static String getUserId(String token) {
        return userService.findByToken(token).getUser().getId();
    }

    private static byte[] xor(byte[] value, byte[] key) {
        int valueLength = value.length;
        byte[] res = new byte[valueLength];

        int keyLength = key.length;
        for (int i = 0; i < valueLength; i++) {
            res[i] = (byte) (value[i] ^ key[i % keyLength]);
        }
        return res;
    }
}
