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

//	@Autowired
//	private RestHighLevelClient client;

	// {"size":0,"aggs":{"pressure":{"terms": {"field":"pressure","size":1,"order":{"_count":"desc"}}}}}
	public JSONObject findMostCommonConditions(String s) {

//		RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http")));
//		System.out.println(client);

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));
//		System.out.println("Cliente conectado. ");
//		try {
//			MainResponse response = client.info(RequestOptions.DEFAULT);
//			String clusterName = response.getClusterName();
//			String clusterUuid = response.getClusterUuid();
//			String nodeName = response.getNodeName();
//			Version version = response.getVersion();
//			System.out.println("Información del cluster: ");
//			System.out.println("Nombre del cluster: "+ clusterName);
//			System.out.println("Identificador del cluster: "+ clusterUuid);
//			System.out.println("Nombre de los nodos del cluster: "+ nodeName);
//			System.out.println("Versión de elasticsearch del cluster: "+ version.toString());
//			client.close();
//			System.out.println("Cliente desconectado.");
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	    int numberOfSearchHitsToReturn = 0; // defaults to 10
	    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
	    sourceBuilder.size(numberOfSearchHitsToReturn);
	    GlobalAggregationBuilder aggregation = AggregationBuilders.global("agg")
	            .subAggregation(AggregationBuilders.terms(s).field(s).size(1).order(BucketOrder.count(false)));
	    sourceBuilder.aggregation(aggregation);
	    try {
		    SearchRequest searchRequest = new SearchRequest("prueba_geo_end").source(sourceBuilder);
		    SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		    Aggregations aggregations = searchResponse.getAggregations();
	//		Terms byZipAggregation = aggregations.get("home_zip");
	//		System.out.print(byZipAggregation);

//		    System.out.println(searchRequest);
//		    System.out.println("-----------------");
//		    System.out.println(searchResponse);
//		    System.out.println(searchResponse.toString());

		    JSONObject jsonResponse = new JSONObject(searchResponse.toString());
//			System.out.println(((JSONObject) ((JSONObject)jsonResponse.get("aggregations")).get("global#agg")).get("dterms#"+s));
		    
		    JSONArray jsonResult = jsonResponse.getJSONObject("aggregations").getJSONObject("global#agg").getJSONObject("dterms#"+s).getJSONArray("buckets");
		    System.out.println(s+" "+jsonResult.getJSONObject(0));
		    client.close();
		    JSONObject result = jsonResult.getJSONObject(0); 
		    result.put("repeticiones", result.remove("doc_count"));
		    result.put("valor", result.remove("key"));
		    return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
