package com.demo.zip;

import java.util.Scanner;

/**
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
public class ReduceDemo {
    public static void main(String[] args) {

        System.out.println("在此输入字符串，如：ABBC");
        // 获取输入字符串
        Scanner scprice = new Scanner(System.in);
        // "ABCBC"
        String str = scprice.next();
        compressString(str);

    }

    public static void compressString(String str) {
        int index = 0, rightLength = str.length();
        //index下标左边的字符串
        StringBuilder leftSb = new StringBuilder();
        //index下标右边的字符串
        StringBuilder rightSb = new StringBuilder(str);
        //当下标小于字符串总长度时，继续循环
        while (index < rightLength) {
            //比较两边字符串大小方便考虑截取长度
            int leftLength = leftSb.length();
            rightLength = rightSb.length();
            int sublength = 1;
            //往左偏
            if (leftLength < rightLength) {
                sublength = leftLength == 0 ? 1 : leftLength;
            } else {//往右偏
                sublength = rightLength == 0 ? 0 : rightLength;
            }
            //获取下标左边的字符串与下标右边的字符串进行比较
            while (sublength > 0) {
                //右边短于左边时,需要保留最后一位字母
                if (leftLength > rightLength) {
                    sublength = sublength - 1;
                }
                String rightstr = rightSb.substring(index, sublength);
                int leftindex = leftSb.lastIndexOf(rightstr);
                //当 sublength已经为1时，需要输出结果，否则认为还要继续
                if (sublength == 1 || leftindex > -1) {
                    //输出结果
                    int startcut = output(rightSb, leftLength, rightstr, leftindex);
                    // 右边字符串转到左边
                    leftSb.append(rightSb.substring(0, startcut));
                    rightstr = rightSb.substring(startcut, rightSb.length());
                    rightSb = new StringBuilder(rightstr);

                    break;
                }
                //右边边向下标位置坍缩
                sublength--;
            }
        }
    }

    private static int output(StringBuilder rightSb, int leftLength, String rightstr, int leftindex) {
        //左边偏移量，leftindex为-1时代表没有匹配到，此时为0
        int leftoffset = leftindex < 0 ? 0 : leftLength - leftindex;
        //匹配的字符串长度，leftindex为-1时代表没有匹配到，此时为0
        int matchstrlength = leftindex < 0 ? 0 : rightstr.length();

        //对匹配到的字符串进行截取
        int startcut = (matchstrlength + 1) > rightSb.length() ? rightSb.length() : (matchstrlength + 1);
        //下一个字符串
        String nextchar = rightSb.substring(matchstrlength, startcut);

        System.out.println("(" + leftoffset + "," + matchstrlength + "," + nextchar + ")");
        return startcut;
    }
}
