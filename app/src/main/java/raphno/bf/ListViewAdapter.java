package raphno.bf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<MatchModel> matchModels;
    Context context;
    LayoutInflater inflater;

    public ListViewAdapter(ArrayList<MatchModel> matchModels, Context context) {
        this.matchModels = matchModels;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return matchModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cardview,viewGroup, false);
        TextView nomA = (TextView) view.findViewById(R.id.nom_equipeA);
        TextView nomB = (TextView) view.findViewById(R.id.nom_equipeB);
        MatchModel match = matchModels.get(i);
        nomA.setText(match.getHomeTeam().getName());
        nomB.setText(match.getAwayTeam().getName());
        return view;
    }
}
