package raphno.bf;

import java.util.ArrayList;

public class DataModel {
    private FilterModel filters;
    private ResultModel resultSet;
    private ArrayList<MatchModel> matches;

    public FilterModel getFilters() {
        return filters;
    }

    public ResultModel getResultSet() {
        return resultSet;
    }

    public ArrayList<MatchModel> getMatches() {
        return matches;
    }
}
