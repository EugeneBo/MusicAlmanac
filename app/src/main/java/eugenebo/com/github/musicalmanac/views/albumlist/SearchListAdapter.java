package eugenebo.com.github.musicalmanac.views.albumlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import eugenebo.com.github.musicalmanac.R;
import eugenebo.com.github.musicalmanac.model.Album;
import eugenebo.com.github.musicalmanac.views.albumdetail.AlbumDetailFragment;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.AlbumsListViewHolder> {
    public static final String ALBUM_TAG = "eugenebo.com.github.musicalmanac.views.albumdetail albumArgument";
    private Context context;
    private List<Album> albums;

    SearchListAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsListViewHolder holder, int position) {
        Album album = albums.get(position);

        final String artworkUrl = album.getArtworkUrl100();
        Glide.with(context)
                .load(artworkUrl)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher))
                .into(holder.albumArtworkImageVIew);

        holder.albumNameTextView.setText(album.getCollectionName());
        holder.artistNameTextView.setText(album.getArtistName());
        holder.genreTextView.setText(album.getPrimaryGenreName());
        holder.priceTextView.setText(String.format("$%s US", String.valueOf(album.getCollectionPrice())));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @NonNull
    @Override
    public AlbumsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumsListViewHolder(itemView);
    }

    class AlbumsListViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout baseViewHolder;
        private ImageView albumArtworkImageVIew;
        private TextView albumNameTextView;
        private TextView artistNameTextView;
        private TextView genreTextView;
        private TextView priceTextView;

        AlbumsListViewHolder(View view) {
            super(view);
            baseViewHolder = view.findViewById(R.id.baseConstraintItemAlbum);
            albumArtworkImageVIew = view.findViewById(R.id.albumArtworkTextView);
            albumNameTextView = view.findViewById(R.id.albumNameAlbumItemTextView);
            artistNameTextView = view.findViewById(R.id.artistNameItemAlbumTextView);
            genreTextView = view.findViewById(R.id.genreItemAlbumTextView);
            priceTextView = view.findViewById(R.id.priceItemAlbumTextView);

            baseViewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ALBUM_TAG, albums.get(getAdapterPosition()));
                    Fragment fragment = new AlbumDetailFragment();
                    fragment.setArguments(bundle);

                    ((FragmentActivity) context)
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, fragment, "DetailedFragment")
                            .addToBackStack("DetailedFragment")
                            .commit();
                }
            });
        }
    }
}
