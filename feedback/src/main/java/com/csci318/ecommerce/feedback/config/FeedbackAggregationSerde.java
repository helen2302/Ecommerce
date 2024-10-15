package com.csci318.ecommerce.feedback.config;

import org.springframework.kafka.support.serializer.JsonSerde;
import com.csci318.ecommerce.feedback.model.FeedbackAggregation;

public class FeedbackAggregationSerde extends JsonSerde<FeedbackAggregation> {
    public FeedbackAggregationSerde() {
        super(FeedbackAggregation.class);
    }
}