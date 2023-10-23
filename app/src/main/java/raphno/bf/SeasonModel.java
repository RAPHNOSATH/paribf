package raphno.bf;

public class SeasonModel {
    private int id;
    private String startDate;
    private String endDate;
    private int currentMatchday;
    private String winner;

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getCurrentMatchday() {
        return currentMatchday;
    }

    public String getWinner() {
        return winner;
    }
}
