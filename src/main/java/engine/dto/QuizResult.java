package engine.dto;

public record QuizResult(boolean success, String feedback) {
    public QuizResult(boolean success) {
        this(success,
                success ? "Congratulations, you're right!" :
                        "Wrong answer! Please, try again.");
    }
}

