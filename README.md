# 🔍 Loa Nickname Search (Backend API)

> **"로스트아크 닉네임, 게임 접속 없이 API로 빠르게 검색하세요."**
> Lost Ark Nickname Availability Check & Search API Service

<br>

## 1. 📅 프로젝트 개요
- **프로젝트명:** Loawa Nickname Search (Backend)
- **개발 기간:** 2026.01.19 ~ 진행 중
- **개발 인원:** 1인 (Back-end)
- **포지션:** Backend API Developer

<br>

## 2. ❓ 기획 배경 (Why?)
로스트아크 캐릭터 생성 시, 중복된 닉네임을 확인하려면 매번 게임에서 직접 타이핑하여 확인하는 불편함이 있었습니다.
이러한 **불필요한 시간을 단축**하고, 웹 환경에서 **손쉽게 닉네임 사용 가능 여부를 조회**할 수 있는 서비스를 기획하게 되었습니다.

이 리포지토리는 해당 서비스의 **백엔드(Server)** 역할을 담당하며, 프론트엔드 클라이언트에게 **RESTful API 형태의 JSON 데이터**를 제공하는 것을 목적으로 합니다.

<br>

## 3. 🛠️ Tech Stack
### Environment
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5.9-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Database & ORM
![MySQL](https://img.shields.io/badge/MySQL_8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

### Collaboration
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

### Infrastructure & DevOps
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

<br>

## 4. 📐 System Architecture
이 프로젝트는 철저하게 **API 서버**로서의 역할에 집중합니다.
화면(View) 로직은 배제하고, 클라이언트의 요청에 대해 순수한 **JSON** 데이터만을 응답합니다.

```mermaid
graph LR
    A["Client (React/Web)"] -- "1. 닉네임 검색 요청" --> B["Spring Boot API Server"]
    B -- "2. 실시간 가능 여부 확인" --> X{"LostArk"}
    
    subgraph Docker Environment
        C[("MySQL Database")]
    end
    
    B -- "3. 검색 기록 저장 (Insert)" --> C
    C -- "4. 인기 검색어 조회 (Select)" --> B
    B -- "5. 결과 응답 (JSON)" --> A
```

<br>

## 5. 🚀 Getting Started (로컬 실행 방법)
본 프로젝트는 로컬 DB 환경을 오염시키지 않기 위해 **Docker**를 사용하여 MySQL을 실행합니다.

### Prerequisites
- Docker Desktop 설치 필요

### DB 실행 명령어
```bash
docker run --name loa-mysql -e MYSQL_ROOT_PASSWORD=1234 -d -p 3306:3306 mysql:8.0
```
