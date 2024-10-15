package com.csci318.ecommerce.rating.config;

import com.csci318.ecommerce.rating.model.RatingAggregation;
import org.springframework.kafka.support.serializer.JsonSerde;

public class RatingAggregationSerde extends JsonSerde<RatingAggregation> {
    public RatingAggregationSerde() {
        super(RatingAggregation.class);
    }
}