package bbdd2.accidents.repository.imlp;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.core.MainResponse.Version;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import bbdd2.accidents.repository.CustomAccidentRepository;

public class CustomAccidentRepositoryImpl implements CustomAccidentRepository {

	public JSONObject findMostCommonConditions(String s) {

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));

	    int numberOfSearchHitsToReturn = 0; //	defaults to 10
	    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
	    sourceBuilder.size(numberOfSearchHitsToReturn);
	    GlobalAggregationBuilder aggregation = AggregationBuilders.global("agg")
	            .subAggregation(AggregationBuilders.terms(s).field(s).size(1).order(BucketOrder.count(false)));
	    sourceBuilder.aggregation(aggregation);
	    try {
		    SearchRequest searchRequest = new SearchRequest("testing").source(sourceBuilder);
		    SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		    Aggregations aggregations = searchResponse.getAggregations();
		    JSONObject jsonResponse = new JSONObject(searchResponse.toString().replaceAll("(dterms|sterms)","terms"));		      
		    JSONArray jsonResult = jsonResponse.getJSONObject("aggregations").getJSONObject("global#agg").getJSONObject("terms#"+s).getJSONArray("buckets");
		    client.close();
		    JSONObject result = jsonResult.getJSONObject(0); 
		    result.put("repeticiones", result.remove("doc_count"));
		    result.put("valor", result.remove("key"));
//		    System.out.println(s+" "+result);
		    return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
