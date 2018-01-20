package br.ufrpe.projetao.rotatour.activities;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import static br.ufrpe.projetao.rotatour.requests_volley.URLs.URL_ROUTES;
import static java.security.AccessController.getContext;

public class RoutesActivity extends AppCompatActivity {

    Button getJson;
    TextView jsonData;
    RequestQueue requestQueue;
    String routeName;
    String routeDescription;
    String token;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Routes> routesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        recyclerView = (RecyclerView)findViewById(R.id.RoutesRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        routesList = new ArrayList<>();

        Usuario user = SharedPrefManager.getInstance(this).getUser();
        token = user.getToken();

        //getJson = (Button) findViewById(R.id.buttonJson);
        //jsonData = (TextView) findViewById(R.id.textViewRoutes);
        requestQueue = Volley.newRequestQueue(this);

        getJsonData();
    }

    public void getJsonData(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://rotatourapi.herokuapp.com/api/routes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("result", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray rotas_array = jsonObject.getJSONArray("routes");

                    String rota_final = "";
                    for(int i=0; i< rotas_array.length();i++){
                        JSONObject rota = rotas_array.getJSONObject(i);

                        Routes route = new Routes(rota.getString("name"), rota.getString("body"), rota.getString("created_at"));
                        routesList.add(route);

                        //String route_name = rota.getString("name");
                        //String route_description = rota.getString("body");
                        //String created = rota.getString("created_at");

                        //String single_rota = route_name+"\n"+route_description+"\n"+created+"\n\n";
                        //rota_final = rota_final + single_rota;
                    }

                    adapter = new RoutesAdapter(getApplicationContext(),routesList);
                    recyclerView.setAdapter(adapter);

                    //jsonData.setText(rota_final);

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
