package com.carro.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.entity.Carro;
import com.carro.service.services.CarroService;

@RestController
@RequestMapping("/carro")
public class CarroController {

	@Autowired
	private CarroService carroService;

	@GetMapping("/")
	public ResponseEntity<List<Carro>> listarCarros() {
		List<Carro> carros = carroService.getAll();
		if (carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carros);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carro> obtenerCarroPorId(@PathVariable("id") Integer id) {
		Carro carro = carroService.getCarroById(id);
		if (carro == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(carro);
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> obtenerCarrosPorUsuarioId(@PathVariable("usuarioId") Integer usuarioId) {
		List<Carro> carros = carroService.byUsuarioId(usuarioId);
		if (carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(carros);
	}

	@PostMapping("/")
	public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro) {
		Carro nuevoCarro = carroService.save(carro);
		return ResponseEntity.ok(nuevoCarro);
	}

}
