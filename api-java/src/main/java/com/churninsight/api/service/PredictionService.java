package com.churninsight.api.service;

import com.churninsight.api.client.MlPredictionClient;
import com.churninsight.api.dto.CustomerInputDto;
import com.churninsight.api.dto.PredictionResponseDto;
import com.churninsight.api.mapper.PredictionMapper;
import com.churninsight.api.model.PredictionModel;
import com.churninsight.api.util.PredictionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionMapper predictionMapper;
    private final MlPredictionClient mlPredictionClient;

    //contador simples em memória para gerar IDs das previsões
    private long counter;

    {
        counter = 1L;
    }

    public PredictionResponseDto predict(CustomerInputDto dto) {

//        double raw = 0.0;
//
//        raw += PredictionUtils.scoreByGender(dto.gender());
//        raw += PredictionUtils.scoreByContractMonths(dto.contractMonths());
//        raw += PredictionUtils.scoreByContractType(dto.contractType());
//        raw += PredictionUtils.scoreByMonthlyCharges(dto.monthlyCharges());
//        raw += PredictionUtils.scoreByTotalCharges(dto.totalCharges());
//        raw += PredictionUtils.scoreByPaymentMethod(dto.paymentMethod());
//        raw += PredictionUtils.scoreByPaperlessBilling(dto.paperlessBilling());
//        raw += PredictionUtils.scoreByInternetService(dto.internetService());
//
//        raw += PredictionUtils.scoreByPhoneService(dto.phoneService());
//        raw += PredictionUtils.scoreByMultipleLines(dto.multipleLines());
//
//        raw += PredictionUtils.scoreByOnlineSecurity(dto.onlineSecurity(), dto.internetService());
//        raw += PredictionUtils.scoreByOnlineBackup(dto.onlineBackup(), dto.internetService());
//        raw += PredictionUtils.scoreByDeviceProtection(dto.deviceProtection(), dto.internetService());
//        raw += PredictionUtils.scoreByTechSupport(dto.techSupport(), dto.internetService());
//        raw += PredictionUtils.scoreByStreamingTV(dto.streamingTV(), dto.internetService());
//        raw += PredictionUtils.scoreByStreamingMovies(dto.streamingMovies(), dto.internetService());
//
//        raw += PredictionUtils.scoreBySeniorCitizen(dto.seniorCitizen());
//        raw += PredictionUtils.scoreByPartner(dto.partner());
//        raw += PredictionUtils.scoreByDependents(dto.dependents());
//
//        double probability = PredictionUtils.normalizeToProbability(raw);
//
//
//        // Regra de decisão simples
//        String prediction = probability >= 0.5 ? "Churn" : "No Churn";
//

        var ml = mlPredictionClient.predict(dto);
        var model = PredictionModel.builder()
                .id(counter++)
                .prediction(ml.prediction())
                .probability(ml.probability())
                .build();

        return predictionMapper.toDto(model);
    }
}
