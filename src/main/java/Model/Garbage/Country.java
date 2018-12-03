package Model.Garbage;

import com.wirefreethought.geodb.client.GeoDbApi;
import com.wirefreethought.geodb.client.model.CitiesResponse;
import com.wirefreethought.geodb.client.model.GeoDbInstanceType;
import com.wirefreethought.geodb.client.net.GeoDbApiClient;
import com.wirefreethought.geodb.client.request.FindCitiesRequest;

import java.util.HashMap;


public class Country {

    private static String path = "C:\\Users\\nkutsky\\Desktop\\Retrival\\Posting";
    private static GeoDbApiClient geoApi = new GeoDbApiClient(GeoDbInstanceType.FREE);
    private static GeoDbApi geoDbApi = new GeoDbApi(geoApi);
    private static HashMap<String,StringBuilder> cityList = new HashMap<>();

    public static void setCity(String cityName) {
        cityList.put(cityName, new StringBuilder());
    }

    public static boolean checkCity(String cityName) {
        CitiesResponse cr = geoDbApi.findCities(
                FindCitiesRequest.builder()
                        .namePrefix(cityName).build());
        return (cr.getData().size()==1);
    }

    public static HashMap<String, StringBuilder> getCityList() {
        return cityList;
    }

    public static void index(){}

}
