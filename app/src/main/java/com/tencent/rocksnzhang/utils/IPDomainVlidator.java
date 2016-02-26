package com.tencent.rocksnzhang.utils;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPDomainVlidator
{
    public static boolean isValidDomainOrIPAddr(String input)
    {
        if((input == null) || input.equals(""))
            return false;

        boolean domain = DomainValidator.getInstance().isValid(input);
        boolean ip = InetAddressValidator.getInstance().isValid(input);

        return domain || ip;
    }
}