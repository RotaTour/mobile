package br.ufrpe.projetao.rotatour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.adapters.LocaisAdapter;
import br.ufrpe.projetao.rotatour.adapters.RoutesAdapter;

import static br.ufrpe.projetao.rotatour.activities.CriarRotaActivity.mGoogleApiClient;

public class RouteActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Local> localList;
    String BASE_URL = "https://rotatourapi.herokuapp.com/api/routes/show/";
    String final_URL="";
    TextView name, description, created;
    int rota_ID;
    private LocaisAdapter locaisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        Intent intent = getIntent();
        final String routeName = intent.getStringExtra(RoutesAdapter.ROUTE_NAME);
        final String routeDescription = intent.getStringExtra(RoutesAdapter.ROUTE_DESCRIPTION);
        final String routeCreated = intent.getStringExtra(RoutesAdapter.ROUTE_CREATED);
        final String routeID= intent.getStringExtra(RoutesAdapter.ROUTE_ID);
        //rota_ID = Integer.valueOf(routeID);

        //final_URL = BASE_URL + routeID;
        //final int routeID = Integer.parseInt(intent.getStringExtra(String.valueOf(RoutesAdapter.ROUTE_ID)));


        TextView name = (TextView)findViewById(R.id.textViewDetailsRouteName);
        TextView description = (TextView)findViewById(R.id.textViewDetailsDescription);
        TextView created = (TextView)findViewById(R.id.textViewDetailsDate);

        recyclerView = (RecyclerView)findViewById(R.id.RouteDetailRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        name.setText(routeName);
        description.setText(routeDescription);
        created.setText(routeCreated);

        requestQueue = Volley.newRequestQueue(this);
       getJsonData();

    }

    public void getJsonData(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading your route...");
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"https://rotatourapi.herokuapp.com/api/routes/show/67" , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("LOCAL DETAILS", String.valueOf(response));
                try {
                    JSONObject fullJson = new JSONObject(String.valueOf(response));
                    JSONObject rota_full = fullJson.getJSONObject("route");

                    JSONArray locals_array = rota_full.getJSONArray("itens");

                    for(int i=0; i< locals_array.length();i++){

                        JSONObject local = locals_array.getJSONObject(i);

                        JSONObject google_place = local.getJSONObject("place");
                        String place_id = google_place.getString("google_place_id");
                        Log.e("DETALHE DO LOCAL>>", place_id);

                        //adicionar lógica para chamada do google places

                    }

                    //adapter = new RoutesAdapter(getApplicationContext(),routesList);
                   // recyclerView.setAdapter(adapter);

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






    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
