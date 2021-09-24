package com.bravo.meli.adn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bravo.meli.adn.models.AdnModel;
import com.bravo.meli.adn.negocio.algoritmos.AlgoritmoRegexEstricto;
import com.bravo.meli.adn.negocio.dto.DnaDto;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;
import com.bravo.meli.adn.services.AdnService;

@RestController
@RequestMapping("/mutant")
public class AdnController {

	@Autowired
	AdnService adnService;

	
	AlgoritmoRegexEstricto algoritmo;

	@PostMapping
	public ResponseEntity<String> isMutant(@RequestBody DnaDto dna) {
		AlgoritmoRegexEstricto algoritmo = new AlgoritmoRegexEstricto();
		AdnModel adnModel = new AdnModel();
		try {
			adnModel.setAdn(dna.toString());
			if (algoritmo.isMutant(dna.getDna())) {
				adnModel.setTipo("MUTANTE");
				adnService.guardarAdn(adnModel);
				return ResponseEntity.ok().build();
			} else {
				adnModel.setTipo("HUMANO");
				adnService.guardarAdn(adnModel);
				return ResponseEntity.status(403).body("Forbidden");
			}

		} catch (DatosInvalidosException e) {
			return ResponseEntity.status(500).body(e.getCode().getMessage());
		}
	}
}
