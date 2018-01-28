package br.ufrpe.projetao.rotatour.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
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

import br.ufrpe.projetao.rotatour.Friend;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.Usuario;
import br.ufrpe.projetao.rotatour.adapters.FriendsAdapter;

public class FriendsActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    String friendName, friendUsername,friendEmail, token;
    Bitmap friendPhoto;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Friend> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        recyclerView = (RecyclerView)findViewById(R.id.FriendsRecycleViewr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendsList = new ArrayList<>();

        Usuario user = SharedPrefManager.getInstance(this).getUser();
        token = user.getToken();
        requestQueue = Volley.newRequestQueue(this);

        getfriends();

    }

    public void getfriends(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://rotatourapi.herokuapp.com/api/friends", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("result", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray friends_array = jsonObject.getJSONArray("friends");
                    Log.d("ARRAY_FRIENDS", String.valueOf(friends_array));


                    for(int i=0; i< friends_array.length();i++){
                        JSONObject f = friends_array.getJSONObject(i);
                        Log.d("FRIEND NAME",f.getString("name"));
                        String avatar = f.getString("avatar");
                        Friend friend= new Friend(f.getString("name"), f.getString("username"), f.getString("email"),avatar);
                        //friend.setPhotoreference((String) f.get("avatar"));

                        //Log.d("teste", rota.getInt("id"));
                        friendsList.add(friend);

                        //String route_name = rota.getString("name");
                        //String route_description = rota.getString("body");
                        //String created = rota.getString("created_at");

                        //String single_rota = route_name+"\n"+route_description+"\n"+created+"\n\n";
                        //rota_final = rota_final + single_rota;
                    }

                    adapter = new FriendsAdapter(getApplicationContext(),friendsList);
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

