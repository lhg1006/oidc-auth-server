# OIDC Authorization Server

Spring Boot 3.4.0 기반 OIDC Authorization Server

## 🔐 보안 설정 (필수)

⚠️ **중요**: 이 애플리케이션은 민감한 정보(DB 비밀번호, Client Secret)를 Spring Profiles로 관리합니다.

### 1. Docker 환경 변수 파일 생성 (PostgreSQL용)

```bash
cp .env.example .env
```

```bash
# 강력한 비밀번호 생성
openssl rand -base64 24

# .env 파일 편집
nano .env
```

```env
# PostgreSQL Database
POSTGRES_USER=your_secure_username
POSTGRES_PASSWORD=your_secure_password_min_16_chars

# PgAdmin
PGADMIN_DEFAULT_PASSWORD=your_admin_secure_password
```

### 2. Spring Boot Profile 설정 파일 생성

**개발 환경:**
```bash
cp src/main/resources/application-dev.properties.example src/main/resources/application-dev.properties
nano src/main/resources/application-dev.properties
```

```properties
# Development Environment Settings
spring.datasource.username=your_db_username
spring.datasource.password=your_secure_password_min_16_chars
OAUTH2_CLIENT_SECRET=your_client_secret_min_24_chars
```

**운영 환경:**
```bash
cp src/main/resources/application-product.properties.example src/main/resources/application-product.properties
nano src/main/resources/application-product.properties
```

```properties
# Production Environment Settings
spring.datasource.username=your_production_db_username
spring.datasource.password=your_production_secure_password_min_16_chars
OAUTH2_CLIENT_SECRET=your_production_client_secret_min_24_chars
```

⚠️ **주의**:
- `.env` 파일과 `application-dev.properties`, `application-product.properties`는 절대 Git에 커밋하지 마세요!
- `.gitignore`에 이미 포함되어 있습니다

## 🚀 실행 방법

### 1. PostgreSQL 시작

```bash
docker-compose up -d
```

### 2. 서버 실행

**개발 환경:**
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**운영 환경:**
```bash
./gradlew bootRun --args='--spring.profiles.active=product'
```

**또는 JAR 실행 시:**
```bash
# 빌드
./gradlew build

# 개발 환경 실행
java -jar build/libs/oidc-auth-server-*.jar --spring.profiles.active=dev

# 운영 환경 실행
java -jar build/libs/oidc-auth-server-*.jar --spring.profiles.active=product
```

### 3. 접속 확인

- Server: http://localhost:9000
- PgAdmin: http://localhost:8080

## 🔒 보안 체크리스트

- [ ] `.env` 파일에 강력한 비밀번호 설정
- [ ] `application-dev.properties` 및 `application-product.properties` 파일 생성 및 설정
- [ ] 민감한 파일들이 `.gitignore`에 포함되어 있는지 확인
- [ ] 운영 환경에서는 포트 5432, 8080 외부 노출 차단
- [ ] HTTPS 적용 (운영 환경)
- [ ] 데이터베이스 정기 백업 설정

## 📁 설정 파일 구조

```
src/main/resources/
├── application.properties              # 기본 설정 (Git에 커밋됨)
├── application-dev.properties          # 개발 환경 비밀 정보 (Git 제외)
├── application-product.properties      # 운영 환경 비밀 정보 (Git 제외)
├── application-dev.properties.example  # 개발 환경 템플릿 (Git에 커밋됨)
└── application-product.properties.example  # 운영 환경 템플릿 (Git에 커밋됨)
```

**주의**: `application-dev.properties`와 `application-product.properties`는 절대 Git에 커밋되지 않습니다.