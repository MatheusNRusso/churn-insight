package com.churninsight.api.mapper;

import com.churninsight.api.dto.CustomerInputDto;
import com.churninsight.api.dto.PredictionResponseDto;
import com.churninsight.api.model.PredictionModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PredictionMapper {

    PredictionModel toModel(CustomerInputDto dto);

    PredictionResponseDto toDto(PredictionModel model);
}
