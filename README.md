
## 프로젝트 개요

서울시에서 축구를 즐기는 아마추어 축구인들을 위한 사이트 입니다.

각 지역구에서 활동하는 축구팀에 대한 정보를 알 수 있고 용병 모집도 확인 할 수 있습니다.

또한 중고나라에서 개인들의 축구용품을 거래하실 수 있습니다.

커뮤니티에서 정보 교환도 가능합니다.

프로젝트 주소: http://15.164.166.234:8080/project/

아이디: asdf
패스워드: 1234

## 개발환경

* Intellij
* STS(Spring Tool Suite)
* GitHub
* Mysql Workbench
* Visual Studio Code
* Postman

## 사용기술

* Java 11
* Spring Framework
* Mysql
* Mybatis
* HTML
* CSS
* JavaScript
* jQuery
* AWS
## 패키징 구조
![패키지구조](https://user-images.githubusercontent.com/95623005/162899156-cb865559-88f0-4b00-bb9c-ad81b27aff18.png)

## 화면정의서


### 홈
![홈화면](https://user-images.githubusercontent.com/95623005/162899980-48797753-6935-45f2-833e-a1a28740ea17.png)
HTML과 CSS로 화면을 디자인하고 JSTL과 EL로 로그인을 하기 전에는 '로그인' 로그인을 했을때는 '로그아웃'버튼이 작동하게 만들었습니다.

그리고 가운데 navigation영역을 만들어서 공지사항과 이벤트가 차례대로 나타나도록 만들려고 합니다. 현재는 고정된 이미지로 대신했습니다. (2022-04-12~)

### 로그인
![로그인화면](https://user-images.githubusercontent.com/95623005/162902893-ad225eb8-63c7-40d0-a36c-40b2707caf35.png)
쿠키를 활용하여 아이디를 기억할 수 있게 만들었고 Validation을 사용하여 아이디와 비밀번호를 검사할 수 있게 만들었습니다.

아이디와 비밀번호를 찾는 화면은 앞으로 추가할 예정입니다(2022-04-12~)

### 회원가입
![회원가입](https://user-images.githubusercontent.com/95623005/162904590-58f883b9-f98e-4243-a2a2-e3554d2097d9.png)

형식에 맞지 않은 정보를 입력할때 경고 메시지가 나오는것으로 처리했지만 재대로 작동하지 않아서 현재 수정중입니다(2022-4-12~)

최종적으로 카카오나 다른 회원가입 API를 활용해서 로그인과 회원가입을 바꿀 예정입니다(2022-4-12~)

### 커뮤니티 게시판
![게시판](https://user-images.githubusercontent.com/95623005/162906624-c55b4782-df47-41e7-b98e-96c90b004364.png)
제목과 글쓴이를 범위로 정해서 게시글을 검색할 수 있게 DB를 작정했습니다 그리고 이를 JSTL로 반영했으며 나중에 ajax로 바꿔서 코드를 짜보려고 합니다.(2022-4-12~)

### 게시판 읽기
![게시판 일기](https://user-images.githubusercontent.com/95623005/162908207-8755291d-4034-441b-b9d7-9d54e84154d2.png)
DB에서 BoardDto에 저장되어있던 정보를 가져와서 보여줍니다.

또한 읽기만 가능하게 하기 위해 readonly로 수정을 못하게 설정합니다.

session에 있는 id를 확인한후 글쓴이와 동일 id이면 수정버튼과 삭제버튼이 나타나게 JSTL과 EL을 활용하여 만들어주었습니다.

