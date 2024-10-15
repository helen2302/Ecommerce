package com.csci318.ecommerce.rating.config;

import com.csci318.ecommerce.rating.model.RatingAggregation;
import com.csci318.ecommerce.vendor.model.RatingReceivedEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class RatingStreamProcessor {
    public final static String TOTAL_RATING = "total-rating";

    @Bean
    public StreamsConfig kafkaStreamsConfigRating() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "rating-stream-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, RatingAggregationSerde.class);

        return new StreamsConfig(props);
    }

    @Bean
    public Consumer<KStream<Long, RatingReceivedEvent>> processRating() {
        return inputStream -> {
            // Generate RUNNING average ratings by product
            Serde<RatingAggregation> RatingAggregation;
            KTable<Long, RatingAggregation> totalRating = inputStream.map((key, value) -> {
                        Long vendorId = value.getVendorId();
                        int rating = value.getRating();
                        return KeyValue.pair(vendorId, (double) rating);
                    })
                    .groupByKey(Grouped.with(Serdes.Long(), Serdes.Double()))
                    .aggregate(
                            () -> new RatingAggregation(0.0, 0), // Start with 0.0 rating and 0 count
                            (key, newValue, aggregateValue) ->
                                 new RatingAggregation(aggregateValue.getTotalRating() + newValue,
                                        aggregateValue.getCount() + 1),
                            Materialized.<Long, RatingAggregation, KeyValueStore<Bytes, byte[]>>as(TOTAL_RATING)
                                    .withKeySerde(Serdes.Long())
                                    .withValueSerde(new RatingAggregationSerde())
                    );

            // Print data to console
            totalRating.toStream()
                    .print(Printed.<Long, RatingAggregation>toSysOut().withLabel("Average ratings by vendor"));
        };
    }





//    @Bean
//    public Function<KStream<Long, RatingReceivedEvent>, KStream<Long, RatingAggregation>> processRating() {
//        return inputStream -> {
//            // Generate RUNNING average ratings by product
//            return inputStream.map((key, value) -> {
//                        Long vendorId = value.getVendorId();
//                        int rating = value.getRating();
//                        return KeyValue.pair(vendorId, (double) rating);
//                    })
//                    .groupByKey(Grouped.with(Serdes.Long(), Serdes.Double()))
//                    .aggregate(
//                            () -> new RatingAggregation(0.0, 0), // Start with 0.0 rating and 0 count
//                            (key, newValue, aggregateValue) ->
//                                    new RatingAggregation(aggregateValue.getTotalRating() + newValue, aggregateValue.getCount() + 1),
//                            Materialized.<Long, RatingAggregation, KeyValueStore<Bytes, byte[]>>as(TOTAL_RATING)
//                                    .withKeySerde(Serdes.Long())
//                                    .withValueSerde(new RatingAggregationSerde())
//                    ).toStream();
//        };
//    }

}

