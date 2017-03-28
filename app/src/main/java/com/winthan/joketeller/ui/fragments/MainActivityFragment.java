package com.winthan.joketeller.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winthan.joketeller.R;
import com.winthan.joketeller.clicklistener.ItemClickListener;
import com.winthan.joketeller.data.database.JokeContract;
import com.winthan.joketeller.data.models.JokeModel;
import com.winthan.joketeller.data.network.ApiRetrofit;
import com.winthan.joketeller.data.vos.JokeVO;
import com.winthan.joketeller.events.DataEvent;
import com.winthan.joketeller.ui.adapters.JokeRvAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int JOKE_LOADER = 1000;

    @BindView(R.id.rv_jokes)
    RecyclerView mRvJokes;

    @BindView(R.id.joke_swipefresh)
    SwipeRefreshLayout mJokeSwipeRefresh;

    private JokeRvAdapter mAdapter;

    private ItemClickListener itemClickListener;

    public MainActivityFragment() {
    }


    public static MainActivityFragment newInstance() {
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        return mainActivityFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        itemClickListener = (ItemClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JokeModel.getInstance();
        mAdapter = new JokeRvAdapter(getActivity(), itemClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootview);

        mRvJokes.setHasFixedSize(true);
        mRvJokes.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvJokes.setAdapter(mAdapter);

        mJokeSwipeRefresh.setRefreshing(true);

        mJokeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiRetrofit.getInstance().loadJokes();
            }
        });

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(JOKE_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDataLoaded(DataEvent.JokeDataLoadedEvent jokeDataLoadedEvent) {
        mJokeSwipeRefresh.setRefreshing(false);
        Log.i("test"," test");
        mAdapter.addJokes(jokeDataLoadedEvent.getJokeVOList());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                JokeContract.JokeEntity.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mJokeSwipeRefresh.setRefreshing(false);

        List<JokeVO> jokeVOList = new ArrayList<>();

        if (data != null && data.moveToFirst()){

            do {

                JokeVO jokeVO = JokeVO.parseFromCursor(data);
                jokeVOList.add(jokeVO);

            }while (data.moveToNext());

            mAdapter.addJokes(jokeVOList);
            JokeModel.getInstance().setStoredData(jokeVOList);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
