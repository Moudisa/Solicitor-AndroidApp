package com.apkglobal.taekook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter{
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: RequestsFragment requestsFragment= new RequestsFragment();
                return requestsFragment;
            case 1: ArticlesFragment articlesFragment = new ArticlesFragment();
                return articlesFragment;
            case 2: ChatsFragment chatsFragment= new ChatsFragment();
                return chatsFragment;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "REQUESTS";
            case 1:
                return "ARTICLES";
            case 2:
                return "CHATS";
                default:
                    return null;
        }
    }
}
