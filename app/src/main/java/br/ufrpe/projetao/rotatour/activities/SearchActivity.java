package br.ufrpe.projetao.rotatour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.Usuario;
import br.ufrpe.projetao.rotatour.adapters.RoutesAdapter;
import br.ufrpe.projetao.rotatour.fragments.HomeFragment;

public class SearchActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Routes> routesList;
    String token;
    String BASE_URL = "https://rotatourapi.herokuapp.com/api/search?query=";
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        final String searchString= intent.getStringExtra(HomeFragment.SEARCH_STRING);
        url = BASE_URL + searchString;

        recyclerView = (RecyclerView)findViewById(R.id.SearchRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        routesList = new ArrayList<>();

        Usuario user = SharedPrefManager.getInstance(this).getUser();
        token = user.getToken();

        requestQueue = Volley.newRequestQueue(this);
        getRoutes();
    }


    public void getRoutes(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("result", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray rotas_array = jsonObject.getJSONArray("routes");

                    String rota_final = "";
                    for(int i=rotas_array.length()-1; i>=0 ;i--){
                        JSONObject rota = rotas_array.getJSONObject(i);
                        String data = rota.getString("created_at");
                        String ano = data.substring(0,4);
                        String mes = data.substring(5,7);
                        String dia = data.substring(8,10);
                        data = dia + "/" + mes + "/" + ano;

                        ArrayList<String> tags = new ArrayList<>();
                        JSONArray jTags = rota.getJSONArray("tags");
                        for (int j = 0; j < jTags.length(); j++) {
                            tags.add(jTags.getJSONObject(j).getString("name")) ;
                        }

                        boolean liked = rota.getBoolean("liked");
                        Routes route = new Routes(rota.getString("name"), rota.getString("body"), data, rota.getInt("id"), tags,liked);
                        routesList.add(route);

                    }

                    adapter = new RoutesAdapter(getApplicationContext(),routesList);
                    recyclerView.setAdapter(adapter);
                    Log.d("TOKEN", token);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", String.valueOf(error));

            }
        }
        ){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getToken());

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }



}
