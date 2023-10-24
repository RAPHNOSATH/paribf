package raphno.bf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText useremail, userpassword ;

    private Button loginUser;

    private String email, password;
   // private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        useremail = findViewById(R.id.id_usersConnect);
        userpassword = findViewById(R.id.id_passwordConnect);
        ProgressBar progressBar = findViewById(R.id.id_progressbar1);

        loginUser = findViewById(R.id.btn_login);

        //databaseManager = new DatabaseManager(getApplicationContext());

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = useremail.getText().toString();
                password = userpassword.getText().toString();

            }
        });
    }
    public void register(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }
    /*public void onApiResponse(JSONObject response){
        Boolean success = null;
        String error = "";

        try {
            success = response.getBoolean("success");
            if(success == true){
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                mainActivity.putExtra("username",username);
                startActivity(mainActivity);
                finish();
            }else{
                error = response.getString("error");
                login_error.setVisibility(View.VISIBLE);
                login_error.setText(error);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    public void connectUser(){
        String url = "http://10.0.2.2/PariBF/actions/connectUser.php";
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password", password);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_interface, menu);
        return true;
    }*/
}