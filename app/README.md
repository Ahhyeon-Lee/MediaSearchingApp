# MediaSearchingApp

- 다음 이미지, 동영상 검색 API를 이용해 미디어 검색 기능
- 검색한 이미지와 동영상들 중 보관하고 싶은 썸네일에 하트를 눌러 보관함에 저장 가능한 저장 기능

## 기능 구현

### 검색(Search)

- 검색어 입력 후 300ms 이내 추가 입력 없으면 검색 API 호출
- 검색 API 호출 작업이 완료되지 않은 상태에서 다시 검색 API 호출시 이전 작업(Job)은 취소하여 연속적인 API 호출로 인한 부담 낮춤 
- 이미지와 동영상 api 호출 결과 각각을 flow 블록에서 방출하도록 해 zip으로 묶어 최신순 정렬
- 각기 다른 api 결과를 묶은 것이기에 다음 페이지와 이전 페이지 호출 결과들의 시간 정렬이 맞지 않을 수 있어 페이지 별로 구분선 추가
- 보관함 내에서 좋아요를 제거할 수도 있기 때문에 해당 fragment가 포커스 되었을 때 좋아요 리스트를 불러와 변한 것이 없는지 확인

### 보관함(MyList)

- 좋아요 변경이 보관함과 검색 화면 모두에서 변경 가능하기 때문에 좋아요 이미지와 동영상 리스트를 Room에서 불러올때 Flow로 가져와 두 리스트를 flatMapLatest로 묶어 반환
- 이를 통해 이미지와 동영상 좋아요 데이터가 바뀔 때마다 자동으로 보관함 내 UI 업데이트

---

## 구조 (CleanArchitecture)

- 구성 : UI(app module), Domain(coreDomain module), Data(coreNetwork module)
- UI Layer
  - Activity, Fragment, ViewModel
- Domain Layer
  - 비즈니스 로직별 UseCase, Repository 인터페이스
- Data Layer
  - DataSource, Repository 구현체, 네트워크 통신을 위한 클래스
  - 네트워크 통신을 위한 객체를 생성하며 통신 결과를 전달해주는 로직을 가지고 있다.

---

## 종속성 관리
- Version Catalog와 Custom Plugin으로 모듈별 공통 디펜던시 관리