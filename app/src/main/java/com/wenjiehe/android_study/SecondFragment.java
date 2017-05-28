package com.wenjiehe.android_study;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("SecondFragment","oncreateview");
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        byte bb = args.getByte("1");
        Toast.makeText(getActivity(),bb+"",Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_second, container, false);
    }


    public void getData(Callback callback){
        String msg="data";
        callback.getResult(msg);
    }
    public interface  Callback{
        public void getResult(String result);
    }


}
