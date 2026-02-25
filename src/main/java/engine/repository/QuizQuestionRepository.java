package engine.repository;

import engine.model.QuizQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizQuestionRepository extends PagingAndSortingRepository<QuizQuestion, Integer>, CrudRepository<QuizQuestion, Integer> {

}

