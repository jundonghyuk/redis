# 🔥 Redis 학습

<div align="center">

![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Status](https://img.shields.io/badge/Status-Learning-brightgreen?style=for-the-badge)
![Progress](https://img.shields.io/badge/Progress-0%25-red?style=for-the-badge)

**고성능 인메모리 데이터 구조 저장소 Redis 학습 저장소**

</div>

---

## 🎯 학습 목표

> Redis의 핵심 개념부터 실무 활용까지, 체계적으로 학습하고 정리하는 공간입니다.

---

## 📚 학습 목차

<table>
  <tr>
    <td colspan="5" align="center">
      <h2>📐 이론 및 아키텍처</h2>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>기초</h3>
    </td>
    <td width="35%">
      <h4><a href="#1-redis-정의-및-특징">1. Redis 정의 및 특징</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_01_definition/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_01_definition/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_01_definition/RedisConnectionFactoryTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>자료구조</h3>
    </td>
    <td width="35%">
      <h4><a href="#2-redis-자료-구조">2. Redis 자료 구조</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_02_datatype/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_02_datatype/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_02_datatype/DataTypeTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>클러스터링</h3>
    </td>
    <td width="35%">
      <h4><a href="#3-redis-구조">3. Redis 클러스터링 구조</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_03_clustering/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_03_clustering/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_03_clustering/ClusteringTest.java">🧪 테스트</a>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="5" align="center">
      <h2>💼 실무 활용 사례</h2>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>캐싱</h3>
    </td>
    <td width="35%">
      <h4><a href="#4-redis-캐시-활용-사례">4. Redis 캐싱 활용</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_04_caching/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_04_caching/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_04_caching/CachingTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>메시징</h3>
    </td>
    <td width="35%">
      <h4><a href="#5-redis-메시지-큐-활용-사례">5. Redis 메시지 큐</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_05_messaging/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_05_messaging/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_05_messaging/MessageQueueTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>발행-구독</h3>
    </td>
    <td width="35%">
      <h4><a href="#6-redis-pub-sub-활용-사례">6. Redis 발행-구독</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_06_pubsub/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_06_pubsub/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_06_pubsub/PubSubTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>분산락</h3>
    </td>
    <td width="35%">
      <h4><a href="#7-redis-분산락-활용-사례">7. Redis 분산락 & Redisson</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_07_lock/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_07_lock/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_07_lock/DistributedLockTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>속도제한</h3>
    </td>
    <td width="35%">
      <h4><a href="#8-redis-RateLimiter--활용-사례">8. Redis 요청 제한</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_08_ratelimit/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_08_ratelimit/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_08_ratelimit/RateLimiterTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>분석-계산</h3>
    </td>
    <td width="35%">
      <h4><a href="#9-redis-분석-계산-활용-사례">9. Redis 분석 & 계산</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_09_analytics/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_09_analytics/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_09_analytics/AnalyticsTest.java">🧪 테스트</a>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="5" align="center">
      <h2>⚙️ 고급 기능</h2>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>트랜잭션</h3>
    </td>
    <td width="35%">
      <h4><a href="#10-redis-트랜잭션">10. Redis 트랜잭션</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_10_transactions/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_10_transactions/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_10_transactions/TransactionTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center" width="15%">
      <h3>영속성</h3>
    </td>
    <td width="35%">
      <h4><a href="#11-aof-append-only-file">11. Redis 영속성 & AOF</a></h4>
    </td>
    <td align="center" width="16%">
      <a href="./src/main/java/studying/redis/_11_persistence/redis-core.md">📖 레디스 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/main/java/studying/redis/_11_persistence/redis-spring-integration.md">📖 스프링 통합 학습 문서</a>
    </td>
    <td align="center" width="17%">
      <a href="./src/test/java/studying/redis/_11_persistence/PersistenceTest.java">🧪 테스트</a>
    </td>
  </tr>
</table>

---

## 🛠️ 개발 환경

<div align="center">

| 환경              | 버전       | 설명              |
|-----------------|----------|-----------------|
| **Redis**       | `7.2.4`  | 메인 학습 버전        |
| **Java**        | `17`     | Spring Boot 예제용 |
| **Spring Boot** | `3.5.5`  | 실습 프레임워크        |
| **Docker**      | `latest` | 로컬 개발 환경        |

</div>

---

## 📝 학습 노트

> 각 주제별로 학습한 내용을 정리하고, 실습 코드와 함께 기록합니다.