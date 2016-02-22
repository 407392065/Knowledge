package com.dante.knowledge.mvp.view;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.dante.knowledge.R;
import com.dante.knowledge.ui.BaseFragment;
import com.dante.knowledge.utils.Constants;
import com.dante.knowledge.utils.SP;

import butterknife.Bind;


/**
 * All fragments have recyclerView & swipeRefresh must implement this.
 */
public abstract class RecyclerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.list)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    boolean isFirst = true;   //whether is first time to enter fragment
    int type;               // type of recyclerView's content
    int lastPosition;       //last visible position
    int firstPosition;      //first visible position

    @Override
    public void onResume() {
        super.onResume();
        //restoring position when reentering fragment.
        lastPosition = SP.getInt(type + Constants.POSITION);
        if (lastPosition!=0){
            recyclerView.scrollToPosition(lastPosition);
        }
    }

    @Override
    public void onDestroyView() {
        SP.save(type + Constants.POSITION, firstPosition);
        super.onDestroyView();
    }

    @Override
    protected void initViews() {
        recyclerView.setHasFixedSize(true);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);
    }
    public void changeProgress(final boolean refreshState) {
        if (null != swipeRefresh) {
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(refreshState);
                }
            });
        }}

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
