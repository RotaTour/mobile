package br.ufrpe.projetao.rotatour;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

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

    void getObject(String url, Response.Listener<JSONObject> callback, Response.ErrorListener error) {
        url = removerAcentos(url);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, callback, error);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    //TODO HEADERS
    void getObjectWithHeader(String url, Response.Listener<JSONObject> callback, Response.ErrorListener error, final String email, final String password) {
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
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    void postLogin(String url, Response.Listener<String> callback, Response.ErrorListener error, final String email, final String password) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, url, callback, error) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        mQueue.add(postRequest);
    }

    void postRegister(String url, Response.Listener<String> callback, Response.ErrorListener error, final String nome, final String email, final String password) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, url, callback, error) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", nome);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        mQueue.add(postRequest);
    }
}