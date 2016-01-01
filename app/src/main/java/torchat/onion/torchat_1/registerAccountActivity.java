package torchat.onion.torchat_1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pawisoon on 01/01/15.
 */
public class registerAccountActivity extends AsyncTask<String,Void,String>{
    private Activity context;
    private TextView registerErrorMsg;
    private EditText inputFullName, inputEmail, inputPassword;
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    public registerAccountActivity(Activity context, EditText inputFullName, EditText inputEmail, EditText inputPassword, TextView registerErrorMsg){
        this.context = context;
        this.inputFullName = inputFullName;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
        this.registerErrorMsg = registerErrorMsg;
    }

    public void onPreExecute(){

    }


    @Override
    protected String  doInBackground(String... arg0){
        String d = "Accout registeration succesfull";

        String name = (String) arg0[0];
        String email = (String) arg0[1];
        String password = (String) arg0[2];
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.registerUser(name, email, password);


        // check for login response
        try {
            String KEY_SUCCESS = "success";

            String storeList = json.getString("success");
            String er = json.getString("error");
            if (storeList!= null) {
                //registerErrorMsg.setText("");



                if (Integer.parseInt(storeList) == 1) {
                    // user successfully registred
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(this.context);
                    JSONObject json_user = json.getJSONObject("user");

                    // Clear all previous data in database
                    userFunction.logoutUser(this.context);
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(this.context, loginActivity.class);
                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(dashboard);
                    // Close Registration Screen
                    context.finish();
                }
                else if(Integer.parseInt(er) == 2) {

                    String messError = json.getString("error_msg");
                    d = messError;
                    // Error in registration
                }
            }
            else {
                d = "Error occured in registration";
            }
        } catch (JSONException e) {

        }
        return d;
    }

    protected void onPostExecute(String result){
        registerErrorMsg.setText(result);
    }


}
