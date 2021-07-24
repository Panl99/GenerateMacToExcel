package main;

/**
 * @author lp
 * @date 2021/7/19 11:56
 **/
public class ConvertUtil {
    /**
     * 十六进制转ascii字符
     * @param s 00 - 7F
     * @return
     */
    public static String hex2ascii(String s) {
        int n = Integer.parseInt(s, 16);
        char c = (char) Integer.parseInt(String.valueOf(n));
        return String.valueOf(c);
    }

    /**
     * ascii字符转十六进制
     * @param s 取字符串第一个字符
     * @return
     */
    public static String ascii2hex(String s) {
        String result = "";
        for (char c : s.toCharArray()) {
            result += Integer.toHexString(c);
        }
        return result;
    }

    /**
     * ascii字符转十进制
     * @param s 取字符串第一个字符
     * @return
     */
    public static String ascii2decimal(String s) {
        String result = "";
        for (char c : s.toCharArray()) {
            result += String.valueOf((int) c);
        }
        return result;
    }

    /**
     * 十进制转ascii字符
     *
     * @param n 0 - 127
     * @return
     */
    public static String decimal2ascii(int n) {
        return (String.valueOf((char) n));
    }

    public static void main(String[] args) {
        System.out.println("hex2ascii(\"61\") = " + hex2ascii("61")); // a
        System.out.println("ascii2hex(\"abcABC1590\") = " + ascii2hex("abcABC1590")); // 61626341424331353930

        System.out.println("ascii2decimal(\"abcABC1590\") = " + ascii2decimal("abcABC1590")); // 97989965666749535748
        System.out.println("char2ascii(128) = " + decimal2ascii(126)); // ~
    }
}
