package mrmini.hold1e17.dk.mrmini.Logic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mrmini.hold1e17.dk.mrmini.Hovedmenu;
import mrmini.hold1e17.dk.mrmini.Login;
import mrmini.hold1e17.dk.mrmini.Scanner;

/**
 * Created by Simon on 15-01-2018.
 */

public class PreferenceLogic {

    public void saveNurse(Hovedmenu hovedmenu) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(hovedmenu);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("pref_key_nurse_main", "true");
        editor.apply();

    }

    public void saveNurse(Scanner scan) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(scan);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("pref_key_nurse_scan", "true");
        editor.apply();

    }

    public void saveLogin(String hospital, String userName, boolean remember, Login login) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(login);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("pref_key_hospital", hospital);
        if (remember) {
            editor.putString("pref_key_save", "true");
        }

        editor.putString("login", userName);
        editor.apply();

    }

}
