package com.amadeusounds.view;

import com.amadeusounds.model.User;

/**
 * Created by Vladica Jovanovski on 4/13/2016.
 */
public interface Views {
    public static class SongBaseView {}
    public static class SongSummaryView extends SongBaseView{}
    public static class SongFullView extends SongSummaryView{}

    public static class UserBaseView extends SongBaseView {}
    public static class UserSummaryView extends UserBaseView {}
}
