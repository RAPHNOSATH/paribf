package raphno.bf;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                for(int i = 0; i< matchs.size(); i++){
                    match = match + "\n" + matchs.get(i).getUtcDate() + ":"
                            + matchs.get(i).getHomeTeam().getName()
                            + " - "
                            +matchs.get(i).getAwayTeam().getName();
                }
                data = "Nombres de matches: "+response.body().getResultSet().getCount()
                    + "Matches :" +match ;
                Log.i("API :", data);
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Log.e("API","Erreur lors de chargement de l'API");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

}