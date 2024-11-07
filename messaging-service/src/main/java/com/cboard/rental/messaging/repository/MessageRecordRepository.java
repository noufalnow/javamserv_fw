package com.cboard.rental.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cboard.rental.messaging.entity.MessageRecord;

public interface MessageRecordRepository extends JpaRepository<MessageRecord, Long> {
	boolean existsByShdIdAndTopics(Long shdId, String topics);
}
