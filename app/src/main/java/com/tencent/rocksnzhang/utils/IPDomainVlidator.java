package com.tencent.rocksnzhang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPDomainVlidator
{
    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static boolean isValidDomainOrIPAddr(String input)
    {
        if((input == null) || input.equals(""))
            return false;
        return domainCheckLegal(input) || isIPv4Address(input);
    }
    /**
     * 检查域名是否合法
     *
     * @param domain
     * @return 返回"LEGAL"成功
     * 其余就返回不合法消息
     */
    public static boolean domainCheckLegal(String domain)
    {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        String str = null;

        //可以包含中文、字母a-z（大小写等价）、数字0-9或者半角的连接符"-"，"-"不能放在开头或结尾
        String reg = "^(?!-)(?!.*?-$)[-a-zA-Z0-9\\u4e00-\\u9fa5]*$";

        p = Pattern.compile(reg);
        m = p.matcher(domain);
        b = m.matches();

        if (!b)
        {
            return false;
        }

        str = m.group();


        reg = "^[\\u4e00-\\u9fa5]+$";//纯汉字必须大于1位
        p = Pattern.compile(reg);
        m = p.matcher(domain);
        b = m.matches();
        if (b)
        {
            String chinese = m.group();
            if (chinese.length() < 2 || chinese.length() > 20)
            {
                return false;
            }
        }
        else
        {
            //判断punycode长度
            if (str.length() < 3)
            {
                return false;
            }

            //如果第一位、二位不是中文，就判断第三、四位是否是“-”
            String str1 = str.substring(0, 3);
            ;
            String reg1 = "^[-a-zA-Z0-9]*$";
            p = Pattern.compile(reg1);
            m = p.matcher(str1);
            b = m.matches();

            if (b)
            {
                if (str.indexOf("-") == 2 || str.indexOf("-") == 3)
                {
                    return false;
                }
            }


            //判断输入的域名是否超长
            int valueLength = 0;
            String chinese = "[\u4e00-\u9fa5]";
            for (int i = 0; i < str.length(); i++)
            {
                String temp = str.substring(i, i + 1);
                if (temp.matches(chinese))
                {
                    valueLength += 2;
                }
                else
                {
                    valueLength++;
                }
            }
            if (valueLength > 63)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * 是否是IPV4格式
     *
     * @param address
     * @return
     */
    public static boolean isIPv4Address(final String input)
    {
        if ((input == null) || input.equals(""))
        {
            return false;
        }
        return IPV4_PATTERN.matcher(input).matches();
    }

}