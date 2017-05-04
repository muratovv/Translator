package yandex.muratov.translator.ui.bookmarks;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yandex.muratov.translator.R;

public class BookmarkScreenFragment extends android.support.v4.app.Fragment {
    private static String TAG = BookmarkScreenFragment.class.getSimpleName();

    private ViewPager pager;
    private TabLayout tabLayout;
    private BookmarkPageFragment bookmarks;
    private FavoritesPageFragment favorites;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_bookmarks_screen, container, false);
        bookmarks = getBookmarksPage();
        favorites = getFavoritesPage();
        pager = initPager(view, getChildFragmentManager(), getActivity().getResources(),
                bookmarks, favorites);
        tabLayout = initTabLayout(view, pager);
        return view;
    }

    private static TabLayout initTabLayout(View view, ViewPager pager) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.layout_toolbar).findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        return tabLayout;
    }

    private static ViewPager initPager(View rootView, FragmentManager fm, Resources resources,
                                       final BookmarkPageFragment bookmarks, final FavoritesPageFragment favorites) {
        ViewPager pager = ((ViewPager) rootView.findViewById(R.id.pager));
        pager.setAdapter(new SectionPageAdapter(fm, resources, bookmarks, favorites));
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        Log.d(TAG, "onPageSelected: bookmarks");
                        bookmarks.activate();
                        break;
                    case 1:
                        Log.d(TAG, "onPageSelected: favorites");
                        favorites.activate();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        pager.addOnPageChangeListener(listener);
        return pager;
    }

    private static class SectionPageAdapter extends FragmentPagerAdapter {

        private Resources resources;
        private BookmarkPageFragment bookmarks;
        private FavoritesPageFragment favorites;

        public SectionPageAdapter(FragmentManager fm, Resources resources,
                                  BookmarkPageFragment bookmarks, FavoritesPageFragment favorites) {
            super(fm);
            this.resources = resources;
            this.bookmarks = bookmarks;
            this.favorites = favorites;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return bookmarks;
                case 1:
                    return favorites;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return resources.getString(R.string.page_title_history);
                case 1:
                    return resources.getString(R.string.page_title_favorites);
            }
            return "";
        }
    }

    private static BookmarkPageFragment getBookmarksPage() {
        return BookmarkPageFragment.getInstance();
    }

    private static FavoritesPageFragment getFavoritesPage() {
        return FavoritesPageFragment.getInstance();
    }
}
