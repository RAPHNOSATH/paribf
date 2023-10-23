package raphno.bf;

public class MatchModel {
    private AreaModel area;
    private  CompetitionModel competition;
    private SeasonModel season;
    private int id;
    private String utcDate;
    private String status;
    private String matchday;
    private String  stage;
    private String group;
    private String lastUpdate;
    private EquipeModel homeTeam;
    private EquipeModel awayTeam;
    private ScoreModel score;

    public AreaModel getArea() {
        return area;
    }

    public CompetitionModel getCompetition() {
        return competition;
    }

    public SeasonModel getSeason() {
        return season;
    }

    public int getId() {
        return id;
    }

    public String getUtcDate() {
        return utcDate;
    }

    public String getStatus() {
        return status;
    }

    public String getMatchday() {
        return matchday;
    }

    public String getStage() {
        return stage;
    }

    public String getGroup() {
        return group;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public EquipeModel getHomeTeam() {
        return homeTeam;
    }

    public EquipeModel getAwayTeam() {
        return awayTeam;
    }

    public ScoreModel getScore() {
        return score;
    }
}
