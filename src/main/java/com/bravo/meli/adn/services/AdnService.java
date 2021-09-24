package com.bravo.meli.adn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bravo.meli.adn.models.AdnModel;
import com.bravo.meli.adn.repositories.AdnRepository;

@Service
public class AdnService {

	@Autowired
	AdnRepository adnRepository;

	public long adnHumano() {
		return adnRepository.countByTipo("HUMANO");
	}
	
	public long adnMutante() {
		return adnRepository.countByTipo("MUTANTE");
	}

	public AdnModel guardarAdn(AdnModel adn) {
		return adnRepository.save(adn);
	}

}
