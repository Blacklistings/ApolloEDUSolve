package com.example.apolloedusolve.account_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apolloedusolve.Articles;
import com.example.apolloedusolve.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ResourcesFragment extends Fragment {
    private TabLayout tabLayout;
    private TextView resourceText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resources, container, false);
        tabLayout = root.findViewById(R.id.resources_tab);
        resourceText = root.findViewById(R.id.resource_text);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        //article 2
                        resourceText.setText(Articles.ARTICLE1);
                        break;
                    case 1:
                        resourceText.setText(Articles.ARTICLE2);
                        //article 1
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        resourceText.setText(Articles.ARTICLE1);
        return root;
    }
}