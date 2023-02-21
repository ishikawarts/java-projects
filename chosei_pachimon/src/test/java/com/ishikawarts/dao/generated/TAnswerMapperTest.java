package com.ishikawarts.dao.generated;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ishikawarts.dao.generated.entity.TAnswerExample;
import com.ishikawarts.dao.generated.mapper.TAnswerMapper;
import com.ishikawarts.foundation.BaseTest;

public class TAnswerMapperTest extends BaseTest {

	@Autowired
	private TAnswerMapper tAnswerMapper;

	@Test
	void test() {
		var entity = tAnswerMapper.selectByPrimaryKey("TEST", "田中", "2/22(水) 19:00〜");

		assertEquals("TEST", entity.getEventId());
		assertEquals("田中", entity.getUserName());
		assertEquals("2/22(水) 19:00〜", entity.getProposedDatetime());
		assertEquals("1", entity.getAnswer());
		assertEquals("TEST_USER", entity.getUpdateUser());
	}

	@Test
	void test1() {
		
		var example = new TAnswerExample();
		
		example.createCriteria()
				.andEventIdEqualTo("TEST")
				.andUserNameEqualTo("田中");
		example.setOrderByClause("PROPOSED_DATETIME ASC");

		var entities = tAnswerMapper.selectByExample(example);
		
		assertEquals(3, entities.size());
		
		assertEquals("TEST", entities.get(0).getEventId());
		assertEquals("田中", entities.get(0).getUserName());
		assertEquals("2/22(水) 19:00〜", entities.get(0).getProposedDatetime());
		assertEquals("1", entities.get(0).getAnswer());
		assertEquals("TEST_USER", entities.get(0).getUpdateUser());
		
		assertEquals("TEST", entities.get(1).getEventId());
		assertEquals("田中", entities.get(1).getUserName());
		assertEquals("2/23(木) 19:00〜", entities.get(1).getProposedDatetime());
		assertEquals("2", entities.get(1).getAnswer());
		assertEquals("TEST_USER", entities.get(1).getUpdateUser());
		
		assertEquals("TEST", entities.get(2).getEventId());
		assertEquals("田中", entities.get(2).getUserName());
		assertEquals("2/24(金) 19:00〜", entities.get(2).getProposedDatetime());
		assertEquals("3", entities.get(2).getAnswer());
		assertEquals("TEST_USER", entities.get(2).getUpdateUser());
	}

}
