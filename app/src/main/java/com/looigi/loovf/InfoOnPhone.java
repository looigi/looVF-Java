package com.looigi.loovf;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

public class InfoOnPhone {
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public String getIMEI() {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) VariabiliGlobali.getInstance().getFragmentActivityPrincipale().getSystemService(serviceName);
        String IMEI = "";
        String IMSI = "";

        try {
            IMEI = m_telephonyManager.getDeviceId();
            IMSI = m_telephonyManager.getSubscriberId();
        } catch (SecurityException ignored) {

        }

        return IMEI + ";" + IMSI + ";";
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getUser(Context context) {
        // String id = "", phone = "";
        String accountName = null;
        // Cursor emailCur = null;
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts[0].name != null) {
            accountName = accounts[0].name;
            // String where = ContactsContract.CommonDataKinds.Email.DATA + " = ?";
            // ArrayList<String> what = new ArrayList<String>();
            // what.add(accountName);
            // for (int i = 1; i < accounts.length; i++) {
            //     where += " or " + ContactsContract.CommonDataKinds.Email.DATA + " = ?";
            //     what.add(accounts[i].name);
            // }
            // String[] whatarr = (String[])what.toArray(new String[what.size()]);
            // ContentResolver cr = context.getContentResolver();
            // emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, where, whatarr, null);
            // if (id != null) {
            //     Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
            //     while (pCur.moveToNext())
            //     {
            //         phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //     }
            //     pCur.close();
            // }
        }

        return accountName;
    }
}
