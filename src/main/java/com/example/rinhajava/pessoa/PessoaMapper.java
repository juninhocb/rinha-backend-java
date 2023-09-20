package com.example.rinhajava.pessoa;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
class PessoaMapper {
    public List<String> stringToList(String str) {


        List<String> list = new ArrayList<>();

        if (str == null || str.isEmpty()) {
            return list;
        }

        String[] items = str.split(",");

        list.addAll(Arrays.asList(items));

        return list;
    }

    public String listToStr(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        return list.toString();
    }

    public PessoaDto entityToDto(Pessoa entity) {
        return PessoaDto.builder()
                .nome(entity.getNome())
                .apelido(entity.getApelido())
                .nascimento(entity.getNascimento())
                .stack(stringToList(entity.getStack()))
                .build();
    }

    public Pessoa dtoToEntity(PessoaDto dto) {

        return Pessoa.builder()
                .nome(dto.nome())
                .apelido(dto.apelido())
                .nascimento(dto.nascimento())
                .stack(listToStr(dto.stack()))
                .build();
    }
}
