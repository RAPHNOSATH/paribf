package raphno.bf;

public class ScoreModel {
    private String winner;
    private String duration;
    private  TimeModel fullTime;
    private TimeModel halfTime;

    public String getWinner() {
        return winner;
    }

    public String getDuration() {
        return duration;
    }

    public TimeModel getFullTime() {
        return fullTime;
    }

    public TimeModel getHalfTime() {
        return halfTime;
    }
}
