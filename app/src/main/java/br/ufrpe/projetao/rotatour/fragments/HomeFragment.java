package br.ufrpe.projetao.rotatour.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.projetao.rotatour.Local;
import br.ufrpe.projetao.rotatour.Pub;
import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.adapters.LocaisAdapter;
import br.ufrpe.projetao.rotatour.adapters.PubsAdapter;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;

public class HomeFragment extends Fragment {

    private EditText mTxtBusca;
    private OnFragmentInteractionListener mListener;

    private List<Pub> mListaPubs;
    private RecyclerView mRvLista;
    private PubsAdapter mPubsAdapter;


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
        mTxtBusca = v.findViewById(R.id.home_search);
        mRvLista = v.findViewById(R.id.home_rvPubs);
        mListaPubs = new ArrayList<>();

        LinearLayoutManager lln = new LinearLayoutManager(getContext());
        lln.setOrientation(LinearLayoutManager.VERTICAL);
        mRvLista.setLayoutManager(lln);

        mPubsAdapter = new PubsAdapter(getContext(), mListaPubs);

        mRvLista.setAdapter(mPubsAdapter);

        carregarPubs();

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
        VolleySingleton.getInstance(getContext()).getPubs(getContext(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray pubs = null;
                        try {
                            pubs = response.getJSONArray("statuses");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i =0; i< pubs.length(); i++) {
                            Pub pub = null;
                            JSONObject pubAtual = null;
                            try {
                                pubAtual = pubs.getJSONObject(i);
                                pub = new Pub("name",
                                        pubAtual.getString("created_at"),
                                        pubAtual.getString("body"),
                                        null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mListaPubs.add(pub);
                            mPubsAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Erro get pubs", Toast.LENGTH_SHORT).show();

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
