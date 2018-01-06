package br.ufrpe.projetao.rotatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.LocaisAdapter;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;
import me.gujun.android.taggroup.TagGroup;

public class CriarRotaActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TagGroup mTagGroup;
    private FloatingActionButton mBtnAdicionarLocal;
    private List<Local> mListaLocais;
    private RecyclerView mRvLista;
    private LocaisAdapter mLocaisAdapter;
    private EditText mEdtNomeRota;
    private EditText mEdtDescricao;
    static GoogleApiClient mGoogleApiClient;
    Local local = null;
    private int mRotaId;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_rota);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        mEdtNomeRota = findViewById(R.id.criarRota_edit_nome);
        mEdtDescricao = findViewById(R.id.criarRota_edit_descricao);

        Toolbar myToolbar = findViewById(R.id.criarRota_toolbar);
        setSupportActionBar(myToolbar);
        mListaLocais = new ArrayList<>();
        mRvLista = findViewById(R.id.criarRota_rv_lista);

        mRvLista.setHasFixedSize(true);
        LinearLayoutManager lln = new LinearLayoutManager(this);
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        mRvLista.setLayoutManager(lln);

        mLocaisAdapter= new LocaisAdapter(this, mListaLocais);
        mRvLista.setAdapter(mLocaisAdapter);

        mBtnAdicionarLocal = findViewById(R.id.criarRota_button_novoLocal);
        mTagGroup = findViewById(R.id.criarRota_tag_group);
        final Activity criarRota = this;
        mBtnAdicionarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(criarRota);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Solucionar o erro.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Solucionar o erro.
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.criar_rota_menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_done:
                // salvar rota
                VolleySingleton.getInstance(this).postRoutes(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    mRotaId = Integer.valueOf(response.getJSONObject("route").getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //NOVA REQUISIÇÃO ANINHADA (pois depende do sucesso da anterior)- ADICIONAR LOCAIS A ROTA CRIADA
                                VolleySingleton.getInstance(CriarRotaActivity.this).postAddToRoutes(
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Toast.makeText(CriarRotaActivity.this, R.string.criarRota_rotaSalva, Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(CriarRotaActivity.this, "Erro addToRoutes", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        String.valueOf(mRotaId), null , mListaLocais, CriarRotaActivity.this);
                            }
                        },
                        new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}},
                        mEdtNomeRota.getText().toString(),mEdtDescricao.getText().toString(),this); //parametros da requisição
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //Aqui pega o local que foi selecionado na pesquisa do Google Places e adiciona a lista de locais
                final Place place = PlaceAutocomplete.getPlace(this, data);

                //pegar foto do local
                local = new Local(place.getName().toString(), "atividade", place.getId(), null);
                new PhotoTask(this).execute(place.getId());

                mListaLocais.add(local);
                mLocaisAdapter.notifyDataSetChanged();

                //esconder texto de ajuda quando adicionar locais.
                TextView tvMais = findViewById(R.id.criarRota_tv_mais);
                if(tvMais.getVisibility() == View.VISIBLE)
                    tvMais.setVisibility(View.INVISIBLE);
                //Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Solucionar o erro.
                //Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    //conection fail Google API
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    static class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributedPhoto> {
        WeakReference<CriarRotaActivity> activityReference;

        PhotoTask(CriarRotaActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(AttributedPhoto attributedPhoto) {
            if (attributedPhoto != null) {
                // Photo has been loaded, display it.
                activityReference.get().local.setImagem(attributedPhoto.bitmap);
                activityReference.get().mLocaisAdapter.notifyDataSetChanged();
            }
        }

        /**
         * Loads the first photo for a place id from the Geo Data API.
         * The place id must be the first (and only) parameter.
         */
        @Override
        protected AttributedPhoto doInBackground(String... params) {
            if (params.length != 1) {
                return null;
            }
            final String placeId = params[0];
            AttributedPhoto attributedPhoto = null;

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

}