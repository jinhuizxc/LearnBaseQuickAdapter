package com.jh.baserv.adapter;

import com.jh.baserv.R;
import com.jh.baserv.base.BaseDataBindingAdapter;
import com.jh.baserv.databinding.ItemMovieBinding;
import com.jh.baserv.entity.Movie;

/**
 * Created by tysheng
 * Date: 2017/5/25 10:47.
 * Email: tyshengsx@gmail.com
 */

public class UpFetchAdapter extends BaseDataBindingAdapter<Movie, ItemMovieBinding> {
    public UpFetchAdapter() {
        super(R.layout.item_movie, null);
    }

    @Override
    protected void convert(ItemMovieBinding binding, Movie item) {
        binding.setMovie(item);
    }
}
