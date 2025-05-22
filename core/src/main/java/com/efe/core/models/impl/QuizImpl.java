package com.efe.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.cq.export.json.ExporterConstants;
import com.efe.core.models.Quiz;
import com.efe.core.services.DynamicMediaService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

// The QuizImpl Class

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = Quiz.class, resourceType = {
    QuizImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class QuizImpl implements Quiz {          

// Constant Resource Type
public static final String RESOURCE_TYPE = "efe/componentes/quiz";

/** The resource resolver. */
@SlingObject
private ResourceResolver resourceResolver;

@OSGiService
private DynamicMediaService dynamicMediaService; 

// Intro Pane

@ValueMapValue
public String introHeadline;

@ValueMapValue
public String introSubheadline;

@ValueMapValue
public String introCta;

// Question Pane

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


//Answers

    // 0 Selections
    @ValueMapValue
    private String icon;

    @ValueMapValue
    private String headline;

    @ValueMapValue
    private String bodyCopy;

    @ValueMapValue
    private String answerCtaText;

    @ValueMapValue
    private String answerCtaLink;

    // 1-2 Selections
    @ValueMapValue
    private String icon12;

    @ValueMapValue
    private String headline12;

    @ValueMapValue
    private String bodyCopy12;

    @ValueMapValue
    private String answerCtaText12;

    @ValueMapValue
    private String answerCtaLink12;

    // 3-4 Selections
    @ValueMapValue
    private String icon34;

    @ValueMapValue
    private String headline34;

    @ValueMapValue
    private String bodyCopy34;

    @ValueMapValue
    private String answerCtaText34;

    @ValueMapValue
    private String answerCtaLink34;    

    //  5- 6 selections
    @ValueMapValue
    private String icon56;

    @ValueMapValue
    private String headline56;

    @ValueMapValue
    private String bodyCopy56;

    @ValueMapValue
    private String answerCtaText56;

    @ValueMapValue
    private String answerCtaLink56;   
    
    //  7-8 selections
    @ValueMapValue
    private String icon78;

    @ValueMapValue
    private String headline78;

    @ValueMapValue
    private String bodyCopy78;

    @ValueMapValue
    private String answerCtaText78;

    @ValueMapValue
    private String answerCtaLink78;     

    // 9 -10 selections
    @ValueMapValue
    private String icon910;

    @ValueMapValue
    private String headline910;

    @ValueMapValue
    private String bodyCopy910;

    @ValueMapValue
    private String answerCtaText910;

    @ValueMapValue
    private String answerCtaLink910;     






// Intro Getters

public String getIntroHeadline() {
    return introHeadline;
}

public String getIntroSubheadline() {
    return introSubheadline;
}

public String getIntroCta() {
    return introCta;
}

// Question Getters
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


// Answers

    // 0 Selctions
    public String getIcon() {
        if (icon.endsWith(".png"))
            return dynamicMediaService.getDmImagePath(resourceResolver, icon)+"?fmt=png-alpha&bfc=on";
        else
            return dynamicMediaService.getDmImagePath(resourceResolver, icon);
    }

    public String getHeadline() {
        return headline;

    }

    public String getBodyCopy() {
        return bodyCopy;
    }

    public String getAnswerCtaLink() {
        return answerCtaLink;
    }

    public String getAnswerCtaText() {
        return answerCtaText;
    }

   // 1-2 Selctions
   public String getIcon12() {
    if (icon.endsWith(".png"))
        return dynamicMediaService.getDmImagePath(resourceResolver, icon12)+"?fmt=png-alpha&bfc=on";
    else
        return dynamicMediaService.getDmImagePath(resourceResolver, icon12);
    }

    public String getHeadline12() {
        return headline12;

    }

    public String getBodyCopy12() {
        return bodyCopy12;
    }

    public String getAnswerCtaLink12() {
        return answerCtaLink12;
    }

    public String getAnswerCtaText12() {
        return answerCtaText12;
    }    

   // 3-4 Selctions
   public String getIcon34() {
    if (icon.endsWith(".png"))
        return dynamicMediaService.getDmImagePath(resourceResolver, icon34)+"?fmt=png-alpha&bfc=on";
    else
        return dynamicMediaService.getDmImagePath(resourceResolver, icon34);
    }

    public String getHeadline34() {
        return headline34;

    }

    public String getBodyCopy34() {
        return bodyCopy34;
    }

    public String getAnswerCtaLink34() {
        return answerCtaLink34;
    }

    public String getAnswerCtaText34() {
        return answerCtaText34;
    }     


     // 5-6 Selctions
   public String getIcon56() {
    if (icon.endsWith(".png"))
        return dynamicMediaService.getDmImagePath(resourceResolver, icon56)+"?fmt=png-alpha&bfc=on";
    else
        return dynamicMediaService.getDmImagePath(resourceResolver, icon56);
    }

    public String getHeadline56() {
        return headline56;

    }

    public String getBodyCopy56() {
        return bodyCopy56;
    }

    public String getAnswerCtaLink56() {
        return answerCtaLink56;
    }

    public String getAnswerCtaText56() {
        return answerCtaText56;
    }

         // 7-8 Selctions
   public String getIcon78() {
    if (icon.endsWith(".png"))
        return dynamicMediaService.getDmImagePath(resourceResolver, icon78)+"?fmt=png-alpha&bfc=on";
    else
        return dynamicMediaService.getDmImagePath(resourceResolver, icon78);
    }

    public String getHeadline78() {
        return headline78;

    }

    public String getBodyCopy78() {
        return bodyCopy78;
    }

    public String getAnswerCtaLink78() {
        return answerCtaLink78;
    }

    public String getAnswerCtaText78() {
        return answerCtaText78;
    }

    // 9 - 10
    public String getIcon910() {
    if (icon.endsWith(".png"))
        return dynamicMediaService.getDmImagePath(resourceResolver, icon910)+"?fmt=png-alpha&bfc=on";
    else
        return dynamicMediaService.getDmImagePath(resourceResolver, icon910);
    }

    public String getHeadline910() {
        return headline910;

    }

    public String getBodyCopy910() {
        return bodyCopy910;
    }

    public String getAnswerCtaLink910() {
        return answerCtaLink910;
    }

    public String getAnswerCtaText910() {
        return answerCtaText910;
    }    

}






