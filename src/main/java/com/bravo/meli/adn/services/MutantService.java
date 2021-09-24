package com.bravo.meli.adn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bravo.meli.adn.negocio.algoritmos.AlgoritmoRegexEstricto;
import com.bravo.meli.adn.negocio.dto.DnaDto;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

@RestController
@RequestMapping("/mutant")
public class MutantService {

	@Autowired
	AlgoritmoRegexEstricto algoritmo;

	@PostMapping
	public ResponseEntity<String> isMutant(@RequestBody DnaDto dna) {
		try {
			if (algoritmo.isMutant(dna.getDna())) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(403).body("Forbidden");
			}

		} catch (DatosInvalidosException e) {
			return ResponseEntity.status(500).body(e.getCode().getMessage());
		}
	}
}
