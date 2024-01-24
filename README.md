# 24.01.24 10장 Mock 객체 활용

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
    * 실제 호출에 대한 테스트는 나머지 대다수의 빠른 테스트들에 비해 속도가 느릴 것이다\n
    * Nominatim HTTP API가 항상 가용한지 보장할 수 없다. 통제 밖의 것.

---
