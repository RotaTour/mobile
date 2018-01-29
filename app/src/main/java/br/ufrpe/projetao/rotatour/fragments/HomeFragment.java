package br.ufrpe.projetao.rotatour.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Pub;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.activities.SearchActivity;
import br.ufrpe.projetao.rotatour.adapters.PubsAdapter;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;

public class HomeFragment extends Fragment {

    private EditText mTxtBusca;
    private EditText mTxtNewPost;
    private ImageButton mBtnNewPost;
    private ImageButton mBtnSearch;
    private OnFragmentInteractionListener mListener;

    private List<Pub> mListaPubs;
    private RecyclerView mRvLista;
    private PubsAdapter mPubsAdapter;

    public static  final String SEARCH_STRING = "Rota";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mTxtNewPost = v.findViewById(R.id.home_etNewPub);
        mBtnNewPost = v.findViewById(R.id.home_btnNewPost);
        mTxtBusca = v.findViewById(R.id.home_search);
        mBtnSearch = v.findViewById(R.id.home_btnSearch);
        mRvLista = v.findViewById(R.id.home_rvPubs);
        mListaPubs = new ArrayList<>();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LinearLayoutManager lln = new LinearLayoutManager(getContext());
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        mRvLista.setLayoutManager(lln);

        mPubsAdapter = new PubsAdapter(getContext(), mListaPubs);

        mRvLista.setAdapter(mPubsAdapter);

        carregarPubs();

        mBtnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPub = mTxtNewPost.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(getString(R.string.home_loadPubs));
                progressDialog.show();

                VolleySingleton.getInstance(getContext()).postPub(getContext(), newPub,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                mTxtNewPost.setText("");
                                mPubsAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                carregarPubs();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.d("vtnc", "Erro postPub");
                            }
                        });
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mTxtBusca.getText().toString();
                Intent i= new Intent(v.getContext(), SearchActivity.class);
                i.putExtra(SEARCH_STRING,search);
                startActivity(i);
            }
        });
        mTxtBusca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO buscar aqui
                    return true;
                }
                return false;
            }
        });
        return v;
    }

    private void carregarPubs() {
        mListaPubs = new ArrayList<>();
        mPubsAdapter = new PubsAdapter(getContext(), mListaPubs);
        mRvLista.setAdapter(mPubsAdapter);

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.home_loadPubs));
        progressDialog.show();

        VolleySingleton.getInstance(getContext()).getPubs(getContext(),
                "0", "0", "0", "-1", "-1",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray pubs = null;
                        try {
                            pubs = response.getJSONArray("posts");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(pubs.length() >= 1) {
                            for (int i = 0; i < pubs.length(); i++) {
                                Pub pub = null;
                                JSONObject pubAtual = null;
                                try {
                                    pubAtual = pubs.getJSONObject(i);
                                    String data = pubAtual.getString("created_at");
                                    String ano = data.substring(0, 4);
                                    String mes = data.substring(5, 7);
                                    String dia = data.substring(8, 10);
                                    data = dia + "/" + mes + "/" + ano;
                                    String avatar = pubAtual.getJSONObject("user").getString("avatar");
                                    String user = pubAtual.getJSONObject("user").getString("name");
                                    long id = Long.valueOf(pubAtual.getString("id"));
                                    boolean liked = pubAtual.getBoolean("liked");
                                    pub = new Pub(id, liked, user, data,
                                            pubAtual.getString("body"), avatar);

                                    mListaPubs.add(pub);
                                    mPubsAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Erro get pubs", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
