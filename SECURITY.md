# 🔐 보안 가이드

## ⚠️ 중요: GitHub에 이미 민감 정보를 푸시한 경우

### 1. 즉시 조치 사항

만약 이미 비밀번호나 Secret이 포함된 커밋을 푸시했다면:

#### A. 모든 비밀번호/Secret 즉시 변경
```bash
# PostgreSQL 비밀번호 변경
docker exec -it oidc-postgres psql -U postgres
ALTER USER oidc_user WITH PASSWORD 'new_strong_password_here';

# .env 파일의 모든 비밀번호 변경
```

#### B. Git 히스토리에서 민감 정보 제거

**⚠️ 주의**: 이 작업은 Git 히스토리를 재작성하므로 신중히 진행하세요.

```bash
# BFG Repo-Cleaner 사용 (권장)
brew install bfg  # macOS
# 또는 https://rtyley.github.io/bfg-repo-cleaner/ 에서 다운로드

# 민감 정보가 포함된 파일 제거
bfg --delete-files .env
bfg --delete-files application.properties.backup
bfg --replace-text passwords.txt  # 비밀번호 목록 파일

# Git history cleanup
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Force push (협업 중이라면 팀원에게 알림 필수!)
git push --force
```

#### C. GitHub에서 Repository Secrets 확인
1. GitHub Repository → Settings → Security → Secret scanning
2. 노출된 Secret이 있는지 확인
3. 있다면 즉시 Revoke/Regenerate

---

## 🛡️ 안전한 운영 가이드

### 1. 환경 변수 관리

#### 개발 환경
- `.env` 파일 사용
- `.gitignore`에 반드시 포함
- 팀원과 공유 시 암호화된 채널 사용 (1Password, Bitwarden 등)

#### 운영 환경
- **절대 `.env` 파일 사용 금지**
- 시스템 환경 변수 또는 Secret Manager 사용:
  - AWS: AWS Secrets Manager / Parameter Store
  - Azure: Azure Key Vault
  - GCP: Secret Manager
  - Kubernetes: Sealed Secrets / External Secrets Operator

### 2. 비밀번호 강도 기준

```
❌ 약한 비밀번호
- oidc_password
- admin123
- password123

✅ 강한 비밀번호 (최소 기준)
- 16자 이상
- 대소문자 + 숫자 + 특수문자 혼합
- 사전에 없는 단어
예시: K9$mP2#vL8@nQ5wX
```

**생성 방법:**
```bash
# 32자 랜덤 비밀번호 생성
openssl rand -base64 32

# 또는
pwgen -s 32 1
```

### 3. 데이터베이스 보안

#### PostgreSQL
```yaml
# ❌ 위험: 외부 접근 허용
ports:
  - "5432:5432"

# ✅ 안전: 로컬만 접근 허용 (운영 환경)
ports:
  - "127.0.0.1:5432:5432"

# ✅ 더 안전: Docker network만 사용 (포트 노출 안 함)
# ports 섹션 제거, 같은 네트워크의 컨테이너만 접근
```

### 4. Spring Boot 설정

#### application.properties 보안
```properties
# ❌ 절대 금지
spring.datasource.password=plaintext_password

# ✅ 환경 변수 사용
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# ✅ Jasypt 암호화 (선택사항)
spring.datasource.password=ENC(encrypted_value_here)
```

#### 운영 환경 설정
```properties
# ddl-auto를 create-drop에서 변경
spring.jpa.hibernate.ddl-auto=validate  # 운영 환경

# SQL 로깅 비활성화
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=WARN
```

### 5. HTTPS 필수 (운영 환경)

```yaml
# application-prod.properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12
```

---

## 📋 보안 체크리스트

### 배포 전 필수 확인 사항

- [ ] 모든 비밀번호가 환경 변수로 관리되는가?
- [ ] `.env`, `.env.local` 파일이 `.gitignore`에 있는가?
- [ ] GitHub에 민감 정보가 푸시되지 않았는가?
- [ ] 데이터베이스 비밀번호가 16자 이상인가?
- [ ] AUTH_SECRET이 32자 이상의 랜덤 문자열인가?
- [ ] 운영 환경에서 `ddl-auto=create-drop`이 아닌가?
- [ ] HTTPS가 적용되었는가?
- [ ] 데이터베이스 포트가 외부에 노출되지 않는가?
- [ ] 정기 백업이 설정되었는가?
- [ ] 로그에 민감 정보가 출력되지 않는가?

---

## 🚨 보안 사고 발생 시

1. **즉시 서비스 중단** (필요시)
2. **모든 비밀번호/Secret 변경**
3. **사용자에게 비밀번호 변경 안내**
4. **침해 범위 파악 및 로그 분석**
5. **보안 패치 적용**
6. **사고 보고서 작성**

---

## 📞 문의

보안 취약점 발견 시: security@your-domain.com (Private하게 보고)