package cn.xmp.generator.demo.user.utils;

import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/1/24
 */
public class StringUtil extends StringUtils {
    private static char[] ch = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static char[] numCh = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(Object str) {
        return !StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str.trim());
    }

    public static boolean isNumeric(CharSequence cs) {
        if(isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String getNonceStr(Integer length) {
        return random(ch, length.intValue());
    }

    public static String random(char[] charArr, int strLen) {
        if(strLen <= 0) {
            if(strLen == 0) {
                return "";
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            Random random = new Random();
            StringBuilder result = new StringBuilder();

            for(int i = 0; i < strLen; ++i) {
                int num = random.nextInt(charArr.length);
                result.append(charArr[num]);
            }

            return result.toString();
        }
    }

    public static String getNumberNonceStr(Integer length) {
        StringBuilder result = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < length.intValue(); ++i) {
            int num = r.nextInt(numCh.length);
            result.append(numCh[num]);
        }

        return result.toString();
    }

    public static boolean isEmail(String email) {
        if(isEmpty(email)) {
            return false;
        } else {
            String emReg = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
            return email.matches(emReg);
        }
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] = (char)(cs[0] + 32);
        return String.valueOf(cs);
    }

    public static boolean isMobilePhone(String phoneNo) {
        if(isEmpty(phoneNo)) {
            return false;
        } else {
            String phReg = "^[1][345789]\\d{9}$";
            return phoneNo.matches(phReg);
        }
    }
}
