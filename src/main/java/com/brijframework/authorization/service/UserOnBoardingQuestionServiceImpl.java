package com.brijframework.authorization.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.brijframework.authorization.beans.UIUserOnBoardingQuestion;
import com.brijframework.authorization.mapper.GenericMapper;
import com.brijframework.authorization.mapper.UserDetailMapper;
import com.brijframework.authorization.mapper.UserOnBoardingQuestionMapper;
import com.brijframework.authorization.model.EOUserAccount;
import com.brijframework.authorization.model.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.repository.OnBoardingQuestionRepository;
import com.brijframework.authorization.repository.UserAccountRepository;
import com.brijframework.authorization.repository.UserOnBoardingQuestionRepository;
import com.brijframework.rest.crud.service.impl.CrudServiceImpl;

@Service
public class UserOnBoardingQuestionServiceImpl extends CrudServiceImpl<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> implements UserOnBoardingQuestionService {

	@Autowired
	private UserOnBoardingQuestionRepository userOnBoardingQuestionRepository;

	@Autowired
	private OnBoardingQuestionRepository onBoardingQuestionRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserDetailMapper userDetailMapper;
	
	@Autowired
	private UserOnBoardingQuestionMapper userOnBoardingQuestionMapper; 

	@Override
	public JpaRepository<EOUserOnBoardingQuestion, Long> getRepository() {
		return userOnBoardingQuestionRepository;
	}

	@Override
	public GenericMapper<EOUserOnBoardingQuestion, UIUserOnBoardingQuestion> getMapper() {
		return userOnBoardingQuestionMapper;
	}

	@Override
	public void initOnBoarding(EOUserAccount eoUserAccount) {
		if (eoUserAccount == null) {
			return;
		}
		List<EOUserOnBoardingQuestion> eoUserOnBoardingQuestions = userOnBoardingQuestionRepository
				.findByUserAccount(eoUserAccount);
		Map<Long, EOUserOnBoardingQuestion> userOnBoardingQuestionMap = eoUserOnBoardingQuestions.stream()
				.collect(Collectors.toMap(userOnBoardingQuestion -> userOnBoardingQuestion.getQuestion().getId(),
						Function.identity()));
		onBoardingQuestionRepository.findAll().forEach(onBoardingQuestion -> {
			if (!userOnBoardingQuestionMap.containsKey(onBoardingQuestion.getId())) {
				EOUserOnBoardingQuestion eoUserOnBoardingQuestion = new EOUserOnBoardingQuestion();
				eoUserOnBoardingQuestion.setQuestion(onBoardingQuestion);
				eoUserOnBoardingQuestion.setUserAccount(eoUserAccount);
				userOnBoardingQuestionRepository.saveAndFlush(eoUserOnBoardingQuestion);
			}
		});
	}

	@Override
	public UIUserOnBoardingQuestion saveOnBoardingQuestion(UIUserOnBoardingQuestion userOnBoardingQuestion, String username) {
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(username);
		if (!findUserAccount.isPresent()) {
			return null;
		}
		EOUserAccount eoUserAccount = findUserAccount.get();
		EOUserOnBoardingQuestion eoUserOnBoardingQuestion = userDetailMapper.mapToUserOnBoardingQuestionDAO(userOnBoardingQuestion);
		eoUserOnBoardingQuestion.setUserAccount(eoUserAccount);
		eoUserOnBoardingQuestion = userOnBoardingQuestionRepository.saveAndFlush(eoUserOnBoardingQuestion);
		return userDetailMapper.mapToUserOnBoardingQuestionDTO(eoUserOnBoardingQuestion);
	}


}
