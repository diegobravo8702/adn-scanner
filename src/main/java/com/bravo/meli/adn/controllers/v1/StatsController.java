package com.bravo.meli.adn.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bravo.meli.adn.negocio.dto.StatsDto;
import com.bravo.meli.adn.services.AdnService;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

	@Autowired
	AdnService adnService;

	@GetMapping
	public ResponseEntity<StatsDto> getStats() {

		StatsDto estadisticas = new StatsDto();
		estadisticas.setCount_human_dna(adnService.adnHumano());
		estadisticas.setCount_mutant_dna(adnService.adnMutante());
		estadisticas.setRatio((double)estadisticas.getCount_mutant_dna()/estadisticas.getCount_human_dna());
		return ResponseEntity.ok(estadisticas);
	}

}
