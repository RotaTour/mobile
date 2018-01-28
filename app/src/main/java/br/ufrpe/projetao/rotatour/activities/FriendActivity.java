package br.ufrpe.projetao.rotatour.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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
import br.ufrpe.projetao.rotatour.adapters.FriendsAdapter;
import br.ufrpe.projetao.rotatour.adapters.RouteAdapter;
import br.ufrpe.projetao.rotatour.adapters.RoutesAdapter;

public class FriendActivity extends AppCompatActivity {

    private Context mContext;
    ImageView photo;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Routes> routesList;
    RequestQueue requestQueue;
    String BASE_URL = "https://rotatourapi.herokuapp.com/api/users/";
    String url ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);


        Intent intent = getIntent();
        final String friendName = intent.getStringExtra(FriendsAdapter.FRIEND_NAME);
        final String friendUsername = intent.getStringExtra(FriendsAdapter.FRIEND_USERNAME);
        final String friendEmail = intent.getStringExtra(FriendsAdapter.FRIEND_EMAIL);
        final String friendAvatar = intent.getStringExtra(FriendsAdapter.FRIEND_AVATAR);

       TextView fname = (TextView)findViewById(R.id.textViewDetailsFriendName);
       TextView fUname = (TextView)findViewById(R.id.textViewDetailsFriendUsername);
       TextView fEmail = (TextView)findViewById(R.id.textViewDetailsFriendEmail);
       photo = (ImageView)findViewById(R.id.friendProfilePhoto);

        routesList = new ArrayList<>();

       Picasso.with(mContext).load(friendAvatar).into(photo);
       fname.setText(friendName);
       fUname.setText(friendUsername);
       fEmail.setText(friendEmail);

        recyclerView = (RecyclerView)findViewById(R.id.FriendDetailRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);

        url = BASE_URL+friendUsername+"/routes";
        getRoutes();

    }

    public void getRoutes(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("result", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray rotas_array = jsonObject.getJSONArray("routes");
                    Log.d("ARRAY DE ROTAS", String.valueOf(rotas_array));


                    for(int i=rotas_array.length()-1; i>=0 ;i--){
                        JSONObject rota = rotas_array.getJSONObject(i);
                        String data = rota.getString("created_at");
                        String ano = data.substring(0,4);
                        String mes = data.substring(5,7);
                        String dia = data.substring(8,10);
                        data = dia + "/" + mes + "/" + ano;

                        Routes route = new Routes(rota.getString("name"), rota.getString("body"), data, rota.getInt("id"));
                        routesList.add(route);

                    }
                    adapter = new RoutesAdapter(getApplicationContext(),routesList);
                    recyclerView.setAdapter(adapter);

                    //Log.d("TOKEN", token);


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
