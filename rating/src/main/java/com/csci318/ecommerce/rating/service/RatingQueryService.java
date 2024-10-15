package com.csci318.ecommerce.rating.service;

import com.csci318.ecommerce.rating.model.RatingAggregation;
import com.csci318.ecommerce.rating.model.RatingByVendor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.csci318.ecommerce.rating.config.RatingStreamProcessor.TOTAL_RATING;

@Service
public class RatingQueryService {

    @Autowired
    private InteractiveQueryService interactiveQueryService;


    public List<RatingByVendor> getAllRatingByVendor() {

        KeyValueIterator<Long, RatingAggregation> all = getAllRating().all();
        List<RatingByVendor> allRatingByVendors = new ArrayList<>();
        while (all.hasNext()) {
            KeyValue<Long, RatingAggregation> ks = all.next();
            RatingByVendor ratingByVendor = new RatingByVendor();
            ratingByVendor.setVendorId(ks.key);
            ratingByVendor.setRating(ks.value.getTotalRating() / (ks.value.getCount()));
            allRatingByVendors.add(ratingByVendor);
        }
        return allRatingByVendors;
    }

    public RatingByVendor getRatingByVendor(Long vendorId) {

        // Retrieve the rating aggregation for the specific vendorId
        RatingAggregation ratingAggregation = getAllRating().get(vendorId);

        // Check if the rating aggregation exists for the given vendor
        if (ratingAggregation != null) {
            RatingByVendor ratingByVendor = new RatingByVendor();
            ratingByVendor.setVendorId(vendorId);
            ratingByVendor.setRating(ratingAggregation.getTotalRating() / ratingAggregation.getCount());
            return ratingByVendor;
        } else {
            // Handle the case where the vendor does not have a rating
            return null; // or throw an exception depending on your use case
        }
    }


    public ReadOnlyKeyValueStore < Long, RatingAggregation> getAllRating() {
        return interactiveQueryService.getQueryableStore(TOTAL_RATING, QueryableStoreTypes.keyValueStore());
    }


}
