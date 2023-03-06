package com.demo.stringrule;


import java.util.Scanner;

/**
 * @author lijunyu
 * @Description: 4、功能号/接口名匹配算法
 * @return $
 * @throws
 * @date $ $
 * ===========================================
 * 修改人：lijunyu，    修改时间：$ $，    修改版本：
 * 修改备注：
 * ===========================================
 */
public class InterfaceMatcherDemo {

    static class MatcherHelper {
        //输入字符串
        public String inputstr;
        //字符串规则
        public String rulestr;
        //上一个规则，用于后续判断
        public String lastrule;
        //已经用过的规则的长度，用于后续计算上一字符是否为*
        public int lastrulelength;


        public MatcherHelper(String inputstr, String rulestr) {
            this.inputstr = inputstr;
            this.rulestr = rulestr;
        }
    }

    //用例：1、*?ab?c?*,
    //2、**？？abc???cd**ef*?gh?*ij*
    // 3、？*？abc???cd**ef*?gh?*ij？
    public static void main(String[] args) {

        System.out.println("在此输入功能号/接口名，如：aa");
        // 获取输入字符串
        Scanner strsc = new Scanner(System.in);
        String str = strsc.next(); // aa

        System.out.println("在此输入匹配规则串，如：a");
        // 获取输入字符串
        Scanner rulesc = new Scanner(System.in);
        String rulestr = rulesc.next(); // aa
        MatcherHelper matcherhelper = new MatcherHelper(str, rulestr);
        boolean flag = matchStr(matcherhelper);
        if (flag) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

    }

    private static String ASTERISK_SPLIT = "\\*";
    private static String ASTERISK = "*";
    private static String QUESTION = "?";


    public static boolean matchStr(MatcherHelper matcherhelper) {
        int asterindex = matcherhelper.rulestr.indexOf(ASTERISK);
        int questindex = matcherhelper.rulestr.indexOf(QUESTION);

        if (asterindex > -1 || questindex > -1) {
            return check(matcherhelper);
        } else {
            if (matcherhelper.inputstr.equals(matcherhelper.rulestr)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static boolean check(MatcherHelper matcherhelper) {
        boolean flag = true;
        //先把规则按*拆分成数组
        String[] rulearr = matcherhelper.rulestr.split(ASTERISK_SPLIT);
        for (int i = 0; i < rulearr.length; i++) {
            String rule = rulearr[i];
            //判断字符串内是否含有？
            boolean isneedcheckquest = false;
            if (rule.indexOf(QUESTION) > -1) {
                isneedcheckquest = true;
            } else {
                isneedcheckquest = false;
            }

            if (isneedcheckquest) {
                //  *?ab?c?*
                flag = checkquestion(matcherhelper, rule, i);
            } else {
                flag = checkAster(matcherhelper, rule, i);
            }
            if (flag) {
                //参与星号的判断
                matcherhelper.lastrule = rule;
                int add = rule.length() == 0 ? 1 : rule.length();
                matcherhelper.lastrulelength = matcherhelper.lastrulelength + add ;
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static boolean checkAster(MatcherHelper matcherhelper, String rule, int i) {
        int prelength = rule.length() == 0 ? 1 : rule.length();
        String laststr = matcherhelper.rulestr.substring(matcherhelper.rulestr.length() - prelength, matcherhelper.rulestr.length());
        //判断规则最后一位是否为*，为*则走indexof，否则强校验equals
        if (laststr.equals(rule)) {
            return matchAsterLastStr(matcherhelper, rule);
        } else {
            //规则不为空的话，代表字符不是*需要进行强校验
            int length = rule.length();
            // 规则长度大于字符串长度
            if (length > matcherhelper.inputstr.length()) {
                return false;
            }
            // 判断上一个规则是否为空
            // 上一个规则是空代表*号开头可以使用indexof，否则使用equals强校验
            boolean flag = lastruleisAster(matcherhelper, rule, i);
            if (flag) {
                int index = matcherhelper.inputstr.indexOf(rule);
                if (index > -1) {
                    //更新str给下一个规则进行匹配
                    int end = (index + rule.length()) > matcherhelper.inputstr.length() ? matcherhelper.inputstr.length() : (index + rule.length());//防止越界
                    matcherhelper.inputstr = matcherhelper.inputstr.substring(end, matcherhelper.inputstr.length());
                    return true;
                } else {
                    return false;
                }
            } else {
                String str = matcherhelper.inputstr.substring(0, rule.length());
                if (str.equals(rule)) {
                    //重新赋值
                    matcherhelper.inputstr = matcherhelper.inputstr.substring(rule.length(), matcherhelper.inputstr.length());
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private static boolean lastruleisAster(MatcherHelper matcherhelper, String rule, int i) {
        //当i=0时是第一个规则，如果规则为空，则表示*开头的数据
        if ((rule == null || (rule != null && "".equals(rule))) && i == 0) {
            return true;
        } else if (i != 0) {
            char lastchar = matcherhelper.rulestr.charAt(i + matcherhelper.lastrulelength - 2);
            if (lastchar == ASTERISK.charAt(0)) {
                //获取上一位，判断是否为*
                return true;
            }
        }
        return false;
    }


    private static boolean checkquestion(MatcherHelper matcherhelper, String rule, int i) {
        int start = 0;
        char questionchar = QUESTION.toCharArray()[0];
        // 上一个规则是空代表*号开头可以用循环位移输入字符串处理，否则需要强行匹配
        boolean flag = lastruleisAster(matcherhelper, rule, i);
        if (flag) {
            return calPerchar(matcherhelper, rule, start, questionchar, true);
        } else {
            return calPerchar(matcherhelper, rule, start, questionchar, false);
        }
    }

    private static boolean calPerchar(MatcherHelper matcherhelper, String rule, int start, char questionchar, boolean isneedcycle) {
        int helperlength = matcherhelper.inputstr.length();
        //长度不匹配则失败,所有字段匹配完毕没有发现合理的值即为失败
        if (helperlength < (rule.length() + start)) {
            return false;
        }
        String newstr = matcherhelper.inputstr.substring(start, start + rule.length());
        char[] newstrchar = newstr.toCharArray();
        char[] rulechar = rule.toCharArray();
        for (int k = 0; k < rulechar.length; k++) {
            char strchar = newstrchar[k];
            char newrulechar = rulechar[k];
            if (newrulechar == questionchar || newrulechar == strchar) {
                continue;
            } else {
                // 是否需要递归位移计算，如果上一规则没有*,则不递归强制校验
                if (isneedcycle) {
                    return calPerchar(matcherhelper, rule, start + 1, questionchar, isneedcycle);
                } else {
                    return false;
                }
            }
        }
        //匹配成功需要更新输入参数
        matcherhelper.inputstr = matcherhelper.inputstr.substring(start + rule.length(), matcherhelper.inputstr.length());
        return true;
    }

    private static boolean matchAsterLastStr(MatcherHelper matcherhelper, String rule) {
        // 如果不包含*了，直接取规则与输入字符串的最后几位字母进行匹配
        if (matcherhelper.rulestr.lastIndexOf(ASTERISK) == (matcherhelper.rulestr.length() - 1)) {
            //最后一个字符是*，只需要校验是否含有即可
            int index = matcherhelper.inputstr.indexOf(rule);
            if (index > -1) {
                //最后一个直接返回即可
                return true;
            } else {
                return false;
            }
        } else {
            //最后一个字符不是*
            if (matcherhelper.inputstr.length() < rule.length()) {
                return false;
            }
            matcherhelper.inputstr = matcherhelper.inputstr.substring(matcherhelper.inputstr.length() - rule.length(), matcherhelper.inputstr.length());
            if (matcherhelper.inputstr.equals(rule)) {
                //最后一个直接返回即可
                return true;
            } else {
                return false;
            }
        }
    }

}
