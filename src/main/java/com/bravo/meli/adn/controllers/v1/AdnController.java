package com.bravo.meli.adn.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bravo.meli.adn.models.AdnModel;
import com.bravo.meli.adn.negocio.algoritmos.Algoritmo;
import com.bravo.meli.adn.negocio.algoritmos.AlgoritmoListillo;
import com.bravo.meli.adn.negocio.dto.DnaDto;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;
import com.bravo.meli.adn.services.AdnService;

@RestController
@RequestMapping("/api/v1/mutant")
public class AdnController {

	@Autowired
	AdnService adnService;

	Algoritmo algoritmo;

	@PostMapping
	public ResponseEntity<String> isMutant(@RequestBody DnaDto dna) {
		Algoritmo algoritmo = new AlgoritmoListillo();
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
				return ResponseEntity.status(403).build();
			}

		} catch (DatosInvalidosException e) {
			return ResponseEntity.status(500).body(e.getCode().getMessage());
		}
	}
}
