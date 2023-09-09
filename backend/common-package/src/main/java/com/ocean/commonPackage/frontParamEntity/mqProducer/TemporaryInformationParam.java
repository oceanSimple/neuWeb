package com.ocean.commonPackage.frontParamEntity.mqProducer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryInformationParam {
    private String redisKey;
    private String key;
}
