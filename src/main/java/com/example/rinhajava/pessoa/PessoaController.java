package com.example.rinhajava.pessoa;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class PessoaController {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    private final RedisTemplate<String, PessoaDto> redisTemplate;

    @PostMapping("/pessoas")
    public ResponseEntity<Void> create(@RequestBody @Valid PessoaDto pessoaDto,
                                       UriComponentsBuilder ucb) {

        if (redisTemplate.opsForValue().get(pessoaDto.apelido()) != null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Pessoa persisted = repository.save(mapper.dtoToEntity(pessoaDto));

        redisTemplate.opsForValue().set(persisted.getId().toString(), mapper.entityToDto(persisted));
        redisTemplate.opsForValue().set(pessoaDto.apelido(), mapper.entityToDto(persisted));

        URI resource = ucb
                .path("/pessoas/{id}")
                .buildAndExpand(persisted.getId().toString())
                .toUri();

        return ResponseEntity.created(resource).build();
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PessoaDto> getById(@PathVariable UUID id) {

        PessoaDto cachedValue = redisTemplate.opsForValue().get(id.toString());
        if (cachedValue != null) {
            return new ResponseEntity<>(cachedValue, HttpStatus.OK);
        }

        Optional<Pessoa> pessoaOpt = repository.findById(id);

        return pessoaOpt.map(pessoa -> new ResponseEntity<>(mapper.entityToDto(pessoa), HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDto>> getByCriteria(@RequestParam("t") String criteria) {

        List<PessoaDto> listDto = repository
                .findByCriteria(criteria).stream().map(mapper::entityToDto).toList();

        return new ResponseEntity<>(listDto, HttpStatus.OK);

    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(repository.count(), HttpStatus.OK);
    }

    @GetMapping("/err")
    public ResponseEntity<List<String>> getListErr() {
        return new ResponseEntity<>(PessoaExceptionHandler.errs, HttpStatus.OK);
    }

}
