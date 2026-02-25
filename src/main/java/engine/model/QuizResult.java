package engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer quizId;

    boolean success;

    String author;

    LocalDateTime completedAt;

    public QuizResult(Integer quizId, boolean success, String author, LocalDateTime completedAt) {
        this.quizId = quizId;
        this.success = success;
        this.author = author;
        this.completedAt = completedAt;
    }
}
