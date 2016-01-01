package torchat.onion.torchat_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class loginActivity extends Activity {


    public String d;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.usernameField);
        inputPassword = (EditText) findViewById(R.id.passwordField);

        loginErrorMsg = (TextView) findViewById(R.id.login_error);



    }
    public void loginPost(View view){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        new SignInActivity(this, inputEmail, inputPassword, loginErrorMsg).execute(email,password);
    }
    public void register(View view){
        Intent intent = new Intent(this,registerActivity.class);
        startActivity(intent);
    }
    public void login(View view){
        Intent intent = new Intent(this,loginActivity.class);
        startActivity(intent);
    }
}
