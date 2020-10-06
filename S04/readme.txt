http://localhost:8081/h2/


Both is OK
===========================================================
#spring.datasource.driverClassName=com.mysql.jdbc.Driver
#spring.datasource.url=jdbc:mysql://127.0.0.1:3306/testdb?serverTimezone=GMT%2B8
#spring.datasource.username=root
#spring.datasource.password=boston

-----------------------------------------------------------

spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
#spring.datasource.url=jdbc:h2:file:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.platform=h2
spring.h2.console.settings.web-allow-others=true
spring.h2.console.path=/h2
spring.h2.console.enabled=true
===========================================================