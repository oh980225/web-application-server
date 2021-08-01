# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* BufferReader의 readLine() 를 쓸때는 inputStream 이 반드시 개행문자가 포함되어야 한다. 자바에서의 개행문자는 "\n" 이지만, 스트림에서의 개행문자는 "\r\n"이 개행문자이다. 따라서, 보내는쪽의 데이터 뒤에 "\r\n"을 반드시 붙여야한다.
 [출처] : https://drawdeveloper.tistory.com/69

* http 요청에 마지막은 개행문자가 없는 빈 문자열, 즉 readLine != null인 것으로 while을 돌릴 경우 문제가 생긴다.

*  !"".equal(line)을 조건으로 사용해야함!
### 요구사항 2 - get 방식으로 회원가입
* 요구사항 쪼개기
1. url을 path와 params로 나눈다. ✔
2. param을 쿼리스트링으로 나눈다. ✔ 
3. 해당 요청시 param부분이 빌 수 없다. ✔
4. 쿼리스트링을 차례로 model.User로 저장한다. ✔
5. 이때 User는 인메모리에 저장한다. ✔
6. User가 받는 모든 값은 공백일 수 없다. ✔


### 요구사항 3 - post 방식으로 회원가입
* 요구사항 쪼개기
1. GET요청과 POST요청을 분리한다 ✔
2. POST요청에서 readData 메소드를 이용하여 request body를 가져온다
3. 본문의 길이는 HTTP 헤더의 Content-Length의 값을 이용한다 ✔
4. body를 parseQueryString 메소드를 이용해 model.User로 저장한다. 
5. 이때 User는 인메모리에 저장한다. ✔
6. User가 받는 모든 값은 공백일 수 없다. ✔

* HTTP요청 시에 빈 요청이 들어오는 경우가 있는 것 같다.
* 원인을 잘 알 수가 없다. 내가 코드를 잘못짠 것인지, 내가 모르는 통신상 무엇인가가 있는지 찾아봤지만 잘 알 수 없었다.
* 뒤에 책에 구현된 것을 보고 테스트해본 뒤 똑같은 현상이 일어나면 혹시 어떤 이유 때문인지 찾아봐야겠다.

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 