package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.Usuario;
import com.usuario.service.models.Carro;
import com.usuario.service.models.Moto;
import com.usuario.service.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping()
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.getAll();

		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);

		if (usuario == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") Integer usuarioId) {
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.noContent().build();
		}

		List<Carro> carros = usuarioService.getCarros(usuarioId);
		return ResponseEntity.ok(carros);
	}

	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") Integer usuarioId) {
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if (usuario == null) {
			return ResponseEntity.noContent().build();
		}

		List<Moto> motos = usuarioService.getMotos(usuarioId);
		return ResponseEntity.ok(motos);
	}

	@GetMapping("todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVechiculos(@PathVariable("usuarioId") Integer usuarioId) {
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Carro carro) {
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);

		return ResponseEntity.ok(nuevoCarro);
	}

	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") Integer usuarioId, @RequestBody Moto moto) {
		Moto nuevaMoto = usuarioService.saveCarro(usuarioId, moto);

		return ResponseEntity.ok(nuevaMoto);
	}

}
