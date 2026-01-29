package engine;

import engine.dto.QuizQuestionRequest;
import engine.model.QuizQuestion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizService {
    private final Map<Integer, QuizQuestion> quizMap;
    private final AtomicInteger idCounter;

    public QuizService() {
        this.quizMap = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
    }

    public QuizQuestion addQuizQuestion(QuizQuestionRequest request) {
        int newId = idCounter.getAndIncrement();
        QuizQuestion question = new QuizQuestion(newId, request);
        quizMap.put(newId, question);
        return question;
    }

    public QuizQuestion getQuizQuestion(int id) {
        return quizMap.getOrDefault(id, null);
    }

    public List<QuizQuestion> getAllQuizQuestion() {
        return new ArrayList<>(quizMap.values());
    }

    public QuizQuestion updateQuizQuestion(int id, QuizQuestionRequest request) {
        QuizQuestion question = new QuizQuestion(id, request);
        return quizMap.replace(id, question);
    }

    public void deleteQuizQuestion(int id) {
        quizMap.remove(id);
    }
}
