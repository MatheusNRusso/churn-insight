package com.churninsight.api.dto;

import com.churninsight.api.model.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CustomerInputDto(

        // 1. GENDER (string: Male/Female)
        @NotNull(message = "gender é obrigatório")
        @JsonProperty("gender")
        Gender gender,

        // 2. SENIOR CITIZEN (boolean: true/false)
        @NotNull(message = "seniorCitizen é obrigatório")
        @JsonProperty("seniorCitizen")
        Boolean seniorCitizen,

        // 3. PARTNER (boolean: true/false)
        @NotNull(message = "partner é obrigatório")
        @JsonProperty("partner")
        Boolean partner,

        // 4. DEPENDENTS (boolean: true/false)
        @NotNull(message = "dependents é obrigatório")
        @JsonProperty("dependents")
        Boolean dependents,

        // 5. TENURE = contractMonths (int: 0-72)
        @NotNull(message = "contractMonths é obrigatório")
        @Min(value = 0, message = "contractMonths deve ser no mínimo 0")
        @Max(value = 72, message = "contractMonths deve ser no máximo 72")
        @JsonProperty("contractMonths")
        Integer contractMonths,

        // 6. PHONE SERVICE (boolean: true/false)
        @NotNull(message = "phoneService é obrigatório")
        @JsonProperty("phoneService")
        Boolean phoneService,

        // 7. MULTIPLE LINES (string: Yes/No/No phone service)
        @NotNull(message = "multipleLines é obrigatório")
        @JsonProperty("multipleLines")
        MultipleLines multipleLines,

        // 8. INTERNET SERVICE (string: DSL/Fiber optic/No)
        @NotNull(message = "internetService é obrigatório")
        @JsonProperty("internetService")
        InternetService internetService,

        // 9. ONLINE SECURITY (boolean: true/false)
        @NotNull(message = "onlineSecurity é obrigatório")
        @JsonProperty("onlineSecurity")
        StateService onlineSecurity ,

        // 10. ONLINE BACKUP (boolean: true/false)
        @NotNull(message = "onlineBackup é obrigatório")
        @JsonProperty("onlineBackup")
        StateService onlineBackup,

        // 11. DEVICE PROTECTION (boolean: true/false)
        @NotNull(message = "deviceProtection é obrigatório")
        @JsonProperty("deviceProtection")
        StateService deviceProtection,

        // 12. TECH SUPPORT (boolean: true/false)
        @NotNull(message = "techSupport é obrigatório")
        @JsonProperty("techSupport")
        StateService techSupport,

        // 13. STREAMING TV (boolean: true/false)
        @NotNull(message = "streamingTV é obrigatório")
        @JsonProperty("streamingTV")
        StateService streamingTV,

        // 14. STREAMING MOVIES (boolean: true/false)
        @NotNull(message = "streamingMovies é obrigatório")
        @JsonProperty("streamingMovies")
        StateService streamingMovies,

        // 15. CONTRACT TYPE (string: Month-to-month/One year/Two year)
        @NotNull(message = "contractType é obrigatório")
        @JsonProperty("contractType")
        ContractType contractType,

        // 16. PAPERLESS BILLING (boolean: true/false)
        @NotNull(message = "paperlessBilling é obrigatório")
        @JsonProperty("paperlessBilling")
        Boolean paperlessBilling,

        // 17. PAYMENT METHOD (string: Electronic check/Mailed check/Bank transfer/Credit card)
        @NotNull(message = "paymentMethod é obrigatório")
        @JsonProperty("paymentMethod")
        PaymentMethod paymentMethod,

        // 18. MONTHLY CHARGES (double: ≥ 0)
        @NotNull(message = "monthlyCharges é obrigatório")
        @DecimalMin(value = "0.0", message = "monthlyCharges deve ser ≥ 0")
        @DecimalMax(value = "200.0", message = "monthlyCharges muito alto para telecom")
        @Digits(integer = 4, fraction = 2, message = "monthlyCharges deve ter no máximo 4 inteiros e 2 decimais")
        @JsonProperty("monthlyCharges")
        Double monthlyCharges,

        // 19. TOTAL CHARGES (double: ≥ 0)
        @NotNull(message = "totalCharges é obrigatório")
        @DecimalMin(value = "0.0", message = "totalCharges deve ser ≥ 0")
        @DecimalMax(value = "10000.0", message = "totalCharges muito alto")
        @Digits(integer = 6, fraction = 2, message = "totalCharges deve ter no máximo 6 inteiros e 2 decimais")
        @JsonProperty("totalCharges")
        Double totalCharges

) {}