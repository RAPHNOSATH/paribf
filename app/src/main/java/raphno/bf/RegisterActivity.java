package raphno.bf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout utilisateur,user_email,user_telephone,user_password,user_confirmPassword;
    private Button createUser;
    private ProgressBar progressBar;
    //private DatabaseManager databaseManager;

    private static  final  String TAG ="RegisterActivity";

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
        createUser = findViewById(R.id.id_save);
        progressBar = findViewById(R.id.id_progressbar);
        //databaseManager = new DatabaseManager(getApplicationContext());

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = utilisateur.getEditText().getText().toString();
                email = user_email.getEditText().getText().toString();
                telephone = user_telephone.getEditText().getText().toString();
                password = user_password.getEditText().getText().toString();
                confirm_password = user_confirmPassword.getEditText().getText().toString();

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre nom d'utilisateur",Toast.LENGTH_LONG).show();
                    utilisateur.setError("Le champ Nom utilisateur est requis");
                    utilisateur.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre Addresse email",Toast.LENGTH_LONG).show();
                    user_email.setError("Le champ Email est requis");
                    user_email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Remettez votre addresse email",Toast.LENGTH_LONG).show();
                    user_email.setError("L'addresse email doit être valide");
                    user_email.requestFocus();
                }else if(TextUtils.isEmpty(telephone)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre numéro de téléphone",Toast.LENGTH_LONG).show();
                    user_telephone.setError("Le champ telephone est requis");
                    user_telephone.requestFocus();
                }else if(telephone.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Remettez votre numéro de téléphone",Toast.LENGTH_LONG).show();
                    user_telephone.setError("Votre num doit avoir au moins 8 chiffres");
                    user_telephone.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Entrer votre mot de passe",Toast.LENGTH_LONG).show();
                    user_password.setError("Le champ mot de passe est requis");
                    user_password.requestFocus();
                }
                else if(password.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Remettez votre mot de passe",Toast.LENGTH_LONG).show();
                    user_password.setError("Votre mot de passe doit avoir au moins 8 caratères");
                    user_password.requestFocus();
                }else if(TextUtils.isEmpty(confirm_password)){
                    Toast.makeText(RegisterActivity.this, "confirmer votre mot de passe",Toast.LENGTH_LONG).show();
                    user_confirmPassword.setError("Le champ Confirmation de mot de passe est requis");
                    user_confirmPassword.requestFocus();
                }else if(!password.equals(confirm_password)){
                    Toast.makeText(RegisterActivity.this, "votre mot de passe est incorrect",Toast.LENGTH_LONG).show();
                    user_confirmPassword.setError("Remettez le même mot de passe");
                    user_confirmPassword.requestFocus();
                    user_password.clearFocus();
                    user_confirmPassword.clearFocus();

                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    createAccount(username,email,telephone,password);
                }
            }
        });

    }

    //FireBase
    private void createAccount(String username, String email, String telephone, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    firebaseUser.updateProfile(profileChangeRequest);
                    ReadWriteUserDetails readWriterUserDetails = new ReadWriteUserDetails(telephone);
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(readWriterUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                // send email verification
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this,"Compte crée avec succès, verifier votre addresse email",Toast.LENGTH_LONG).show();
                                // open user profile after successfull registration
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this,"Création du compte échoué, veuillez reprendre",Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        user_password.setError("Votre mot de passe est faible, utiliser des lettres,des nombres et des caratères spécuales");
                        user_password.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        user_email.setError("Votre addresse email est invalid ou déjà été crée, changez d'addresse");
                        user_email.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        user_email.setError("L'utilisateur est déjà crée avec cet addresse email, utilisez un autre");
                        user_email.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
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

    /*public void onApiResponse(JSONObject response){
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
    }*/
}