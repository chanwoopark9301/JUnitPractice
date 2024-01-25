# (24.01.24) 10장 Mock 객체 활용

## 도전과제 


```java
public class AddressRetriever {
    public Address retrieve(double latitude, double longitude)
            throws IOException, ParseException, org.json.simple.parser.ParseException {
        String parms = String.format("lat=%.6flon=%.6f", latitude, longitude);
        String response = new HttpImpl().get(
                "http://open.mapquestapi.com/nominatim/v1/reverse?format=json&"
                        + parms);

        JSONObject obj = (JSONObject)new JSONParser().parse(response);

        JSONObject address = (JSONObject)obj.get("address");
        String country = (String)address.get("country_code");
        if (!country.equals("us"))
            throw new UnsupportedOperationException(
                    "cannot support non-US addresses at this time");

        String houseNumber = (String)address.get("house_number");
        String road = (String)address.get("road");
        String city = (String)address.get("city");
        String state = (String)address.get("state");
        String zip = (String)address.get("postcode");
        return new Address(houseNumber, road, city, state, zip);
    }
}
```
  위의 코드는 아래의 두 가지 문제를 안고 있다. 이를 해결해보자.

  
```java
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

```
## 목을 올바르게 사용할 때 중요한 것

* 질문 리스트
  * 목이 프로덕션 코드의 동작을 ㅇ올바르게 묘사하고 있는가?
  * 프로덕션 코드는 생각하지 못한 다른 형식으로 반환하는가?
  * 프로덕션 코드는 예외를 던지는가?
  * null을 반환하는가?
* 테스트가 진짜를 목을 사용할까? 우연하게 여전히 프로덕션 코드를 실행하고 있진 않은가?
  -> 확인하고 싶다면 어떻게 해야 할까? 
  * 프로덕션 코드에서 런타임 예외를 던져 보기!!
  * 테스트를 실행할 때 예외가 보인다면 프로덕션 코드가 동작하고 있다는 것
  * 테스트를 고친 뒤엔 꼭 throw문을 제거하기
  
---
  
* 실제 호출에 대한 테스트는 나머지 대다수의 빠른 테스트들에 비해 속도가 느릴 것이다
* Nominatim HTTP API가 항상 가용한지 보장할 수 없다. 통제 밖의 것.

---
