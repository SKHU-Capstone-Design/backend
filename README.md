AWS의 EC2에 서버는 배포가 되어있고 다음달까지 인스턴스는 실행되고 있을 예정입니다. 

프론트 개발을 하고 있는 친구의 웹을 사용하시면 백엔드도 연결이 되어있습니다.

저의 코드를 local에서는 환경변수만 설정해주시면 바로 테스트가 가능합니다. 

만약 부득이 하게 서버에 오류가 발생하면 아래와 같이 배포해 주시면 감사하겠습니다.

java 버전은 17을 사용했습니다.
jpa와 spring security, spring develop tools, lombok, mysql Driver등을 사용하였습니다.
![image](https://github.com/SKHU-Capstone-Design/backend/assets/129734272/4fa3bc2f-582e-42cb-9ae8-b75f1b637d93)

서버가 오류가 발생하여 직접 서버를 설치 해야한다면
![image](https://github.com/SKHU-Capstone-Design/backend/assets/129734272/5f287acf-5dc6-4412-aca0-57a57a5e2c80)
JWT_SECRET만 환경변수에 설정해 주시고 aws에 ec2를 이용해 서버 배포를 해주세요.
java와 mysql을 설치 하시고 
![image](https://github.com/SKHU-Capstone-Design/backend/assets/129734272/da1a2698-79e8-4585-9772-ecaf319e61b4)

위와 같이 데이터베이스의 이름과 username, userpassword를 설정하신뒤 
.jar 파일을 gradle를 이용하여 다운 받으신 뒤 ubuntu에서 실행해 주시면 감사하겠습니다.


