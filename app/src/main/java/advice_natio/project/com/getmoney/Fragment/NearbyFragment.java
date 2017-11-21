package advice_natio.project.com.getmoney.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import advice_natio.project.com.getmoney.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearbyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NearbyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            mListener = (OnFragmentInteractionListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
