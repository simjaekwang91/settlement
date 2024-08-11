# **과제 : 1/N 정산하기 기능 구현**
### 카카오페이에서 제공하는 1/N 정산하기 기능을 구현합니다.
- 사용자는 다수의 사람들에게 금액을 지정하여 정산 요청을 할 수 있습니다.
- 요청받은 사용자는 정산하기 버튼을 통해서 요청한 요청자에게 금액을
  송금할 수 있습니다.
- 요청한 모든 인원이 정산을 완료한 경우에는 해당 요청은 정산완료
  처리됩니다.
- 요청한 일부 인원중 미정산된 경우에는 리마인드 알림을 보낼 수 있습니다.
- 요청하기 또는 요청받는 사람은 여러 개의 요청을 생성 또는 요청받습니다.
- 요청자는 자신이 요청한 정산하기 전체 리스트의 정보를 확인할 수
  있습니다.
- 요청받은 사람은 자신이 요청받은 정산하기 전체 리스트의 정보를 확인할 수
  있습니다.

# 기술스택
Spring Boot 3.2.5 로 구성하였으며 Kotlin 으로 작성되었습니다.
정산 관련 DB 는 MariaDB로 구성하였습니다.
- Spring Boot 3.2.5
- JAVA 17 Version
- MariaDB
- JUnit 5

# 상세 기능 명세
Hexagonal architecture 로 구성해보았으며 호출은 Adaptor Application Domain 로 패키지 구조를 만들었습니다.
호출은 Adaptor.rest -> UseCase -> Application.Service -> Domain
-> Application.out.port -> Adaptor.persistence(DB) 의 순서로 호출됩니다.

### Feature 별 Endpoint
- 사용자는 다수의 사람들에게 금액을 지정하여 정산 요청을 할 수 있습니다. createSettlement
- 요청받은 사용자는 정산하기 버튼을 통해서 요청한 요청자에게 금액을 송금할 수 있습니다. sendMoney
- 요청한 모든 인원이 정산을 완료한 경우에는 해당 요청은 정산완료 sendMoney
  처리됩니다.
- 요청한 일부 인원중 미정산된 경우에는 리마인드 알림을 보낼 수 있습니다. createSettlement
  (알람은 Scheduled 를 통해 매 분 async를 통해 500건씩 끊어서 알람을 처리합니다.)
- 요청하기 또는 요청받는 사람은 여러 개의 요청을 생성 또는 요청받습니다. createSettlement
- 요청자는 자신이 요청한 정산하기 전체 리스트의 정보를 확인할 수 있습니다. getOwnerSettlementList
- 요청받은 사람은 자신이 요청받은 정산하기 전체 리스트의 정보를 확인할 수
  있습니다. getRequestedSettlementList


### 기술적인 요구사항
- Front-End를 제외한 기능을 REST API로 구현합니다.
- 로그인 사용자의 식별값은 숫자 형태이며 “X-USER-ID”라는 Http Header로
  전달됩니다. -> webFilter 및 Resolver를 동록하여 Custom Header를 Controller의 파라미터로 전달했습니다.)
- (선택) 사용자가 속한 대화방의 식별값은 문자 형태이며, “X-ROOM-ID”라는 Http
  Header로 전달됩니다.
- 작성한 어플리케이션이 다수의 서버에 다수의 인스턴스가 실행되더라도 기능에
  문제가 없도록 설계되어야 합니다
- 각 기능 및 제약사항에 대한 단위테스트를 작성하여야 합니다.
- 실제 금액 이체, 실제 알림 발송 등의 외부의존성이 필요한 기능은 Interface 혹은  (알람은 Scheduled 를 통해 매 분 async를 통해 500건씩 끊어서 알람을 처리합니다.)
  Mock으로 구현합니다. (실제 구현은 없어도 됩니다)
- 회원정보의 가입/탈퇴는 생략하며, 이미 회원정보가 존재한다고 가정합니다.
- 개인정보가 있을 경우 데이터는 암호화되어야 합니다.
- 동시성 이슈를 함께 고려합니다.-> Entitiy에 @Version 어노테이션을 추가하여 낙관적 잠금방식으로 고려했습니다.
