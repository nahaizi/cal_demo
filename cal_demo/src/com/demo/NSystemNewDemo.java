package com.demo;

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
public class NSystemNewDemo {

    public String transform(int num, int n) {
        //参数num为输入的十进制数，参数n为要转换的进制
        int array[] = new int[100];
        int count = 0;
        int location = 0;
        while (num != 0) {//当输入的数不为0时循环执行求余和赋值
            int remainder = num % n;
            num = num / n;
            array[location] = remainder;//将结果加入到数组中去
            location++;
            count++;
        }
       return show(array, location - 1, count);
    }

    private String show(int[] arr, int n, int count) {
        String result = "";
        for (int i = n; i >= 0; i--) {
            if (arr[i] < 0) {
                for (int j = 0; j < count - 1; j++) {
                    arr[j] = Math.abs(arr[j]);
                }
            }
            if (arr[i] > 9 || arr[i] < -9) {
                result += (char) (arr[i] + 55);
            } else {
                result += arr[i] + "";
            }
        }
        return result;
    }


    public static void main(String[] args) {
        NSystemNewDemo t = new NSystemNewDemo();
        int num = 100;
        System.out.println("10进制" + num + " 的36进制是:" + t.transform(num, 36));
        System.out.println("10进制" + num + " 的27进制是:" + t.transform(num, 27));
        String str = "1Z1";
        System.out.println("27进制" + str + " 的27进制转10进制是:" + t.hexToDecimal(str, 27));
        System.out.println("36进制" + str + " 的36进制转10进制是:" + t.hexToDecimal(str, 36));
    }

    /**
     * @param: [hex]
     * @return: int
     * @description: 按位计算，位值乘权重
     */
    public  int  hexToDecimal(String hex,int n){
        int outcome = 0;
        for(int i = 0; i < hex.length(); i++){
            char hexChar = hex.charAt(i);
            outcome = outcome * n + charToDecimal(hexChar);
        }
        return outcome;
    }

    /**
     * @param: [c]
     * @return: int
     * @description:将字符转化为数字
     */
    public  int charToDecimal(char c){
        if(c >= 'A' && c <= 'Z'){
            return 10 + c - 'A';
        }
        else{
            return c - '0';
        }
    }

}
