package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Quiz;
import com.efe.core.models.multifield.QuizAnswer;



// The QuizImpl Class

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = Quiz.class, resourceType = {
    QuizImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class QuizImpl implements Quiz {  

// The answerblock responses from multifield
@Inject
private List<QuizAnswer> quizAnswer;    

// Constant Resource Type
public static final String RESOURCE_TYPE = "efe/componentes/quiz";

@ValueMapValue
public String listFrom;

@ValueMapValue
private String questionOne;

@ValueMapValue
private String questionTwo;

@ValueMapValue
private String questionThree;

@ValueMapValue
private String questionFour;

@ValueMapValue
private String questionFive;

@ValueMapValue
private String questionSix;

@ValueMapValue
private String questionSeven;

@ValueMapValue
private String questionEight;

@ValueMapValue
private String questionNine;

@ValueMapValue
private String questionTen;

@ValueMapValue
private String resultsCta;



public String getQuizLength() {
    return listFrom;
}

public String getQuestion1() {
    return questionOne;
}

public String getQuestion2() {
    return questionTwo;
}

public String getQuestion3() {
    return questionThree;
}

public String getQuestion4() {
    return questionFour;
}

public String getQuestion5() {
    return questionFive;
}

public String getQuestion6() {
    return questionSix;
}

public String getQuestion7() {
    return questionSeven;
}

public String getQuestion8() {
    return questionEight;
}

public String getQuestion9() {
    return questionNine;
}

public String getQuestion10() {
    return questionTen;
}

public String getResultsCta() {
    return resultsCta;
}

public List<QuizAnswer> getQuizAnswer() {
    if (Objects.nonNull(quizAnswer)) {
        return new ArrayList<>(quizAnswer);
    }
    return Collections.emptyList();
}


    
}






