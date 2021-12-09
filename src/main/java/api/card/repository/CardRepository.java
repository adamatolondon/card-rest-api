package api.card.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import api.card.model.Card;

public interface CardRepository extends CrudRepository<Card, Long> {
	List<Card> findByNumberAndCustomerId(String number, Long customerId);

	List<Card> findByCustomerId(Long customerId, Sort sort);
}
