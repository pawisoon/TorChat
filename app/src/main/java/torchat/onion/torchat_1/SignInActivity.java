package torchat.onion.torchat_1;

/**
 * Created by pawisoon on 25/12/14.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInActivity extends AsyncTask<String,Void,String>{

    private EditText inputEmail, inputPassword;
    private TextView loginErrorMsg;

    private Activity context;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    //added  tezd


    public String d;
    public SignInActivity(Activity context, EditText inputEmail, EditText inputPassword, TextView loginErrorMsg) {
        this.context = context;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
        this.loginErrorMsg = loginErrorMsg;

    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {
        String email = (String)arg0[0];
        String password = (String)arg0[1];
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.loginUser(email,password);

        try{
            String token = json.getString("success");
            String res = json.getString(KEY_SUCCESS);
            if (token != null) {
                if(Integer.parseInt(res) == 1){
                    // user successfully logged in
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(this.context);
                    JSONObject json_user = json.getJSONObject("user");

                    // Clear all previous data in database
                    userFunction.logoutUser(this.context);
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(this.context, MainPanel.class);

                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(dashboard);

                    // Close Login Screen
                    context.finish();
                }else{
                    // Error in login
                    d = "Incorrect username/password";
                }
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return d;

    }

    protected void onPostExecute(String result) {
        loginErrorMsg.setText(result);

    }
}