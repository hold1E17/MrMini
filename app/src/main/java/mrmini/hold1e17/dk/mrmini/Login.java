package mrmini.hold1e17.dk.mrmini;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Simon on 30-10-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button noLogBut, logBut;
    EditText userName;
    Spinner hospitalChoice;
    CheckBox rememberMe;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logBut = (Button) findViewById(R.id.logBut);
        noLogBut = (Button) findViewById(R.id.noLogBut);
        userName = (EditText) findViewById(R.id.userName);
        hospitalChoice = (Spinner) findViewById(R.id.hospitalChoice);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        logBut.setText(R.string.logBut);
        noLogBut.setText(R.string.noLogBut);
        userName.setHint(R.string.loginName);
        rememberMe.setText(R.string.rememberMe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hospitalList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalChoice.setAdapter(adapter);

        logBut.setOnClickListener(this);
        noLogBut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == logBut) {
            Intent i = new Intent(this, Hovedmenu.class);
            if (rememberMe.isChecked()) {
                saveLogin();
                i.putExtra("login", userName.getText().toString());
                i.putExtra("hospital", hospitalChoice.getSelectedItem().toString());
            }
            startActivity(i);
        } else if (v == noLogBut) {
            Intent i = new Intent(this, Hovedmenu.class);
            startActivity(i);
        }

    }

    public void saveLogin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("login", userName.getText().toString());
        editor.putString("hospital", hospitalChoice.getSelectedItem().toString());
        editor.apply();

    }

}
