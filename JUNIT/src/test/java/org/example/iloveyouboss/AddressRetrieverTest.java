package org.example.iloveyouboss;

import org.example.util.Http;
import org.junit.Test;

import java.io.IOException;
import org.json.simple.parser.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressRetrieverTest {
    @Test
    public void answersAppropriateAddressForValidCoordinates()
            throws IOException, ParseException, java.text.ParseException {
        Http http = mock(Http.class);
        when(http.get(contains("lat=38.000000&lon=-104.000000"))).thenReturn(
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        // ...
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}");
        AddressRetriever retriever = new AddressRetriever(http);

        Address address = retriever.retrieve(38.0,-104.0);

        assertThat(address.houseNumber, equalTo("324"));
        assertThat(address.road, equalTo("North Tejon Street"));
        assertThat(address.city, equalTo("Colorado Springs"));
        assertThat(address.state, equalTo("Colorado"));
        assertThat(address.zip, equalTo("80903"));
    }
}
