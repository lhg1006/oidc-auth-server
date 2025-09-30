#!/bin/bash

# OIDC Server 실행 스크립트
# .env 파일에서 환경 변수를 로드하고 서버를 시작합니다

set -e

# .env 파일 존재 확인
if [ ! -f .env ]; then
    echo "❌ 오류: .env 파일이 없습니다!"
    echo ""
    echo "다음 명령을 실행하여 .env 파일을 생성하세요:"
    echo "  cp .env.example .env"
    echo "  nano .env  # 실제 비밀번호로 변경"
    echo ""
    exit 1
fi

# .env 파일에서 환경 변수 로드
echo "📋 환경 변수 로드 중..."
export $(cat .env | grep -v '^#' | xargs)

# 필수 환경 변수 확인
REQUIRED_VARS=(
    "SPRING_DATASOURCE_USERNAME"
    "SPRING_DATASOURCE_PASSWORD"
    "OAUTH2_CLIENT_SECRET"
)

for VAR in "${REQUIRED_VARS[@]}"; do
    if [ -z "${!VAR}" ]; then
        echo "❌ 오류: $VAR 환경 변수가 설정되지 않았습니다!"
        echo ".env 파일을 확인하세요."
        exit 1
    fi
done

echo "✅ 환경 변수 로드 완료"
echo ""
echo "🚀 서버 시작 중..."
echo "   - Database: ${SPRING_DATASOURCE_URL:-jdbc:postgresql://localhost:5432/oidc_auth}"
echo "   - Username: $SPRING_DATASOURCE_USERNAME"
echo ""

# Gradle bootRun 실행
./gradlew bootRun