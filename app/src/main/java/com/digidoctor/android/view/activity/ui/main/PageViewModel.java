package com.digidoctor.android.view.activity.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.R;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            String[] strings = {
                    "Book an appointment with your nearest and top doctor",
                    "Too busy to see a doctor? Consent online with doctor at your place.",
                    "Get sample collection at your place, You stay at home."};
            return strings[input - 1];
        }
    });
    private LiveData<Integer> mImage = Transformations.map(mIndex, new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer input) {
            int[] IMAGES = {R.drawable.get_started, R.drawable.get_started_two, R.drawable.get_started_three};
            return IMAGES[input - 1];
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getImage() {
        return mImage;
    }
}