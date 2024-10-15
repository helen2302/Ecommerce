package com.csci318.ecommerce.feedback.config;


import com.csci318.ecommerce.feedback.model.FeedbackAggregation;
import com.csci318.ecommerce.vendor.model.FeedbackReceivedEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class FeedbackStreamProcessor {
    public final static String TOTAL_FEEDBACK = "total-feedback";

    @Bean
    public StreamsConfig kafkaStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "feedback-stream-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, FeedbackAggregationSerde.class);

        return new StreamsConfig(props);
    }

    @Bean
    public Consumer<KStream<Long, FeedbackReceivedEvent>> process() {
        return inputStream -> {
            // Generate RUNNING average ratings by product
            Serde<FeedbackAggregation> FeedbackAggregation;
            KTable<Long, FeedbackAggregation> totalFeedback = inputStream.map((key, value) -> {
                        Long productId = value.getProductId();
                        int rating = value.getRating();
                        return KeyValue.pair(productId, (double) rating);
                    })
                    .groupByKey(Grouped.with(Serdes.Long(), Serdes.Double()))
                    .aggregate(
                            () -> new FeedbackAggregation(0.0, 0), // Start with 0.0 rating and 0 count
                            (key, newValue, aggregateValue) ->
                                    new FeedbackAggregation(aggregateValue.getTotalRating() + newValue,
                                            aggregateValue.getCount() + 1),
                            Materialized.<Long, FeedbackAggregation, KeyValueStore<Bytes, byte[]>>as(TOTAL_FEEDBACK)
                                    .withKeySerde(Serdes.Long())
                                    .withValueSerde(new FeedbackAggregationSerde())
                    );

            // Print data to console
            totalFeedback.toStream()
                    .print(Printed.<Long, FeedbackAggregation>toSysOut().withLabel("Average ratings by product"));
        };
    }
}
