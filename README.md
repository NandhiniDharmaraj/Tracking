 Tracking Number Generator API

This is a scalable Spring Boot API that generates unique tracking numbers based on country, weight, and customer metadata.

 Deployment
https://tracking-1-zpbg.onrender.com/swagger-ui.html
The API is deployed on Render and publicly accessible:

🔗 [Live API Endpoint](https://tracking-1-zpbg.onrender.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics)

**Postman
**curl "https://tracking-1-zpbg.onrender.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics"


Github: https://github.com/NandhiniDharmaraj/Tracking
or 
https://github.com/NandhiniDharmaraj/Tracking/tree/master
Note :
I have push code master branch 
---

 Setup (Local Development)

Prerequisites:
* Java 17+
* Maven 
* PostgreSQL (optional for local testing)

./mvnw clean package
java -jar target/trackingapi-0.0.1-SNAPSHOT.jar



