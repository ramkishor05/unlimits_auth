package com.brijframework.authorization.global.account.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingAnswer;
import com.brijframework.authorization.account.entities.onboarding.EOUserOnBoardingQuestion;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingAnswer;
import com.brijframework.authorization.account.model.onboarding.UIUserOnBoardingQuestion;
import com.brijframework.authorization.account.repository.UserAccountRepository;
import com.brijframework.authorization.account.repository.UserOnBoardingAnswerRepository;
import com.brijframework.authorization.account.repository.UserOnBoardingQuestionRepository;
import com.brijframework.authorization.constant.RecordStatus;
import com.brijframework.authorization.global.account.mapper.GlobalUserDetailMapper;
import com.brijframework.authorization.global.account.mapper.GlobalUserOnBoardingQuestionMapper;
import com.brijframework.authorization.view.model.onboadring.UIViewOnBoardingOptions;
import com.brijframework.authorization.view.repository.ViewOnBoardingQuestionRepository;

@Service
public class UserOnBoardingQuestionServiceImpl extends CrudServiceImpl<UIUserOnBoardingQuestion, EOUserOnBoardingQuestion, Long> implements UserOnBoardingQuestionService {

	@Autowired
	private UserOnBoardingQuestionRepository userOnBoardingQuestionRepository;
	
	@Autowired
	private UserOnBoardingAnswerRepository userOnBoardingAnswerRepository;

	@Autowired
	private ViewOnBoardingQuestionRepository onBoardingQuestionRepository;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private GlobalUserDetailMapper userDetailMapper;
	
	@Autowired
	private GlobalUserOnBoardingQuestionMapper userOnBoardingQuestionMapper; 

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
				.findAllByUserAccount(eoUserAccount);
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
		Optional<EOUserAccount> findUserAccount = userAccountRepository.findByUsername(username, RecordStatus.ACTIVETED.getStatusIds());
		if (!findUserAccount.isPresent()) {
			return null;
		}
		EOUserAccount eoUserAccount = findUserAccount.get();
		EOUserOnBoardingQuestion eoUserOnBoardingQuestion = userDetailMapper.mapToUserOnBoardingQuestionDAO(userOnBoardingQuestion);
		eoUserOnBoardingQuestion.setUserAccount(eoUserAccount);
		eoUserOnBoardingQuestion = userOnBoardingQuestionRepository.saveAndFlush(eoUserOnBoardingQuestion);
		return userDetailMapper.mapToUserOnBoardingQuestionDTO(eoUserOnBoardingQuestion);
	}
	
	@Override
	public void merge(UIUserOnBoardingQuestion dtoObject, EOUserOnBoardingQuestion entityObject,
			UIUserOnBoardingQuestion updateDtoObject, EOUserOnBoardingQuestion updateEntityObject,
			Map<String, List<String>> headers) {
		List<UIUserOnBoardingAnswer> answers = dtoObject.getAnswers();
		Map<String, EOUserOnBoardingAnswer> valueMap = userOnBoardingAnswerRepository.findAllByQuestionId(updateEntityObject.getId()).stream().collect(Collectors.toMap(EOUserOnBoardingAnswer::getValue, Function.identity()));
		for (UIUserOnBoardingAnswer uiUserOnBoardingAnswer : answers) {
			EOUserOnBoardingAnswer eoUserOnBoardingAnswer= valueMap.getOrDefault(uiUserOnBoardingAnswer.getValue(), new EOUserOnBoardingAnswer());
			eoUserOnBoardingAnswer.setValue(uiUserOnBoardingAnswer.getValue());
			eoUserOnBoardingAnswer.setQuestion(updateEntityObject);
			eoUserOnBoardingAnswer = userOnBoardingAnswerRepository.save(eoUserOnBoardingAnswer);
			valueMap.put(eoUserOnBoardingAnswer.getValue(), eoUserOnBoardingAnswer);
			updateDtoObject.getAnswers().add(userOnBoardingQuestionMapper.toAnswerDTO(eoUserOnBoardingAnswer));
		}
	}
	
	@Override
	public void preUpdate(UIUserOnBoardingQuestion data, Map<String, List<String>> headers) {
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount!=null) {
			EOUserOnBoardingQuestion clientQuestion = userOnBoardingQuestionRepository.findOneByUserAccountIdAndQuestionId(currentAccount.getId(), data.getQuestion().getId());
		    if(clientQuestion!=null) {
		    	data.setId(clientQuestion.getId());
		    }
		}
		super.preUpdate(data, headers);
	}

	@Override
	public List<UIUserOnBoardingQuestion> findAllByUserAccountId(Long userAccountId) {
		return postFetch(userOnBoardingQuestionRepository.findAllByUserAccountId(userAccountId));
	}
	
	@Override
	public List<UIUserOnBoardingQuestion> postFetch(List<EOUserOnBoardingQuestion> findObjects) {
		List<UIUserOnBoardingQuestion> boardingQuestions = super.postFetch(findObjects);
		boardingQuestions.sort((op1,op2)->op1.getQuestion().getOrderSequence().compareTo(op2.getQuestion().getOrderSequence()));
		return boardingQuestions;
	}

	public void postFetch(EOUserOnBoardingQuestion findObject, UIUserOnBoardingQuestion dtoObject) {
		dtoObject.getQuestion().getOptions().sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		Map<String, String> optionMap = dtoObject.getQuestion().getOptions().stream().collect(Collectors.toMap(UIViewOnBoardingOptions::getValue, UIViewOnBoardingOptions::getDesciption));
		dtoObject.getAnswers().forEach(answer->{
			answer.setDesciption(optionMap.get(answer.getValue()));
		});
	}

}
