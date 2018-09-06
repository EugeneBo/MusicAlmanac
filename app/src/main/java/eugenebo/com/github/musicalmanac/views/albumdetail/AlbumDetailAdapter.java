package eugenebo.com.github.musicalmanac.views.albumdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import java.util.Locale;
import eugenebo.com.github.musicalmanac.R;
import eugenebo.com.github.musicalmanac.model.Album;
import eugenebo.com.github.musicalmanac.model.Song;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.AlbumsListViewHolder> {

    private final static int SONG_INFO = 0;
    private final static int ALBUM_INFO = 1;

    private Context context;
    private List<ItemView> items;

    AlbumDetailAdapter(Context context, List<ItemView> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Album) return ALBUM_INFO;
        else return SONG_INFO;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsListViewHolder holder, int position) {
        ItemView item = items.get(position);
        if (item instanceof Song) {
            Song song = (Song) item;
            holder.trackNumber.setText(String.valueOf(song.getTrackNumber()));
            holder.trackName.setText(song.getTrackName());
            holder.trackTime.setText(toReadableFormatTimeConverter(song.getTrackTimeMillis()));
        } else {
            Album album = (Album) item;

            final String artworkUrl = album.getArtworkUrl100();
            Glide.with(context)
                    .load(artworkUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher))
                    .into(holder.artworkImageView);

            holder.albumNameTextView.setText(album.getCollectionName());
            holder.artistNameTextView.setText(album.getArtistName());
            holder.genreTextView.setText(album.getPrimaryGenreName());
            holder.yearTextView.setText(album.getReleaseDate().substring(0, 4));
            holder.priceTextView.setText(String.format("US $ %s", String.valueOf(album.getCollectionPrice())));
            holder.countryTextView.setText(album.getCountry());
            holder.copyrightTextView.setText(album.getCopyright());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public AlbumsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == SONG_INFO) itemView = provideView(R.layout.item_song, parent);
        else itemView = provideView(R.layout.item_album_info, parent);

        return new AlbumsListViewHolder(itemView, viewType);
    }

    private String toReadableFormatTimeConverter(Integer millis) {
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private View provideView(int layoutID, ViewGroup parent) {
        return LayoutInflater
                .from(parent.getContext())
                .inflate(layoutID, parent, false);
    }

    class AlbumsListViewHolder extends RecyclerView.ViewHolder {
        TextView trackNumber;
        TextView trackName;
        TextView trackTime;

        ImageView artworkImageView;
        TextView albumNameTextView;
        TextView artistNameTextView;
        TextView genreTextView;
        TextView yearTextView;
        TextView priceTextView;
        TextView countryTextView;
        TextView copyrightTextView;

        AlbumsListViewHolder(View view, int viewType) {
            super(view);
            if (viewType == SONG_INFO) {
                trackNumber = view.findViewById(R.id.trackNumberTextView);
                trackName = view.findViewById(R.id.trackNameTextView);
                trackTime = view.findViewById(R.id.trackTimeTextView);
            }
            if (viewType == ALBUM_INFO) {
                artworkImageView = view.findViewById(R.id.artworkDetailAlbumImageView);
                albumNameTextView = view.findViewById(R.id.albumDetailAlbumNameTextView);
                artistNameTextView = view.findViewById(R.id.artistDetailAlbumNameTextView);
                genreTextView = view.findViewById(R.id.genreDetailAlbumTextView);
                yearTextView = view.findViewById(R.id.yearDetailAlbumTextView);
                priceTextView = view.findViewById(R.id.priceDetailAlbumTextView);
                countryTextView = view.findViewById(R.id.countryDetailAlbumTextView);
                copyrightTextView = view.findViewById(R.id.copyrightDetailAlbumTextView);
            }
        }
    }
}
