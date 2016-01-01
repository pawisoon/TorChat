package torchat.onion.torchat_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class registerActivity extends Activity {
    public Boolean hasError = Boolean.FALSE;

    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);

        registerErrorMsg = (TextView) findViewById(R.id.register_error);

    }

    public void login(View view){
        Intent intent = new Intent(this,loginActivity.class);
        startActivity(intent);
    }


    public void registerPost(View view){

        String username = inputFullName.getText().toString();
        String password = inputPassword.getText().toString();
        String email = inputEmail.getText().toString();

        if (username.length() < 3) {
            hasError=Boolean.TRUE;

            inputFullName.setBackgroundColor(Color.MAGENTA);
        }
        else if (password.length() < 3) {
            hasError= Boolean.TRUE;

            inputPassword.setBackgroundColor(Color.MAGENTA);
        }
        if(!hasError){
            new registerAccountActivity(this,inputFullName,inputEmail,inputPassword,registerErrorMsg).execute(username, email, password);
        }


    }
}
