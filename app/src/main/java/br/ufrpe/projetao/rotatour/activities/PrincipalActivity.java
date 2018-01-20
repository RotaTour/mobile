package br.ufrpe.projetao.rotatour.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.fragments.HomeFragment;
import br.ufrpe.projetao.rotatour.fragments.MapsFragment;
import br.ufrpe.projetao.rotatour.fragments.PerfilFragment;

public class PrincipalActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener
                  , PerfilFragment.OnFragmentInteractionListener {

    private FragmentManager mFragmentManager;
    private MapsFragment mMapFrag = new MapsFragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(mFragmentManager.getFragments().get(0).getClass() != HomeFragment.class)
                        fragmentTransaction.replace(R.id.principal_frame_container, new HomeFragment(), "Home fragment");
                    findViewById(R.id.principal_card_search).setVisibility(View.INVISIBLE);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;

                case R.id.navigation_map:
                    if(mFragmentManager.getFragments().get(0).getClass() != MapsFragment.class)
                        fragmentTransaction.replace(R.id.principal_frame_container, mMapFrag, "Maps Fragment");

                    fragmentTransaction.commitAllowingStateLoss();
                    return true;

                case R.id.navigation_profile:
                    if(mFragmentManager.getFragments().get(0).getClass() != PerfilFragment.class)
                        fragmentTransaction.replace(R.id.principal_frame_container, new PerfilFragment(), "Profile Fragment");
                    findViewById(R.id.principal_card_search).setVisibility(View.INVISIBLE);

                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        findViewById(R.id.principal_card_search).setVisibility(View.INVISIBLE);

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principal_frame_container, new HomeFragment(), "Home Fragment");
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
