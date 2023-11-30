package day02;

public enum Option {

    ROCK('B', 'C'),
    PAPER('C', 'A'),
    SCISSORS('A', 'B');

    private final char losesAgainst;
    private final char winsAgainst;

    Option(char losesAgainst, char winsAgainst) {
        this.losesAgainst = losesAgainst;
        this.winsAgainst = winsAgainst;
    }

    private Integer getPlayerChoiceScore() {
        return this == ROCK ? 1 : this == PAPER ? 2 : 3;
    }

    public Integer getOutcome(char opponentChoice) {
        Integer playerChoiceScore = this.getPlayerChoiceScore();
        Integer outcomeScore = this.losesAgainst == opponentChoice ? 0 : this.winsAgainst == opponentChoice ? 6 : 3;
        return outcomeScore + playerChoiceScore;
    }

}
