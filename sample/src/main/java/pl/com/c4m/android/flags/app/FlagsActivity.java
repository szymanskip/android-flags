package pl.com.c4m.android.flags.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import pl.com.c4m.android.flags.Flags;

/**
 * @author Pawel Szymanski
 */

public class FlagsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags);
        ListView flagsListView = (ListView) findViewById(R.id.list_flags);
        FlagsAdapter adapter = new FlagsAdapter(this);
        flagsListView.setAdapter(adapter);
        List<Country> countries = getCountries();
        sortByName(countries);
        adapter.setCountries(countries);
        adapter.notifyDataSetChanged();
    }

    private void sortByName(List<Country> countries) {
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return c1.getDisplayName().compareTo(c2.getDisplayName());
            }
        });
    }

    @NonNull
    private List<Country> getCountries() {
        Set<String> countryCodes = Flags.getAvailableCountryCodes();
        List<Country> countries = new ArrayList<>(countryCodes.size());
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countries.add(new Country(countryCode, locale.getDisplayCountry()));
        }
        return countries;
    }

    private class FlagsAdapter extends BaseAdapter {
        private final Context context;
        private final LayoutInflater layoutInflater;
        private List<Country> countries = Collections.emptyList();

        FlagsAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return countries.size();
        }

        @Override
        public Country getItem(int position) {
            return countries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView itemView;
            if (convertView == null) {
                itemView = (TextView) layoutInflater.inflate(R.layout.item_country, parent, false);
            } else {
                itemView = (TextView) convertView;
            }
            Country country = getItem(position);
            itemView.setText(country.getDisplayName());
            Drawable flagDrawable = Flags.drawableFromCode(context, country.getCode());
            itemView.setCompoundDrawablesWithIntrinsicBounds(flagDrawable, null, null, null);
            return itemView;
        }

        void setCountries(List<Country> countries) {
            this.countries = countries;
        }
    }
}
