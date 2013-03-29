package edu.Drake.doppelganger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreInfo extends Fragment {
	
@Override
public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    /*ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_more_info, container, false);
    
    boolean shouldCreateChild = getArguments().getBoolean("shouldYouCreateAChildFragment");

    if (shouldCreateChild) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        fm.beginTransaction();
        Fragment fragTwo = new MoreInfo();
        Bundle arguments = new Bundle();
        arguments.putBoolean("shouldYouCreateAChildFragment", false);
        fragTwo.setArguments(arguments);
        ft.replace(R.id.fragment_container, fragTwo);
        ft.addToBackStack(null);
        ft.commit();

    }

    return layout;
    */
	
	
        return inflater.inflate(R.layout.activity_more_info, container, false);
    
}
	
}