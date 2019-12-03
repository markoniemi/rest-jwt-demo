rest-jwt-demo
--

Demonstrates a REST service with spring-boot-web, and another REST service using CXF.

REST with spring-boot-web
--

No extra config required. See UserRestController. This approach is useful if no WebService is required, and CXF is not wanted. Also useful if application is already providing a web application using spring-web.

Contract-last WebService can be done with jaxws without CXF, but that is using rather outdated dependencies.
 

REST with CXF
--   

CXF dependencies are provided by cxf-spring-boot-starter-jaxrs, which is maintained by cxf, not spring. CXF can be configured by autoconfig and application.properties, but here WebServiceConfig and RestConfig are used, it probably offers more flexibility in the long run. 

Feign
--

Testing is done using FeignClient, which creates a client from an interface. IntegrationTestConfig uses @EnableFeignClients and UserClient extends from UserService and uses @FeignClient.

JWT
--

JWT is using java-jwt and spring-security. API requires JWT, which can be obtained from /login, which is connected to UserRepositoryAuthenticationProvider. There are two JWT filters, JwtAuthenticationFilter which adds JWT to header after successful authentication, and JwtAuthorizationFilter which checks that all calls have a valid JWT in there header. UserDetailsServiceImpl only adds user details to JWT, and is not necessary.  

