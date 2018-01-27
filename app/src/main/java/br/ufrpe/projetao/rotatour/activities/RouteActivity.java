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
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.Routes;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.adapters.LocaisAdapter;
import br.ufrpe.projetao.rotatour.adapters.RouteAdapter;
import br.ufrpe.projetao.rotatour.adapters.RoutesAdapter;

import static br.ufrpe.projetao.rotatour.activities.CriarRotaActivity.mGoogleApiClient;

public class RouteActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    RequestQueue requestQueue;
    String cardName ="";
    String atividade="";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Local> localList;
    String BASE_URL = "https://rotatourapi.herokuapp.com/api/routes/show/"; //mover para URLS
    TextView name, description, created, activity;
    int rota_ID;
    private RouteAdapter routeAdapter;
    static GoogleApiClient mGoogleApiClient;
    Local local;
    String place_id ="";
    String FINAL_URL ="";

    public RouteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        localList = new ArrayList<>();
        Intent intent = getIntent();
        final String routeName = intent.getStringExtra(RoutesAdapter.ROUTE_NAME);
        final String routeDescription = intent.getStringExtra(RoutesAdapter.ROUTE_DESCRIPTION);
        final String routeCreated = intent.getStringExtra(RoutesAdapter.ROUTE_CREATED);
        final String routeID= intent.getStringExtra(RoutesAdapter.ROUTE_ID);
        FINAL_URL = BASE_URL+routeID;



        TextView name = (TextView)findViewById(R.id.textViewDetailsRouteName);
        TextView description = (TextView)findViewById(R.id.textViewDetailsDescription);
        TextView created = (TextView)findViewById(R.id.textViewDetailsDate);
        //TextView activity = (TextView)findViewById(R.id.place_atividade);
        //activity.setText("");

        recyclerView = (RecyclerView)findViewById(R.id.RouteDetailRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        name.setText(routeName);
        description.setText(routeDescription);
        created.setText(routeCreated);

        requestQueue = Volley.newRequestQueue(this);
        adapter = new RouteAdapter(getApplicationContext(), localList);
        recyclerView.setAdapter(adapter);
        loadRoute();
    }


    public void loadRoute(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading your route...");
        progressDialog.show();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("LOCAL DETAILS", String.valueOf(response));

                try {

                    //recuperacao do array contendo locais
                    JSONObject fullJson = new JSONObject(String.valueOf(response));
                    JSONObject rota_full = fullJson.getJSONObject("route");
                    JSONArray locals_array = rota_full.getJSONArray("itens");


                    //recuperando google place Id para cada local da rota
                    for(int i=0; i< locals_array.length();i++){

                        JSONObject localObj= locals_array.getJSONObject(i);
                        JSONObject google_place = localObj.getJSONObject("place");
                        final String place_id = google_place.getString("google_place_id");


                        //realizando chamada assíncrona para o google e criando um objeto places a partir do google id
                        Places.GeoDataApi.getPlaceById(mGoogleApiClient, place_id)
                                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                    @Override
                                    public void onResult(PlaceBuffer places) {
                                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                            final Place myPlace = places.get(0);
                                            cardName = (String) myPlace.getName();

                                            local = new Local(cardName,atividade,place_id, null);
                                            localList.add(local);
                                            adapter.notifyDataSetChanged();

                                            //new PhotoTask((RouteActivity) getApplicationContext()).execute(myPlace.getId());
                                        } else {
                                            Log.e("PLACEE>> ", "Place not found");
                                        }
                                        places.release();
                                    }
                                });

                    }

                    Log.d("out_loop::", String.valueOf(localList.size()));


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


    // get photo
    static class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributedPhoto> {
        WeakReference<RouteActivity> activityReference;

        PhotoTask(RouteActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            Log.d("PHOTOS>","GOING!");
        }

        @Override
        protected void onPostExecute(RouteActivity.PhotoTask.AttributedPhoto attributedPhoto) {
            if (attributedPhoto != null) {

                Log.e("PHOTOS>","PHOTO LOADED!");
                // Photo has been loaded, display it
                activityReference.get().local.setImagem(attributedPhoto.bitmap);
                activityReference.get().adapter.notifyDataSetChanged();
            }
        }

        /**
         * Loads the first photo for a place id from the Geo Data API.
         * The place id must be the first (and only) parameter.
         */
        @Override
        protected RouteActivity.PhotoTask.AttributedPhoto doInBackground(String... params) {
            if (params.length != 1) {
                return null;
            }
            final String placeId = params[0];
            RouteActivity.PhotoTask.AttributedPhoto attributedPhoto = null;

            PlacePhotoMetadataResult result = Places.GeoDataApi
                    .getPlacePhotos(mGoogleApiClient, placeId).await();

            if (result.getStatus().isSuccess()) {
                PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
                if (photoMetadataBuffer.getCount() > 0 && !isCancelled()) {
                    // Get the first bitmap and its attributions.
                    PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                    CharSequence attribution = photo.getAttributions();
                    // Load a scaled bitmap for this photo.
                    Bitmap image = photo.getPhoto(mGoogleApiClient).await()
                            .getBitmap();

                    attributedPhoto = new AttributedPhoto(attribution, image);
                }
                // Release the PlacePhotoMetadataBuffer.
                photoMetadataBuffer.release();
            }
            return attributedPhoto;
        }

        /**
         * Holder for an image and its attribution.
         */
        class AttributedPhoto {

            final CharSequence attribution;

            final Bitmap bitmap;

            AttributedPhoto(CharSequence attribution, Bitmap bitmap) {
                this.attribution = attribution;
                this.bitmap = bitmap;
            }
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
