package eugenebo.com.github.musicalmanac.views.albumlist;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eugenebo.com.github.musicalmanac.R;
import eugenebo.com.github.musicalmanac.model.Album;
import eugenebo.com.github.musicalmanac.views.Contract;

import static android.content.Context.SEARCH_SERVICE;

public class SearchListFragment extends Fragment implements Contract.View {

    private ConstraintLayout baseViewGroup;
    private TextView onStartupText;
    private TextView onStartupText2;
    private TextView onStartupText3;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Album> albums = new ArrayList<>();
    private SearchAdapter adapter;
    private SearchView searchView = null;
    private SearchListPresenter presenter;

    public SearchListFragment() {
        presenter = new SearchListPresenter(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        baseViewGroup = view.findViewById(R.id.baseSearchFragmentConstrainLayout);
        onStartupText = view.findViewById(R.id.onStartupText);
        onStartupText2 = view.findViewById(R.id.onStartupText2);
        onStartupText3 = view.findViewById(R.id.onStartupText3);
        progressBar = view.findViewById(R.id.searchFragmentProgressBar);
        recyclerView = view.findViewById(R.id.listSongs);
        adapter = new SearchAdapter(getActivity(), albums);

        progressBar.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Search albums...");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        }
    }

    public void search(final String strTerm) {
        onStartupText.setVisibility(View.GONE);
        onStartupText2.setVisibility(View.GONE);
        onStartupText3.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        albums.clear();
        adapter.notifyDataSetChanged();

        setLoadingIndicator(true);
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
        setLoadingIndicator(false);
        this.albums.clear();
        this.albums.addAll(albums);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(baseViewGroup, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }
}