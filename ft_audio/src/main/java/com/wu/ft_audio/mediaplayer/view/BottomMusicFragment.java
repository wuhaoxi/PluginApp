package com.wu.ft_audio.mediaplayer.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qihoo360.replugin.RePlugin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * BottomMusicView的一个Fragment包装
 */
public class BottomMusicFragment extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = RePlugin.getPluginContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = new BottomMusicView(mContext);
        return rootView;
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }
}

