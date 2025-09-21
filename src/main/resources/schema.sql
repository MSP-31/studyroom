-- GIST 인덱스 기능을 위해 확장 설치
CREATE EXTENSION IF NOT EXISTS btree_gist;

-- 테이블이 없으면 생성
CREATE TABLE IF NOT EXISTS reservation (
  id SERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  rooms_id BIGINT NOT NULL,
  start_at TIMESTAMP NOT NULL,
  end_at TIMESTAMP NOT NULL,
  time_range tstzrange NOT NULL
);

-- JPA에서는 해당 제약 조건을 처리하지 못하기 때문에 SQL문으로 따로 처리
ALTER TABLE reservation
ADD EXCLUDE USING gist (
  rooms_id WITH =,
  time_range WITH &&
);
