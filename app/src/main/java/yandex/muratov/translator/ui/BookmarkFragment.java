package yandex.muratov.translator.ui;

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

public class BookmarkFragment extends android.support.v4.app.Fragment {

    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks_screen, container, false);
        pager = initPager(view, getChildFragmentManager(), getActivity().getResources());
        tabLayout = initTabLayout(view, pager);
        return view;
    }

    private static TabLayout initTabLayout(View view, ViewPager pager) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.layout_toolbar).findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        return tabLayout;
    }

    private static ViewPager initPager(View rootView, FragmentManager fm, Resources resources) {
        ViewPager pager = ((ViewPager) rootView.findViewById(R.id.pager));
        pager.setAdapter(new SectionPageAdapter(fm, resources));
        return pager;
    }

    private static class SectionPageAdapter extends FragmentPagerAdapter {

        private static String TAG = SectionPageAdapter.class.getSimpleName();

        private Resources resources;

        public SectionPageAdapter(FragmentManager fm, Resources res) {
            super(fm);
            this.resources = res;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: ");
            switch (position) {
                case 0:
                    return getBookmarksPage();
                case 1:
                    return getFavoritesPage();
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

        private Fragment getBookmarksPage() {
            return BookmarkPageFragment.getInstance();
        }

        private Fragment getFavoritesPage() {
            return FavoritesPageFragment.getInstance();
        }
    }

}
