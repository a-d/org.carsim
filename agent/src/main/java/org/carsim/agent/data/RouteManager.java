package org.carsim.agent.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteManager {
	private static final long WAITING = 1000;
	public static String url ="http://router.project-osrm.org/viaroute";

	public static String getUrl(Point...points) {
		String u = url;
		for(int i=0; i<points.length; i++) {
			u += (i==0 ? "?" : "&") + "loc=" + points[i].latitude + "," + points[i].longitude;
		}
		return u;
	}
	
	private static long lastTime = 0;
	

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    }
	    finally {
	      is.close();
	    }
	}

	public static Route getRoute(Point start, Point end) {
		Route route = new Route();
	    try {
	    	long currTime = System.currentTimeMillis();
	    	long delay = lastTime+WAITING-currTime;
	    	if(delay>0) {
	    		Thread.sleep(delay);
	    	}
	    	
	    	double precision = 1/1E6;
			JSONObject json = readJsonFromUrl(getUrl(start, end));

			Object geo = json.getString("route_geometry");
			if(geo!=null) {
				route.route = PolylineDecoder.decodePoly(geo.toString(), precision).toArray(new Point[0]);
			}
			JSONObject sum = json.getJSONObject("route_summary");
			if(sum!=null) {
				route.duration = sum.getInt("total_time");
				route.distance = sum.getInt("total_distance");
				route.endStreet = sum.getString("end_point");
				route.startStreet = sum.getString("start_point");
			}
			JSONArray poi = json.getJSONArray("via_points");
			if(poi!=null) {
				JSONArray poiStart = poi.getJSONArray(0);
				JSONArray poiEnd = poi.getJSONArray(1);
				if(poiStart!=null && poiEnd!=null) {
					route.startPoint = new Point(poiStart.get(0), poiStart.get(1));
					route.endPoint = new Point(poiEnd.get(0), poiEnd.get(1));
				}
			}
			return route;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return route;
	}

}
