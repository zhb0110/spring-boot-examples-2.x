# spring-boot-jwt

该项目就验证了jwt的实用

## 返回实体推荐格式

Controller返回类型都改为ResponseEntity<Object>
return:new ResponseEntity<>(返回数据, HttpStatus.OK);

## 注意

本Demo 此次没放在授权Authorization中，而是放在Header中
1. 如果是Authorization
HttpHeaders requestHeaders = new HttpHeaders();
requestHeaders.add("Authorization","Bearer "+client.getToken());
2. 如果是Header
String token = request.getHeader(jwtConfig.getHeader());

