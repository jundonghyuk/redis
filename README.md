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
    <td align="center">
      <h3> 기초</h3>
    </td>
    <td>
      <h4><a href="#1-redis-정의-및-특징">1. Redis 정의 및 특징</a></h4>
    </td>
    <td align="center">
      <a href="./src/main/java/studying/redis/_01_definition/redis-core.md">📖 레디스 코어 문서</a>
    </td>
    <td align="center">
      <a href="./src/main/java/studying/redis/_01_definition/redis-core.md">📖 스프링 통합 문서</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_01_definition/RedisConnectionFactoryTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>자료구조</h3>
    </td>
    <td>
      <h4><a href="#2-redis-자료-구조">2. Redis 자료 구조</a></h4>
    </td>
    <td align="center">
      <a href="./docs/02-redis-data-structures.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_02_datastructures/DataStructuresTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>클러스터링</h3>
    </td>
    <td>
      <h4><a href="#3-redis-구조">3. Redis 구조</a></h4>
    </td>
    <td align="center">
      <a href="./docs/03-redis-clustering.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_03_clustering/ClusteringTest.java">🧪 테스트</a>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="4" align="center">
      <h2>💼 실무 활용 사례</h2>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>캐싱</h3>
    </td>
    <td>
      <h4><a href="#4-redis-캐시-활용-사례">4. Redis Caching</a></h4>
    </td>
    <td align="center">
      <a href="./docs/04-redis-caching.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_04_caching/CachingTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>메시징</h3>
    </td>
    <td>
      <h4><a href="#5-redis-메시지-큐-활용-사례">5. Redis Message Queue</a></h4>
    </td>
    <td align="center">
      <a href="./docs/05-redis-message-queue.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_05_messaging/MessageQueueTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>발행-구독 </h3>
    </td>
    <td>
      <h4><a href="#6-redis-pub-sub-활용-사례">6. Redis Pub/Sub</a></h4>
    </td>
    <td align="center">
      <a href="./docs/06-redis-pubsub.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_06_pubsub/PubSubTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>분산락</h3>
    </td>
    <td>
      <h4><a href="#7-redis-분산락-활용-사례">7. Redis Distributed Lock & Redissson</a></h4>
    </td>
    <td align="center">
      <a href="./docs/07-redis-distributed-lock.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_07_lock/DistributedLockTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>속도제한</h3>
    </td>
    <td>
      <h4><a href="#8-redis-RateLimiter--활용-사례">8. Redis Rate Limiter</a></h4>
    </td>
    <td align="center">
      <a href="./docs/08-redis-rate-limiter.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_08_ratelimit/RateLimiterTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>분석-계산</h3>
    </td>
    <td>
      <h4><a href="#9-redis-분석-계산-활용-사례">9. Redis Analysis/Calculation</a></h4>
    </td>
    <td align="center">
      <a href="./docs/09-redis-analytics.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_09_analytics/AnalyticsTest.java">🧪 테스트</a>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="4" align="center">
      <h2>⚙️ 고급 기능</h2>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>트랜잭션</h3>
    </td>
    <td>
      <h4><a href="#10-redis-트랜잭션">10. Redis 트랜잭션</a></h4>
    </td>
    <td align="center">
      <a href="./docs/10-redis-transactions.md">📖 가이드</a>
    </td>
    <td align="center">
      <a href="./src/test/java/studying/redis/_10_transactions/TransactionTest.java">🧪 테스트</a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>영속성</h3>
    </td>
    <td>
      <h4><a href="#11-aof-append-only-file">11. AOF (Append Only File)</a></h4>
    </td>
    <td align="center">
      <a href="./docs/11-redis-persistence.md">📖 가이드</a>
    </td>
    <td align="center">
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