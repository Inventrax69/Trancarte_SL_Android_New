package com.inventrax.falconsl_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inventrax.falconsl_new.R;
import com.inventrax.falconsl_new.util.FragmentUtils;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    Fragment fragment = null;

    LinearLayout ll_receive, ll_putaway, ll_picking, ll_VLPDPicking;
    LinearLayout  ll_houseKeeping, ll_cycleCount, ll_livestock;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home,container,false);
        loadFormControls();


        return rootView;
    }

    private void loadFormControls() {

        ll_receive = (LinearLayout) rootView.findViewById(R.id.ll_receive);
        ll_putaway = (LinearLayout) rootView.findViewById(R.id.ll_putaway);
        ll_picking = (LinearLayout) rootView.findViewById(R.id.ll_picking);
        ll_VLPDPicking = (LinearLayout) rootView.findViewById(R.id.ll_VLPDPicking);
        ll_houseKeeping = (LinearLayout) rootView.findViewById(R.id.ll_houseKeeping);
        ll_cycleCount = (LinearLayout) rootView.findViewById(R.id.ll_cycleCount);
        ll_livestock = (LinearLayout) rootView.findViewById(R.id.ll_livestock);

        ll_receive.setOnClickListener(this);
        ll_putaway.setOnClickListener(this);
        ll_picking.setOnClickListener(this);

        ll_VLPDPicking.setOnClickListener(this);

        ll_houseKeeping.setOnClickListener(this);
        ll_cycleCount.setOnClickListener(this);

        ll_livestock.setOnClickListener(this);
    }



    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_receive:
                UnloadingFragment unloadingFragment = new UnloadingFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, unloadingFragment);
                break;

            case R.id.ll_putaway:
                PutawayFragment putAwayFragment = new PutawayFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, putAwayFragment);
                break;

            case R.id.ll_picking:
                OBDPickingHeaderFragment obdPickingHeaderFragment = new OBDPickingHeaderFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, obdPickingHeaderFragment);
                break;

            case R.id.ll_VLPDPicking:
                VLPDPickingHeaderFragment vlpdPickingHeaderFragment = new VLPDPickingHeaderFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, vlpdPickingHeaderFragment);
                break;


            case R.id.ll_houseKeeping:
                InternalTransferFragment internalTransferheaderFragment = new InternalTransferFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, internalTransferheaderFragment);
                break;

            case R.id.ll_cycleCount:
                CycleCountHeaderFragment cycleCountHeaderFragment = new CycleCountHeaderFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, cycleCountHeaderFragment);
                break;


            case R.id.ll_livestock:
                LiveStockFragment liveStockFragment_ = new LiveStockFragment();
                FragmentUtils.replaceFragmentWithBackStack(getActivity(), R.id.container_body, liveStockFragment_);
                break;
        }
    }


}