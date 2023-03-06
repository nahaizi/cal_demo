package com.demo.ip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @param $
 * @author lijunyu
 * @Description: TODO
 * @return $
 * @throws
 * @date $ $
 * ===========================================
 * 修改人：lijunyu，    修改时间：$ $，    修改版本：
 * 修改备注：
 * ===========================================
 */
public class IPMatcherDemo {

    public static void main(String[] args) {

        System.out.println("在此输入IP地址，如：172.16.254.1,2001:0db8:85a3:0:0:8A2E:0370:7334");
        // 获取输入字符串
        Scanner strsc=new Scanner(System.in);
        String str =strsc.next(); // aa
        boolean flag = isValidIPV4ByCustomRegex(str);
        if(!flag){
            flag = isValidInet6Address(str);
            if(flag){
                System.out.println("IPV6");
            } else {
                System.out.println("ERROR");
            }
        } else {
            System.out.println("IPV4");
        }
    }

    private static final String IPV4_REGEX =
            "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    public static boolean isValidIPV4ByCustomRegex(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }
        if (!IPv4_PATTERN.matcher(ip).matches()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        try {
            for (String segment : parts) {
                if (Integer.parseInt(segment) > 255 ||
                        (segment.length() > 1 && segment.startsWith("0"))) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static boolean isValidInet6Address(String inet6Address) {
            if ((!inet6Address.startsWith(":") ) && (!inet6Address.endsWith(":") )) {
            String[] octets = inet6Address.split(":");
            if (octets.length > 8) {
                return false;
            } else {
                int validOctets = 0;
                int emptyOctets = 0;

                for(int index = 0; index < octets.length; ++index) {
                    String octet = octets[index];
                    if (octet.length() == 0) {
                        ++emptyOctets;
                        if (emptyOctets > 1) {
                            return false;
                        }
                    } else {
                        emptyOctets = 0;

                        if (octet.length() > 4) {
                            return false;
                        }
                        int octetInt;
                        try {
                            octetInt = Integer.parseInt(octet, 16);
                        } catch (NumberFormatException var10) {
                            return false;
                        }
                        if (octetInt < 0 || octetInt > 65535) {
                            return false;
                        }
                    }
                    ++validOctets;
                }
                if (validOctets <= 8) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
