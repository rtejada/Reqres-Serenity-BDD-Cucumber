package com.testautomation.mesaj.questions;

import com.testautomation.mesaj.models.register.RegResponse;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class PostResponse implements Question {

    public static Question<Object> was(){
        return new PostResponse();
    }


    @Override
    public RegResponse answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(RegResponse.class);
    }
}