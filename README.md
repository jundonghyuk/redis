# 🔥 Redis 완전 정복 가이드

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
      <h3>🏗️ 기초</h3>
    </td>
    <td>
      <h4><a href="#1-redis-특징-및-아키텍처">1. Redis 특징 및 아키텍처</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>📊 자료구조</h3>
    </td>
    <td>
      <h4><a href="#2-redis-자료구조">2. Redis 자료구조</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>🌐 클러스터링</h3>
    </td>
    <td>
      <h4><a href="#3-redis-구조">3. Redis 구조</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="3" align="center">
      <h2>💼 실무 활용 사례</h2>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>💨 캐싱</h3>
    </td>
    <td>
      <h4><a href="#4-redis-캐시-활용-사례">4. Redis 캐시</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>🔐 세션</h3>
    </td>
    <td>
      <h4><a href="#5-redis-세션-활용-사례">5. Redis 세션</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>📨 메시징</h3>
    </td>
    <td>
      <h4><a href="#6-redis-메시지-브로커-활용-사례">6. Redis 메시지 브로커</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>🔒 분산락</h3>
    </td>
    <td>
      <h4><a href="#7-redis-락--redisson-활용-사례">7. Redis 락 & Redisson</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
</table>

<table>
  <tr>
    <td colspan="3" align="center">
      <h2>⚙️ 고급 기능</h2>
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>🔄 트랜잭션</h3>
    </td>
    <td>
      <h4><a href="#8-redis-트랜잭션">8. Redis 트랜잭션</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <h3>💾 영속성</h3>
    </td>
    <td>
      <h4><a href="#9-aof-append-only-file">9. AOF (Append Only File)</a></h4>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/완료-0%25-lightgrey" />
    </td>
  </tr>
</table>

---

## 🛠️ 개발 환경

<div align="center">

| 환경 | 버전       | 설명 |
|------|----------|------|
| **Redis** | `7.2.4`  | 메인 학습 버전 |
| **Java** | `17`     | Spring Boot 예제용 |
| **Spring Boot** | `3.5.5`  | 실습 프레임워크 |
| **Docker** | `latest` | 로컬 개발 환경 |

</div>

---

## 📝 학습 노트

> 각 주제별로 학습한 내용을 정리하고, 실습 코드와 함께 기록합니다.
