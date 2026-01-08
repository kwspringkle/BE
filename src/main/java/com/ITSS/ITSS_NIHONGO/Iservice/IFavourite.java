package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.AddFavourite;
import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.DeleteFavorite;
import com.ITSS.ITSS_NIHONGO.dto.response.Favourite.FavouriteResponse;

import java.util.List;

public interface IFavourite {
    List<FavouriteResponse> get3Favourite(int userId);
    List<FavouriteResponse> getAllFavourite(int userId);
    boolean addFavourite(int userId, AddFavourite addFavourite);
    boolean deleteFavourite(int userId,DeleteFavorite deleteFavorite);
}
