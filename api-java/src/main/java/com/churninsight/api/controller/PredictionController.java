package com.churninsight.api.controller;

import com.churninsight.api.dto.CustomerInputDto;
import com.churninsight.api.dto.PredictionResponseDto;
import com.churninsight.api.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponseDto> predict(
            @Valid @RequestBody
            CustomerInputDto input,
            UriComponentsBuilder uriComponentsBuilder) {

        var prediction = predictionService.predict(input);
        var uri = uriComponentsBuilder.path("/predict/{id}")
                .buildAndExpand(prediction.id())
                .toUri();

        return ResponseEntity.created(uri).body(prediction);
    }
}
