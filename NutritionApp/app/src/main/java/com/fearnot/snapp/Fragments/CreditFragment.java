package com.fearnot.snapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fearnot.snapp.Interfaces.ReplaceFragmentInterface;
import com.fearnot.snapp.MainActivity;
import com.fearnot.snapp.NutritionSingleton;
import com.fearnot.snapp.R;
import com.fearnot.snapp.Views.CheckableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class CreditFragment extends Fragment {
    private Drawer result;
    private ReplaceFragmentInterface replaceFragmentInterface;

    public CreditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_credit, container, false);

        // SIDE MENU

        // Drawer Items
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_profile);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_graphs);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_credits);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_logout);
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.wallpaperandroid50)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(NutritionSingleton.getInstance().getCurrProfile().getName())
                                .withIcon(getResources().getDrawable(CheckableImageView.mOriginalIds[(int) NutritionSingleton.getInstance().getCurrProfile().getImagePos()]))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        // Actual Drawer
        result = new DrawerBuilder()
                .withActivity(getActivity())
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item,
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            replaceFragmentInterface.replaceFragment(new HomeFragment());
                            close();
                        } else if (position == 2) {
                            replaceFragmentInterface.replaceFragment(new ProfileFragment());
                            close();
                        } else if (position == 3) {
                            replaceFragmentInterface.replaceFragment(new ProgressFragment());
                            close();
                        } else if (position == 4) {
                            replaceFragmentInterface.replaceFragment(new CreditFragment());
                            close();
                        } else if (position == 6) {
                            // logout
                            FirebaseAuth.getInstance().signOut();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        }
                        return true;
                    }
                })
                .build();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar1);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_2x);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationOnClickListener(new Toolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            replaceFragmentInterface = (ReplaceFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReplaceFragmentInterface");
        }
    }

    private void close() {
        result.closeDrawer();
    }

}
