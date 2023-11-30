package day02;

public enum Decryptor {

    // Rock
    A(Option.PAPER, Option.ROCK, Option.SCISSORS),

    // Paper
    B(Option.SCISSORS, Option.PAPER, Option.ROCK),

    // Scissors
    C(Option.ROCK, Option.SCISSORS, Option.PAPER);

    private final Option oppToWin;
    private final Option oppToDraw;
    private final Option oppToLose;

    Decryptor(Option oppToWin, Option oppToDraw, Option oppToLose) {
        this.oppToWin = oppToWin;
        this.oppToDraw = oppToDraw;
        this.oppToLose = oppToLose;
    }

    public Option getOutcome(char wantedOutcome) {
        if (wantedOutcome == 'X') return this.oppToLose;
        if (wantedOutcome == 'Y') return this.oppToDraw;
        return this.oppToWin;
    }

}
