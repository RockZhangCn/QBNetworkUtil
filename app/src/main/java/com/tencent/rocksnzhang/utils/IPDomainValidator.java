package com.tencent.rocksnzhang.utils;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IPDomainValidator
{
    public static boolean isValidDomainOrIPAddr(String input)
    {
        if ((input == null) || input.equals(""))
        {
            return false;
        }

        boolean domain = DomainValidator.getInstance().isValid(input);
        boolean ip = InetAddressValidator.getInstance().isValid(input);

        return domain || ip;
    }
}