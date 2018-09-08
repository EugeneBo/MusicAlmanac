package eugenebo.com.github.musicalmanac.views.albumdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import eugenebo.com.github.musicalmanac.R;
import eugenebo.com.github.musicalmanac.model.Album;
import eugenebo.com.github.musicalmanac.model.Song;
import eugenebo.com.github.musicalmanac.views.Contract;
import eugenebo.com.github.musicalmanac.views.albumlist.SearchListAdapter;

public class AlbumDetailFragment extends Fragment implements Contract.View {

    private ConstraintLayout baseViewGroup;
    private Album album;
    private ProgressBar progressBar;
    private AlbumDetailAdapter adapter;
    private RecyclerView recyclerView;
    private AlbumDetailPresenter presenter;
    private List<ItemView> items = new ArrayList<>();

    public AlbumDetailFragment() {
        this.presenter = new AlbumDetailPresenter(this);
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

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        if (getArguments() != null) {
            album = (Album) getArguments().getSerializable(SearchListAdapter.ALBUM_TAG);
        }

        if (getArguments() != null) {
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(album.getCollectionName());
            }
        } else {
            displayMessage("Something goes wrong! Please, try again later.");
        }

        progressBar = view.findViewById(R.id.fragmentDetailProgressBar);
        baseViewGroup = view.findViewById(R.id.baseDetailFragmentConstrainLayout);
        adapter = new AlbumDetailAdapter(getActivity(), items);

        recyclerView = view.findViewById(R.id.songsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            loadDetailedAlbumInfo(album.getCollectionId());
        }

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadDetailedAlbumInfo(final Integer id) {
        recyclerView.setVisibility(View.VISIBLE);
        items.clear();
        adapter.notifyDataSetChanged();
        showProgressBar(true);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                presenter.getData(id);
            }
        });
    }

    @Override
    public void displayMessage(String message) {
        Snackbar.make(baseViewGroup, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public <T> void displayData(List<T> data) {
        List<Song> songs = (ArrayList<Song>) data;

        this.items.clear();
        this.items.add(album);

        //Removing 0 element that contain reference info about album provided by iTunes API
        //which we don't need in adapter.
        //All album info we take from fragment args.
        songs.remove(0);

        this.items.addAll(songs);
        showProgressBar(false);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showProgressBar(boolean isLoading) {
        if (isLoading) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }
}