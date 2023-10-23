package raphno.bf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstActivity extends AppCompatActivity {
    ListViewAdapter adapter;
    ArrayList<MatchModel> matchModels = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Appel de l'api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.football-data.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // instance de l'interface
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<DataModel> call = apiCall.getData();

        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if(response.code() !=200) {
                    Log.e("API", "Erreur lors de chargement de l'API");
                }
                String data = "";
                String match ="\n";
                ArrayList<MatchModel> matchs = response.body().getMatches();
                //  33333333333333
                matchModels = matchs;
                listView = (ListView) findViewById(R.id.id_liste);
                adapter = new ListViewAdapter(matchModels, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //444444444444

            }
            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e("API","Erreur lors de chargement de l'API");
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_interface, menu);
        return true;
    }
    public void formsUsers(View view){
        startActivity(new Intent(FirstActivity.this,RegisterActivity.class));
        finish();
    }
    public void formsLogin(View view){
        startActivity(new Intent(FirstActivity.this,LoginActivity.class));
        finish();
    }
}