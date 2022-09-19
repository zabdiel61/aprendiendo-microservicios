package com.usuario.service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.models.Carro;
import com.usuario.service.models.Moto;
import com.usuario.service.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeignClient motoFeignClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Metodos utilizando restTemplate para comunicar microservicios
	public List<Carro> getCarros(Integer usuarioId) {
		// con restTemplate vamos a obtener la lista de microservicio de carros
		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
		return carros;
	}

	public List<Moto> getMotos(Integer usuarioId) {
		// con restTemplate vamos a obtener la lista de microservicio de motos
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
		return motos;
	}
	// fin restTemplate

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}

	public Map<String, Object> getUsuarioAndVehiculos(Integer usuarioId) {
		Map<String, Object> resultado = new HashMap<>();

		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		if (usuario == null) {
			resultado.put("Mensaje", "El Usuario no existe");
			return resultado;
		}
		resultado.put("Usuario", usuario);
		
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if (carros.isEmpty()) {
			resultado.put("Carros", "El Usuario no no tiene carros");
			return resultado;
		} else {
			resultado.put("Carros", carros);
		}

		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		if (motos.isEmpty()) {
			resultado.put("Motos", "El Usuario no no tiene motos");
			return resultado;
		} else {
			resultado.put("Motos", motos);
		}

		return resultado;
	}

	// Metodos para feignClient para comunicacion entre microservicios
	public Carro saveCarro(Integer usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}

	public Moto saveCarro(Integer usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto = motoFeignClient.save(moto);
		return nuevaMoto;
	}
	// Fin de FeigClient
}
