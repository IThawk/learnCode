//package com.ithawk.demo.elasticsearch.springboot.controller;
//
//
//import com.ithawk.demo.elasticsearch.springboot.pojo.Car;
//import com.ithawk.demo.elasticsearch.springboot.utils.IMOOCJSONResult;
//import com.ithawk.demo.elasticsearch.springboot.utils.JsonUtils;
//import org.elasticsearch.common.geo.GeoPoint;
//import org.elasticsearch.common.unit.DistanceUnit;
//import org.elasticsearch.index.query.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.json.GsonJsonParser;
//import org.springframework.boot.json.JsonParser;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
//import org.springframework.data.elasticsearch.core.query.IndexQuery;
//import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//@Controller
//@RequestMapping("esmap")
//public class GeoController {
//
//    @Autowired
//    private ElasticsearchRestTemplate esTemplate;
//
//    /**
//     * 矩阵搜索
//     * @param geoBoundingBoxEntity
//     * @return
//     */
//    @PostMapping("/geoBoundingBox")
//    @ResponseBody
//    public IMOOCJSONResult geoBoundingBox(
//            @RequestBody GeoBoundingBoxEntity geoBoundingBoxEntity) {
//
//        GeoBoundingBoxQueryBuilder geoQueryBuilder = QueryBuilders
//                .geoBoundingBoxQuery("geo")
//                .setCorners(geoBoundingBoxEntity.getTopLeft(),
//                        geoBoundingBoxEntity.getBottomRight());
//
//        /***** es 6.x *****/
////        SearchQuery searchQuery = new NativeSearchQueryBuilder()
////                .withQuery(builder)
////                .build();
////        AggregatedPage<User> pagedUser = esTemplate.queryForPage(searchQuery, User.class);
////        List<User> userList = pagedUser.getContent();
//
//        /***** es 7.x *****/
////        NativeSearchQuery query = new NativeSearchQueryBuilder()
////                .withQuery(geoQueryBuilder)
////                .build();
////
////        SearchHits<Car> searchHits = esTemplate.search(query, Car.class);
////        List<SearchHit<Car>> searchHitList = searchHits.getSearchHits();
////        List<Map<String, Object>> mapList = new ArrayList<>();
////        for (SearchHit h : searchHitList) {
////            String originalJson = JsonUtils.objectToJson(h.getContent());
////            JsonParser jj = new GsonJsonParser();
////            Map<String, Object> carMap = jj.parseMap(originalJson);
////            mapList.add(carMap);
////        }
//        List<Map<String, Object>> mapList = doSearchQuery(geoQueryBuilder);
//        System.out.println(mapList);
//        return IMOOCJSONResult.ok(mapList);
//    }
//
//    /**
//     * 自定义区域搜索
//     * @param geoPointList
//     * @return
//     */
//    @PostMapping("/geoPolygon")
//    @ResponseBody
//    public IMOOCJSONResult geoPolygon(
//            @RequestBody List<GeoPoint> geoPointList) {
//
//        GeoPolygonQueryBuilder geoQueryBuilder =
//                QueryBuilders.geoPolygonQuery("geo",
//                        geoPointList);
//
//        List<Map<String, Object>> mapList = doSearchQuery(geoQueryBuilder);
//        return IMOOCJSONResult.ok(mapList);
//    }
//
//    /**
//     * 从当前位置搜索一定范围内的坐标点
//     * @param centerPoint
//     * @param km
//     * @return
//     */
//    @PostMapping("/geoDistance")
//    @ResponseBody
//    public IMOOCJSONResult geoDistance(
//            @RequestBody  GeoPoint centerPoint, String km) {
//
//        GeoDistanceQueryBuilder geoQueryBuilder =
//                QueryBuilders.geoDistanceQuery("geo")
//                        .distance(km, DistanceUnit.KILOMETERS)
//                        .point(centerPoint);
//        List<Map<String, Object>> mapList = doSearchQuery(geoQueryBuilder);
//        return IMOOCJSONResult.ok(mapList);
//    }
//
//    private List<Map<String, Object>> doSearchQuery(QueryBuilder queryBuilder) {
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(queryBuilder)
//                .build();
//
//        SearchHits<Car> searchHits = esTemplate.search(query, Car.class);
//        List<SearchHit<Car>> searchHitList = searchHits.getSearchHits();
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        for (SearchHit h : searchHitList) {
//            String originalJson = JsonUtils.objectToJson(h.getContent());
//            JsonParser jj = new GsonJsonParser();
//            Map<String, Object> carMap = jj.parseMap(originalJson);
//            mapList.add(carMap);
//        }
//        return mapList;
//    }
//
//    /**
//     * 添加坐标点
//     * @param car
//     * @return
//     */
//    @PostMapping("/addESPoint")
//    @ResponseBody
//    public IMOOCJSONResult addESPoint(@RequestBody Car car) {
//        Integer randomId = new Random().nextInt(1000000);
//        car.setUserId(Long.valueOf(randomId));
//        esTemplate.save(car);
//        return IMOOCJSONResult.ok();
//    }
//
//}
