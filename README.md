# 싱크라이프 백엔드 신입 개발자 과제 - 스터디룸 예약
## 프로젝트 소개

싱크라이프 백엔드 신입 개발자 과제로 주어진 스터디룸 예약 시스템입니다.

목표: 동시성 속에서 무결성 지키기

## 실행 방법

먼저 프로젝트의 최상위 폴더에서 프로젝트를 빌드합니다.
```
./gradlew build -x test
```
다음으로 Docker로 실행합니다
```
docker-compose up --build
```
이후에 아래의 주소로 접속하여 스웨거로 테스트 하면됩니다.
```
http://localhost:8080/swagger-ui/index.html
```
미리 생성된 DB가 없기에 먼저 회원가입을 한 후 로그인 하여 엑세스 토큰을 발급받고, 
상단의 Available 버튼을 눌러 키 값을 붙여넣고 진행하면 됩니다.

## 기능
총 6가지의 간단한 기능으로 구성되어있습니다.
### 회원가입/로그인
### 회의실 등록
- 등록은 관리자만 할수있음
### 회의실 예약
- 회의실 번호, 시작 날짜, 종료 날짜를 정해서 예약 가능
- 동시 예약 방지를 위해 PostgreSQL의 EXCLUDE 제약 조건 사용
### 회의실 예약 조회
- yyyy-mm-dd 형식의 날짜를 입력받아 해당 날짜의 모든 예약 조회
### 회의실 예약 취소
- 관리자 또는 예약자만 취소 가능

### API명세서
[노션 링크](https://www.notion.so/275e3d8d21828144a9b9fe587abc8036?v=275e3d8d218281e690b8000c0c603d6e&source=copy_link)

## ERD
<img width="394" height="531" alt="image" src="https://github.com/user-attachments/assets/dc45f1f8-51fb-49e2-aa1a-a2432a4a7140" />

## 기술스택
&nbsp;&nbsp;&nbsp;&nbsp; ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/Springdata jpa-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" style="border-radius: 5px;">

## ADR (Architecture Decision Record)
### 중복 예약 방지 전략
- 과제에서 권장한 PostgreSQL의 `tstzrange` 타입과 `EXCLUDE USING gist` 제약을 활용하여 중복 예약을 방지했습니다. 

Spring JPA는 `EXCLUDE USING gist` 제약과 `tstzrange` 타입을 직접 지원하지 않기 때문에, 이를 해결하기 위해 프로젝트 실행 시 `schema.sql`을 통해 다음 작업을 수행했습니다:

1. `CREATE EXTENSION IF NOT EXISTS btree_gist;`를 실행하여 GiST 인덱스가 B-Tree 타입도 처리할 수 있도록 확장.
2. 예약 테이블을 직접 생성하고 `EXCLUDE USING gist` 제약을 설정하여 시간 범위의 겹침을 방지.

이 과정에서 기존의 `StartAt`, `EndAt` 컬럼을 제거하고 `timeRange` 하나로 통합하였으며, JPA에서는 `String` 타입으로 선언하되 `@Column(columnDefinition = "tstzrange")`를 사용해 PostgreSQL의 `tstzrange` 타입으로 매핑했습니다.
  
  
### 용어 설명
- GiST는 범위 검색이나 근접 검색에 강한 인덱스 방식
- btree_gist는 GiST 인덱스가 B-Tree 타입도 처리할 수 있게 확장해줌
- TSTZRANGE: timestamp with time zone 범위를 저장하는 PostgreSQL 타입
- EXCLUDE USING gist: GiST 인덱스를 사용해서 겹침 금지 제약을 설정.

## LLM 사용 구간
- 스프링 시큐리티의 기본적인 구조 생성시에 사용
- JWT 관련 파일 생성시에 사용 (인증 필터 등)
- SQL 쿼리문 작성시에 사용
- 다양한 주석등을 검수받고 더 나은 주석을 위해 사용
