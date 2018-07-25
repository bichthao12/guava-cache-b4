package b5;

import static spark.Spark.get;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import spark.Request;
import spark.Response;
import spark.Route;
public class Bai5 {
	public static void main(String[] args) {
		final LoadingCache<Integer, List<Integer>> primeCache =
				CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterWrite(20, TimeUnit.SECONDS)
				.expireAfterAccess(10, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, List<Integer>>() {
					@Override
					public List<Integer> load(Integer n) throws Exception {
						// TODO Auto-generated method stub
						return getAllPrime(n);
					}
				});
		final JSONObject jo = new JSONObject();
		get("/prime","application/json",new Route() {
			public Object handle(Request request, Response respone) throws Exception {
				 Integer n = Integer.parseInt(request.queryParams("n"));
				jo.put("prime",primeCache.get(n));
				jo.put("prime",primeCache.get(n));
				return "Cache Size: "+primeCache.size()+" "+jo.toJSONString();
			}
		});
	}
	private static List<Integer> getAllPrime(Integer n) {	
			List<Integer> listPrime = new ArrayList<Integer>();
			for ( int i = 0; i <= n; i++) {
				if (isPrime(i)) {
					listPrime.add(i);
				}
			}
			return listPrime;
	}
	private static boolean isPrime(int n) {
		if (n < 2) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
