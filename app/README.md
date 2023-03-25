# MediaSearchingApp

## 기능 구현

### 검색

- 이미지와 동영상 api 호출 결과 각각을 flow 블록에서 방출하도록 해 zip으로 묶어 최신순 정렬
- 각기 다른 api 결과를 묶은 것이기에 다음 페이지와 이전 페이지 호출 결과들의 시간 정렬이 맞지 않을 수 있어 페이지 별로 구분선 추가
- 보관함 내에서 좋아요를 제거할 수도 있기 때문에 해당 fragment가 포커스 되었을 때 좋아요 리스트를 불러와 변한 것이 없는지 확인

### 보관함

- 검색 fragment에서 좋아요 추가/해제시 결과를 반영하기 위해 해당 fragment가 포커스 되었을 때 좋아요 리스트를 불러와 갱신

---

## 구조 (CleanArchitecture)

- 구성 : UI(app module), Domain(coreDomain module), Data(coreNetwork module)
- UI Layer
    - Activity, Fragment 등 사용자에게 보여지는 화면과 화면에 대한 데이터를 가지고 있는 ViewModel로 구성되어 있다.
- Domain Layer
    - 비즈니스 로직별 UseCase를 가지고 있다.
    - 각 UseCase는 네트워크 통신 결과를 UI에서 사용하기 편한 데이터로 변환한다.
- Data Layer
    - DataSource와 Repository, 네트워크 통신을 위한 클래스들을 가지고 있다.
    - 네트워크 통신을 위한 객체를 생성하며 통신 결과를 전달해주는 로직을 가지고 있다.

---

## 종속성 관리
- Version Catalog와 Custom Plugin으로 공통 디펜던시 관리