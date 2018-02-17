package br.ufrpe.projetao.rotatour.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.FavoritesAdapter;
import br.ufrpe.projetao.rotatour.adapters.RouteAdapter;
import me.gujun.android.taggroup.TagGroup;

public class FavoriteActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String cardName ="";
    String atividade="";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Local> localList;
    private TagGroup mTagGroup;
    private LikeButton mRouteLike;
    String BASE_URL = "https://rotatourapi.herokuapp.com/api/routes/show/"; //mover para URLS
    TextView name, description, created, activity;
    int rota_ID;
    private RouteAdapter routeAdapter;
    static GoogleApiClient mGoogleApiClient;
    Local local;
    String place_id ="";
    String FINAL_URL ="";

    public FavoriteActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

      /*  mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();*/


        localList = new ArrayList<>();
        Intent intent = getIntent();
        final String routeName = intent.getStringExtra(FavoritesAdapter.ROUTE_NAME);
        final String routeDescription = intent.getStringExtra(FavoritesAdapter.ROUTE_DESCRIPTION);
        final String routeCreated = intent.getStringExtra(FavoritesAdapter.ROUTE_CREATED);
        final String routeID= intent.getStringExtra(FavoritesAdapter.ROUTE_ID);
        //final ArrayList<String> tags = intent.getStringArrayListExtra(RoutesAdapter.ROUTE_TAGS);
        //final boolean liked = intent.getBooleanExtra(RoutesAdapter.ROUTE_LIKED, false);
        FINAL_URL = BASE_URL+routeID;

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
        adapter = new RouteAdapter(getApplicationContext(), localList);
        recyclerView.setAdapter(adapter);

    }


}
