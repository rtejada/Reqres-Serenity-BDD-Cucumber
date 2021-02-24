package com.testautomation.mesaj.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateUser implements Task {

    private final Object updateUserInfo;
    //private final int page;


    public UpdateUser(Object updateUserInfo){
        this.updateUserInfo = updateUserInfo;
    }

    public static Performable withInfo(Object updateUserInfo){
        return instrumented(RegisterUser.class, updateUserInfo);
    }

    public static Performable withUser(int page){
        return instrumented(GetUsers.class, page);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/users/2")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .body(updateUserInfo)
                        )
        );

    }
}
