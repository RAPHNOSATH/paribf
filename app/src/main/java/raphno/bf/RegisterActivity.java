package raphno.bf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout utilisateur,user_email,user_telephone,user_password,user_confirmPassword;
    private TextView error_register;
    private Button createUser;
    private DatabaseManager databaseManager;

    private String username, email,password,confirm_password, telephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        utilisateur = findViewById(R.id.id_users);
        user_email = findViewById(R.id.id_email);
        user_telephone = findViewById(R.id.id_tel);
        user_password = findViewById(R.id.id_password);
        user_confirmPassword = findViewById(R.id.id_confirmPassword);
        error_register = findViewById(R.id.id_error_register);
        createUser = findViewById(R.id.id_save);
        databaseManager = new DatabaseManager(getApplicationContext());

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = utilisateur.getEditText().getText().toString();
                email = user_email.getEditText().getText().toString();
                telephone = user_telephone.getEditText().getText().toString();
                password = user_password.getEditText().getText().toString();
                confirm_password = user_confirmPassword.getEditText().getText().toString();

                createAccount();
            }
        });

    }
    public void login(View view){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_interface, menu);
        return true;
    }

    public void onApiResponse(JSONObject response){
        Boolean success = null;
        String error = "";

        try {
            success = response.getBoolean("success");
            if(success == true){
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }else{
                error = response.getString("error");
                error_register.setVisibility(View.VISIBLE);
                error_register.setText(error);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void createAccount(){
        String url = "http://10.0.2.2/PariBF/actions/createAccount.php";
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("telephone",telephone);
        params.put("email",email);
        params.put("password", password);
        params.put("confirm_password", confirm_password);
        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                onApiResponse(response);
                Toast.makeText(getApplicationContext(),"OPERATION SUCCESSFUL",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        databaseManager.queue.add(jsonObjectRequest);
    }
}