package com.atguigu.gulimall.search.test;


import com.atguigu.gulimall.search.Application;
import com.atguigu.gulimall.search.pojo.Car;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.GeoDistanceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.InternalGeoDistance;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.ParsedGeoDistance;
import org.elasticsearch.search.aggregations.metrics.ParsedGeoBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESGEOTest {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    /**
     * 统计一定范围内坐标点的个数
     */
    @Test
    public void geo_distance_aggs() {

        GeoPoint position = new GeoPoint(32.026904,  118.795254);
//        GeoPoint position = new GeoPoint(32.027042,  118.79549);

        GeoDistanceAggregationBuilder tongjiAggs = AggregationBuilders
                            .geoDistance("tongji", position)
                            .field("geo")
                            .unit(DistanceUnit.KILOMETERS)
                            .distanceType(GeoDistance.PLANE)
                            .addRange(0, 1)
                            .addRange(1, 5)
                            .addRange(5, 100);

        /***** es7.x *****/
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(tongjiAggs)
                .build();

        /***** es6.x *****/
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .addAggregation(tongjiAggs)
//                .build();

        SearchHits<Car> searchHits = esTemplate.search(query, Car.class);
        Aggregations aggregations = searchHits.getAggregations();

//        Aggregations aggregations =
//                esTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
//                    @Override
//                    public Aggregations extract(SearchResponse response) {
//                        return response.getAggregations();
//                    }
//                });

        ParsedGeoDistance parsedGeoDistance = aggregations.get("tongji");
        List bucketList = parsedGeoDistance.getBuckets();

        System.out.println(bucketList);
        for (int i = 0 ; i < bucketList.size() ; i ++) {
            ParsedGeoDistance.ParsedBucket bucket = (ParsedGeoDistance.ParsedBucket) bucketList.get(i);
            long docCount = bucket.getDocCount();
            String key = bucket.getKey();

            System.out.println("key: " + key);
            System.out.println("docCount: " + docCount);
        }

    }
}
