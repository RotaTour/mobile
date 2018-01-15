package br.ufrpe.projetao.rotatour.requests_volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.SharedPrefManager;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mQueue;

    private VolleySingleton(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replace(" ", "%20");
    }

    //TODO HEADERS
    public void getObjectWithHeader(String url, Response.Listener<JSONObject> callback, Response.ErrorListener error, final String email, final String password) {
        url = removerAcentos(url);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, callback, error) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //Adicionar cabeçalho à requisição
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void postLogin(final String email, final String password,
                          Response.Listener<String> callback, Response.ErrorListener error) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, callback, error) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(postRequest);
    }

    public void postRegister(Response.Listener<String> callback, Response.ErrorListener error,
                             final String nome, final String email, final String password) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, callback, error) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nome);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        mQueue.add(postRequest);
    }

    public void postRegisterSocial(
                            final String nome, final String email, final String avatar,
                            final String provider, final String provider_id,
                            Response.Listener<String> callback, Response.ErrorListener error) {

        final StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER_SOCIAL, callback, error) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nome);
                params.put("email", email);
                params.put("avatar", avatar);
                params.put("provider", provider);
                params.put("provider_id", provider_id);

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);
                }
                return super.parseNetworkResponse(response);
            }
        };
        mQueue.add(postRequest);
    }

    public void postRoutes(final String name, final String body, final String[] tags, final Context context,
                           Response.Listener<JSONObject> callback, Response.ErrorListener error){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            if(body != null)
                jsonObject.put("body", body);
            if (tags != null){
                JSONArray arrayTags = new JSONArray(Arrays.asList(tags));
                jsonObject.put("tags", arrayTags);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ikaro", jsonObject.toString());
        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URLs.URL_ROUTES, jsonObject, callback, error) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //Adicionar cabeçalho à requisição
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ SharedPrefManager.getInstance(context).getUser().getToken());
                return params;
            }
        };
        mQueue.add(postRequest);
    }

    public void postAddToRoutes(final String routeId, final String google_place_id,
                                final List<Local> google_places, final Context context,
                                Response.Listener<JSONObject> callback, Response.ErrorListener error) {
        List<String> lista_places_id = new ArrayList<>();
        for (int i = 0; i < google_places.size(); i++){
            lista_places_id.add(google_places.get(i).getGooglePlaceId());
        }
        //aqui é um JsonObjectRequest, dessa forma, nao preciso de Override Params
        //monto o JSONObject e envio ele na requisição
        JSONArray arrayPlaces = null;
        if (google_places != null)
            arrayPlaces = new JSONArray(lista_places_id);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("routeId", routeId);
            if(google_place_id != null)
                jsonObject.put("google_place_id", google_place_id);
            if (arrayPlaces != null)
                jsonObject.put("google_places", arrayPlaces);
            //jsonObject.
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                URLs.URL_ADD_ROUTE, jsonObject, callback, error) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //Adicionar cabeçalho à requisição
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ SharedPrefManager.getInstance(context).getUser().getToken());
                return params;
            }
        };
        mQueue.add(postRequest);
    }



}