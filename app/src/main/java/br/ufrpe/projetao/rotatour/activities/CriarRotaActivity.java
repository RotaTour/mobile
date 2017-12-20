package br.ufrpe.projetao.rotatour.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.LocaisAdapter;
import me.gujun.android.taggroup.TagGroup;

public class CriarRotaActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    //private Button mAddLocal;
    private TagGroup mTagGroup;
    private FloatingActionButton mBtnAdicionarLocal;
    private List<Local> mListaLocais;
    private RecyclerView mRvLista;
    private LocaisAdapter mLocaisAdapter;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_rota);

        Toolbar myToolbar = findViewById(R.id.criarRota_toolbar);
        setSupportActionBar(myToolbar);
        mListaLocais = new ArrayList<>();
        mRvLista = findViewById(R.id.criarRota_rv_lista);
        mRvLista.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LinearLayoutManager lln = (LinearLayoutManager) mRvLista.getLayoutManager();
                LocaisAdapter adp = (LocaisAdapter) mRvLista.getAdapter();

                if(mListaLocais.size() == lln.findLastCompletelyVisibleItemPosition()+1){
                    //TODO TRATAR QTD DA LISTA = QTD DA EXIBIÇÃO
                }
            }
        });
        mRvLista.setHasFixedSize(true);
        LinearLayoutManager lln = new LinearLayoutManager(this);
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        mRvLista.setLayoutManager(lln);

        mLocaisAdapter= new LocaisAdapter(this, mListaLocais);
        mRvLista.setAdapter(mLocaisAdapter);

        mLinearLayout = findViewById(R.id.criarRota_layout_pai);
        //mAddLocal = findViewById(R.id.criarRota_button_novoItem);
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
        /*mAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adicionar novos dinamicos campos de atividade e local para a Rota
                LinearLayout ll = new LinearLayout(v.getContext());

                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.HORIZONTAL);

                EditText etAtv = new EditText(v.getContext());
                etAtv.setId(i);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT, 2);
                etAtv.setLayoutParams(lp);
                etAtv.setHint(getString(R.string.criarRota_activity));

                EditText etLoc = new EditText(v.getContext());
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 3);
                etLoc.setLayoutParams(lp2);
                etLoc.setHint(getString(R.string.criarRota_place));

                i++;

                ll.addView(etAtv);
                ll.addView(etLoc);
                mLinearLayout.addView(ll, 4);
            }
        });*/
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.criar_rota_menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_done:
                // TODO do something
                Toast.makeText(getApplication(), "Tags: " + mTagGroup.getTags(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Aqui pega o local que foi selecionado na pesquisa do Google Places e adiciona a lista de locais
                Place place = PlaceAutocomplete.getPlace(this, data);
                mListaLocais.add(new Local(place.getName().toString(), "atividade", 1));
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

}