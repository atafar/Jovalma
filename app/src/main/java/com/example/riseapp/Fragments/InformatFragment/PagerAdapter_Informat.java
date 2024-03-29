/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.riseapp.Fragments.InformatFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Fragment to return the clicked tab.
 */
public class PagerAdapter_Informat extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter_Informat(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabFragment1_Informat();
            case 1: return new TabFragment2_Informat();
            case 2: return new TabFragment3_Informat();
            case 3:
                return new TabFragment4_Informat();
            case 4:
                return new TabFragment5_Informat();
            case 5:
                return new TabFragment6_Informat();
            case 6:
                return new TabFragment7_Informat();

            default: return null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
