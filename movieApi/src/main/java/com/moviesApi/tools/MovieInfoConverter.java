package com.moviesApi.tools;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviesApi.entities.MovieInfo;

import java.io.IOException;
import java.util.Map;

@Converter(autoApply = true)
public class MovieInfoConverter implements AttributeConverter<Map<Long, MovieInfo>, String> {

    @Override
    public String convertToDatabaseColumn(Map<Long, MovieInfo> attribute) {
        // Convertir la Map en une représentation JSON ou XML (ou autre format) pour le stockage en base de données

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erreur lors de la conversion en base de données", e);
        }
    }

    @Override
    public Map<Long, MovieInfo> convertToEntityAttribute(String dbData) {
        // Convertir la représentation stockée en base de données (JSON ou XML) en une Map
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(dbData, new TypeReference<Map<Long, MovieInfo>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Erreur lors de la conversion depuis la base de données", e);
        }
    }
}
