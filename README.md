## 📁 프로젝트 개요

- **프로젝트명**: RealQ (Real-time Air Quality Monitoring)
- **목표**: 에어코리아 API로부터 실시간 대기 데이터를 수집하여 사용자에게 시각화 및 알림 제공
- **핵심 기능**: 공공 API 연동, 자동 스케줄링, 데이터 저장/조회, 사용자 알림, 통계 제공
- **개발 인원**: 2-3인 협업 (백엔드 중심)

---

## 👨‍👩‍👧‍👦 참여 인원 및 역할

### 🔹 A 개발자 (데이터 수집 및 API 관리 담당)

- 에어코리아 OpenAPI 연동 (`RestTemplate`/`WebClient`)
- 실시간 대기질 데이터 수집 및 DB 저장 (`@Scheduled`)
- 최신/기간별 데이터 조회 API 설계 및 구현
- JPA 기반 Entity 설계 및 DB 최적화

### 🔹 B 개발자 (사용자 및 기능 담당)

- 회원가입 / 로그인 구현 (JWT + Spring Security)
- 즐겨찾는 지역 관리 기능 구현
- 알림 조건 설정 및 알림 전송 기능 구현
- 사용자 맞춤형 통계 API 개발 (일평균/최고/최저)

### 🔹 C 개발자 (옵션 – 전체 구조 정비 및 테스트 담당)

- Swagger 기반 API 명세 작성 및 테스트 케이스 작성
- ERD 및 전체 도메인 정리
- 데이터 검증 및 단위/통합 테스트 구성

---

## 🔧 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Spring Scheduler
- Swagger
- Slack Webhook (알림)

---

## 🗃️ ERD

### region

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long (PK) | 지역 고유 ID |
| name | String | 지역명 (예: 서울) |
| station_name | String | 측정소 이름 (에어코리아 기준) |
| latitude | Double | 위도 |
| longitude | Double | 경도 |

### air_quality_data

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long (PK) | 자동 생성 ID |
| region_id | Long (FK → region.id) | 측정 지역 |
| data_time | LocalDateTime | 측정 시간 |
| pm10 | Integer | 미세먼지 수치 |
| pm25 | Integer | 초미세먼지 수치 |
| o3 | Double | 오존 농도 (ppm) |
| no2 | Double | 이산화질소 농도 (ppm) |
| co | Double | 일산화탄소 농도 (ppm) |
| so2 | Double | 아황산가스 농도 (ppm) |
| khai_grade | Integer | 통합대기환경지수 |
| khai_value | Integer | 통합대기환경수치 |
| created_at | Timestamp | 수집 시각 (자동 저장) |

### user

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long (PK) | 사용자 ID |
| email | String | 이메일 |
| password | String | 암호화된 비밀번호 |
| nickname | String | 닉네임 |
| created_at | Timestamp | 가입일 |

### user_favorite_region

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long (PK) | 고유 ID |
| user_id | Long (FK) | 사용자 참조 |
| region_id | Long (FK → region.id) | 즐겨찾는 지역 |

### user_notify_setting

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | Long (PK) | 고유 ID |
| user_id | Long (FK) | 사용자 참조 |
| region_id | Long (FK → region.id) | 알림 대상 지역 |
| pm10_threshold | Integer | PM10 기준값 |
| pm25_threshold | Integer | PM2.5 기준값 |
| enabled | Boolean | 알림 활성화 여부 |
| created_at | Timestamp | 설정 등록 시각 |

---

## 🌐 API 명세서

### 1. 실시간 대기질 조회

```
GET /api/airquality/latest?regionId=1
```

- 설명: 가장 최근 측정된 대기질 정보 조회
- 응답:

```json
{
  "region": "서울",
  "dataTime": "2025-04-05 14:00",
  "pm10": 85,
  "pm25": 54
}
```

### 2. 기간별 대기질 조회

```
GET /api/airquality/history?regionId=1&from=2025-04-01&to=2025-04-05
```

### 3. 사용자 회원가입

```
POST /api/user/register
```

- 요청:

```json
{
  "email": "test@example.com",
  "password": "1234",
  "nickname": "홍길동"
}
```

### 4. 로그인 (JWT)

```
POST /api/user/login
```

### 5. 즐겨찾기 지역 등록

```
POST /api/user/favorite
```

- 요청:

```json
{
  "regionId": 1
}
```

### 6. 하루 평균 통계 조회

```
GET /api/statistics/daily-average?regionId=1
```

### 7. 알림 조건 설정

```
POST /api/notify/settings
```

- 요청:

```json
{
  "regionId": 1,
  "pm10Threshold": 100,
  "pm25Threshold": 50,
  "enabled": true
}
```
