package com.amhzing.participant.helper;

import java.util.Arrays;
import java.util.Locale;

class Countries {

    public static void main(String[] args) {

        Arrays.stream(Locale.getISOCountries())
              .map(countryCode -> new Locale("", countryCode))
              .forEach(locale -> System.out.println("Country Code >"
                                                    + locale.getCountry()
                                                    + "< Country Name >"
                                                    + locale.getDisplayCountry()
                                                    + "<"));
    }
}
