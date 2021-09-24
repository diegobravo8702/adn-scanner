package com.bravo.meli.adn.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bravo.meli.adn.models.AdnModel;

@Repository
public interface AdnRepository extends CrudRepository<AdnModel, Long> {

	long countByTipo(String tipo);
}
