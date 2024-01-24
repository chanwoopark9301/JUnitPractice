package iloveyouboss;

import org.junit.Test;
import util.Http;

import java.io.IOException;
import java.text.ParseException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class AddressRetrieverTest {
    @Test
    public void answerAppropriateAddressForValidCoodinates() throws IOException, ParseException, org.json.simple.parser.ParseException {

        Http http = (String url) ->
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}";
        AddressRetriever retriever = new AddressRetriever(http);
        Address address = retriever.retrieve(38.0,-104.0);

        assertThat(address.houseNumber, equalTo("324"));
        assertThat(address.road, equalTo("North Tejon Street"));
        assertThat(address.city, equalTo("Colorado Springs"));
        assertThat(address.state, equalTo("Colorado"));
        assertThat(address.zip, equalTo("80903"));
    }
}