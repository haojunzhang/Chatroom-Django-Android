package com.example.chatroom.utils;

import android.util.Base64;

import androidx.core.util.PatternsCompat;

import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class StringUtils {

    public static String decodeUnicode(String text) {
        return new String(text.getBytes());
    }

    public static String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String toJson(Object obj) {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(obj);
    }

    public static String toAmountFormat(String text) {
        return new BigDecimal(text).setScale(2, BigDecimal.ROUND_FLOOR).toString();
    }

    public static String toCommaAmount(String amount) {
        double dAmount = Double.valueOf(amount);
        return String.format(Locale.getDefault(), "%,.2f", dAmount);
    }

    public static boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }

        return PatternsCompat.EMAIL_ADDRESS
                .matcher(email)
                .matches();
    }

    public static boolean isValidOtp(String otp) {
        if (otp == null || otp.length() != 6) {
            return false;
        }
        return Pattern.compile("[0-9]{6}")
                .matcher(otp)
                .matches();
    }


    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return Pattern.compile("(?=.*[a-zA-Z])(?=.*[0-9]).{8,}")
                .matcher(password)
                .matches();
    }

    public static String getPhone(String nationCode, String phone) {
        String nationCodeNoPlus = nationCode.replace("+", "");

        String finalPhone;

        // taiwan phone number
        if ("886".equals(nationCodeNoPlus) && phone.startsWith("0")) {
            // remove prefix '0'
            finalPhone = nationCodeNoPlus + " " + phone.substring(1);
        } else {
            finalPhone = nationCodeNoPlus + " " + phone;
        }
        return finalPhone;
    }

    public static boolean isPhoneValid(String phone) {
        // 886 0987654321
        // 86 12345678901
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        String[] arr = phone.split(" ");

        if (arr.length != 2) {
            return false;
        }
        String nationCode = arr[0];
        String phoneNumber = arr[1];

        // 是否都由數字組合而成
        if (!Pattern.compile("\\d+")
                .matcher(phoneNumber)
                .matches()) {
            return false;
        }

        if ("886".equals(nationCode)) {
            // 針對86的phoneNumber檢查

            // 移除字首0
            if (phoneNumber.startsWith("0")) {
                phoneNumber = phoneNumber.substring(1);
            }

            // 不是9開頭
            if (!phoneNumber.startsWith("9")) {
                return false;
            }

            // 檢查是否為9碼
            return phoneNumber.length() == 9;
        } else if ("86".equals(nationCode)) {
            // 針對886的phoneNumber檢查

            // 檢查是否為11碼
            return phoneNumber.length() == 11;
        } else {
            return false;
        }
    }
}
