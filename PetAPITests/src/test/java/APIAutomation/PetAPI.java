package APIAutomation;

import io.restassured.RestAssured;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class PetAPI {

	@Test
	public void pet() {
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		
        //Find pet by ID
		given().log().all()
		.when().get("pet/1")
		.then().log().all().assertThat().statusCode(200);
		
		given().log().all()
		.when().get("pet/0")
		.then().log().all().assertThat().statusCode(404);
		
		//update pet name
		Map<String, String> keys = new HashMap<String, String>() {
            {
                put("name", "myfavourite");
                put("status", "pending");
            }
        };
		given().log().all()
		.config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
		.formParams(keys).post("pet/1")
		.then().log().all().assertThat().statusCode(200);
		
		
		//Delete Pet
		given().log().all().header("api_key", "special-key")
		.when().delete("pet/0")
		.then().log().all().assertThat().statusCode(404);
	}

}
