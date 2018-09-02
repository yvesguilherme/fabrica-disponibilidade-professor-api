package br.projecao.fabricadesoftware.disponibilidadeprofessoresapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.projecao.fabricadesoftware.disponibilidadeprofessoresapi.models.Titulacao;
import br.projecao.fabricadesoftware.disponibilidadeprofessoresapi.repository.TitulacaoRepository;
import br.projecao.fabricadesoftware.disponibilidadeprofessoresapi.resources.interfaces.Resource;

@RestController
@RequestMapping(value="/titulacao", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class TitulacaoResource implements Resource<Titulacao>{

	@Autowired
	private TitulacaoRepository repository;
	
	public ResponseEntity<List<Titulacao>> getAll() {
		List<Titulacao> lista = repository.findAll();
		HttpStatus status = HttpStatus.OK;
		if(lista == null || lista.isEmpty()) {
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<List<Titulacao>>(lista, getHeader(), status);
	}
	
	public ResponseEntity<Optional<Titulacao>> getOne(Long id) {
		Optional<Titulacao> model = repository.findById(id);
		HttpStatus status = HttpStatus.OK;
		if(!model.isPresent()) {
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<Optional<Titulacao>>(model, getHeader(), status);
	}
	
	public ResponseEntity<Titulacao> post(@RequestBody @Valid Titulacao entity) {
		HttpStatus status = HttpStatus.CREATED;
		Map<String, String> contents = new HashMap<>();
		try {
			repository.save(entity);
		} catch (Exception e) {
			status = HttpStatus.NOT_MODIFIED;
			Titulacao titulacao = repository.findByDescricao(entity.getDescricao());
			contents.put(HttpHeaders.LINK, titulacao.getId().toString());
			contents.put(HttpHeaders.WARNING, e.getMessage());
		}
		
		return new ResponseEntity<>(null, getHeader(contents), status);
	}
	
	public ResponseEntity<Titulacao> patch(@PathVariable("id") Long id, @RequestBody Titulacao entity) {
		entity.setId(id);
		fillInBlankFields(entity);
		repository.save(entity);
		HttpStatus status = HttpStatus.ACCEPTED;
		if(entity.getId() == null || entity.getId().longValue() <= 0) {
			status = HttpStatus.NOT_MODIFIED;
		}
		return new ResponseEntity<>(null, getHeader(), status);
	}
	
	public ResponseEntity<Titulacao> put(@PathVariable("id") Long id, @RequestBody Titulacao entity) {
		entity.setId(id);
		repository.save(entity);
		HttpStatus status = HttpStatus.ACCEPTED;
		if(entity.getId() == null || entity.getId().longValue() <= 0) {
			status = HttpStatus.NOT_MODIFIED;
		}
		return new ResponseEntity<>(null, getHeader(), status);
	}
	
	public ResponseEntity<Titulacao> delete(@PathVariable("id") Long id) {
		HttpStatus status = HttpStatus.NO_CONTENT;
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}else {
			status = HttpStatus.NOT_MODIFIED;
		}
		return new ResponseEntity<>(null, getHeader(), status);
	}
	
	public void fillInBlankFields(Titulacao entity) {
		Titulacao oldEntity = repository.findById(entity.getId()).get();
		merge(entity, oldEntity);
	}
	
	public void merge(Titulacao newEntity, Titulacao oldEntity) {
		// TODO Auto-generated method stub
		
	}

}
