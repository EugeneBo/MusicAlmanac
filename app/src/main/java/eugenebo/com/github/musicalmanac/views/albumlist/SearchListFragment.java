package eugenebo.com.github.musicalmanac.views.albumlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import eugenebo.com.github.musicalmanac.R;
import eugenebo.com.github.musicalmanac.model.Album;
import eugenebo.com.github.musicalmanac.views.Contract;

public class SearchListFragment extends Fragment implements Contract.View {

    private static boolean searchInitialized = false;
    private ConstraintLayout startupTextViews;
    private ConstraintLayout baseViewGroup;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Album> albums = new ArrayList<>();
    private SearchListAdapter adapter;
    private SearchView searchView = null;
    private SearchListPresenter presenter;

    public SearchListFragment() {
        presenter = new SearchListPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Music Almanac");

        baseViewGroup = view.findViewById(R.id.baseSearchFragmentConstrainLayout);
        startupTextViews = view.findViewById(R.id.constraintForText);

        progressBar = view.findViewById(R.id.searchFragmentProgressBar);
        recyclerView = view.findViewById(R.id.listSongs);
        adapter = new SearchListAdapter(getActivity(), albums);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if (searchInitialized) startupTextViews.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint("Search albums...");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.length() != 0) search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                 /*   The Search API is limited to approximately 20 calls per minute
                      so be careful with this option.
                      It is desirable to process queries with a delay
                      when the user enters the query.
                  */
                    return false;
                }
            });

        }
    }

    public void search(final String strTerm) {
        searchInitialized = true;
        startupTextViews.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        albums.clear();
        adapter.notifyDataSetChanged();
        showProgressBar(true);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                presenter.getData(strTerm);
            }
        });
    }

    @Override
    public <T> void displayData(List<T> dataAlbums) {
        List<Album> albums = (ArrayList<Album>) dataAlbums;
        showProgressBar(false);
        this.albums.clear();
        this.albums.addAll(albums);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayMessage(String message) {
        showProgressBar(false);
        Snackbar.make(baseViewGroup, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar(boolean isLoading) {
        if (isLoading) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchInitialized = false;
    }
}