package com.csci318.ecommerce.feedback.service;

import com.csci318.ecommerce.feedback.model.FeedbackAggregation;
import com.csci318.ecommerce.feedback.model.RatingByProduct;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.csci318.ecommerce.feedback.config.FeedbackStreamProcessor.TOTAL_FEEDBACK;

@Service
public class FeedbackQueryService {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    public List<RatingByProduct> getAllRatingByProduct() {

        KeyValueIterator<Long, FeedbackAggregation> all = getAllFeedback().all();
        List<RatingByProduct> allRatingByProducts = new ArrayList<>();
        while (all.hasNext()) {
            KeyValue<Long, FeedbackAggregation> ks = all.next();
            RatingByProduct ratingByProduct = new RatingByProduct();
            ratingByProduct.setProductId(ks.key);
            ratingByProduct.setRating(ks.value.getTotalRating() / (ks.value.getCount()));
            allRatingByProducts.add(ratingByProduct);
        }
        return allRatingByProducts;
    }

    public ReadOnlyKeyValueStore < Long, FeedbackAggregation > getAllFeedback() {
        return interactiveQueryService.getQueryableStore(TOTAL_FEEDBACK, QueryableStoreTypes.keyValueStore());
    }
}
