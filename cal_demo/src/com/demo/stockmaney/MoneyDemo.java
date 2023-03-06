package com.demo.stockmaney;

import com.demo.MinPathDemo;

import java.util.ArrayList;
import java.util.List;
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
public class MoneyDemo {
    static class Node {
        int start; //起始
        int end;//截至
    }

    public static void main(String[] args) {
        System.out.println("在此输入本金，如：10000");
        // 获取输入字符串
        Scanner sc=new Scanner(System.in);
        double principle =sc.nextDouble(); // 10000
        System.out.println("在此输入天数，如：8");
        // 获取输入字符串
        Scanner scday=new Scanner(System.in);
        int days =scday.nextInt(); // 3
        System.out.println("在此输入连续价格，如：2,3,4,5,1,6,5,7");
        // 获取输入字符串
        Scanner scprice=new Scanner(System.in);
        String pricestr =scprice.next(); // 2,3,4,5,1,6,5,7
        if((days+days-1) != pricestr.length()){
            System.out.println("输入价格数量与天数不匹配！");
        } else {
            // 获取上涨周期内的开始价格与截至价格，形成数组
            List<Node> pricelist = getPriceArr(pricestr,days);
            //遍历数组计算价格
            double totol = calprice(pricelist,principle);

            System.out.println("输出：" + (totol - principle));
        }


    }

    public static double calprice( List<Node> pricelist,double principle){
        for(Node node : pricelist){
            int start = node.start;
            int end = node.end;
            principle = principle / start * end;
        }
        return principle;
    }

    public static List<Node> getPriceArr(String pricestr, int days){
        String[] pricearr =  pricestr.split(",");
        int lastprice = 0;
        List<Node> nodelist = new ArrayList<>();
        int start = -1;
        int end = -1;
        for(int i = 0 ; i < pricearr.length;i ++){
            String str = pricearr[i];
            int val = Integer.parseInt(str);
            //第一个值不用计算
            if(i != 0){
                //与上一期的值进行比较，
                if(val >= lastprice){
                    //上涨--开始买入
                    if(start == -1){
                        start = lastprice;
                    } else {
                        start = lastprice > start ? start : lastprice;
                    }
                    end = val;

                    //最后一个区间
                    if(i == pricearr.length - 1){
                        addNode((List<Node>) nodelist, start, end);
                    }
                } else{
                    //下跌 --开始值需要重新确定
                    if(start > -1){//防止数组一开始就是下跌，而且没有买入
                        addNode((List<Node>) nodelist, start, end);
                        //记录完金额后需要重新设置开始和结束的值
                        start = -1;
                        end = -1;
                    }
                }
            }
            lastprice = val;
        }
        return nodelist;
    }

    private static void addNode(List<Node> nodelist, int start, int end) {
        Node node = new Node();
        node.start = start;
        node.end = end;
        nodelist.add(node);
    }
}
