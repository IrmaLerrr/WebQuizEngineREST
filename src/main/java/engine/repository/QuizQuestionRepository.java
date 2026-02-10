package engine.repository;

import engine.model.QuizQuestion;
import org.springframework.data.repository.CrudRepository;

public interface QuizQuestionRepository extends CrudRepository<QuizQuestion, Integer> {

}
