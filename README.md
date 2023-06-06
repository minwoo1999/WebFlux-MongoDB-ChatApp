# WebFlux-MongoDB-ChatApp
WebFlux MongoDB RealTime Chatting


https://github.com/minwoo1999/WebFlux-MongoDB-ChatApp/assets/79193811/d26fa71e-b6dc-4636-a5d1-7d935e7b593e




netty 서버
mongo db연결 

서블릿기반 스프링
->스레드 만들어진다(사용자 request) 

스프링 5.0 과 4.0 차이 netty 와 톰캣

-- MongoDB Command

use chat;
db.chat.save({sender:'ssar', receiver:'cos',msg:'안녕'});
db.chat.find();
db.chat.find().pretty();


-- SSE 프로토콜

클라이언트 요청 서버 응답
요청에서는 끊어버리는데
응답은 계속 유지하고있다가 데이터가 서버에 들어오면 flux 로 
데이터를 흘려보내준다.


Tailable 로 요청을 했고
데이터가 계속 흘러들어온다 열러있다고 보면됨 controller로
그럼 controller에서 flux로 계속 데이터를 유지하면서 흘려보냄


@Tailable 를 사용하기위해서는 버퍼사이즈 조정이 필요하다
db.runCommand({convertToCapped: 'chat', size:8192}); 

