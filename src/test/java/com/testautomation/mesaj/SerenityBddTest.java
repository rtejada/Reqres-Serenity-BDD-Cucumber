package com.testautomation.mesaj;

import com.testautomation.mesaj.facts.NetflixPlans;
import com.testautomation.mesaj.models.users.Datum;
import com.testautomation.mesaj.models.users.RegisterUserInfo;
import com.testautomation.mesaj.questions.GetUsersQuestion;
import com.testautomation.mesaj.questions.ResponseCode;
import com.testautomation.mesaj.tasks.GetUsers;
import com.testautomation.mesaj.tasks.RegisterUser;
import com.testautomation.mesaj.tasks.UpdateUser;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abiities.CallAnApi;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityBddTest {

    private final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void initialTest() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        roxanne.attemptsTo(
                GetUsers.fromPage(1)
        );

        roxanne.should(
                seeThat("The status code is: ", new ResponseCode(), equalTo(200))
        );


        Datum user = new GetUsersQuestion().answeredBy(roxanne)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);


        roxanne.should(
                seeThat("The user is not null: ", act -> user, notNullValue())
        );

        roxanne.should(
                seeThat("The user email is:", act -> user.getEmail(), equalTo("george.bluth@reqres.in")),
                seeThat("The user id is:", act -> user.getId(), equalTo(1))
        );
    }

    @Test
    public void registerUserTest() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        String registerUserInfo = "{\n" +
                "\t\"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"email\": \"tracey.ramos@reqres.in\",\n" +
                "    \"password\": \"serenity\"\n" +
                "}";

        roxanne.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)

        );

        roxanne.should(
                seeThat("the status code is: ", new ResponseCode(), equalTo(200))
        );

    }

    @Test
    public void registerUserTest2() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        RegisterUserInfo registerUserInfo = new RegisterUserInfo();

        registerUserInfo.setName("morpheus");
        registerUserInfo.setJob("leader");
        registerUserInfo.setEmail("tracey.ramos@reqres.in");
        registerUserInfo.setPassword("serenity");

        roxanne.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)

        );

        roxanne.should(
                seeThat("The status code is", new ResponseCode(), equalTo(200))
        );
    }

    @Test
    public void updateUserTest(){

        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));
        RegisterUserInfo registerUserInfo = new RegisterUserInfo();

        registerUserInfo.setName("roxanne");
        registerUserInfo.setJob("Software Develop");
        registerUserInfo.setEmail("lindsay.ferguson@reqres.in");
        registerUserInfo.setPassword("serenity");

        roxanne.attemptsTo(
                UpdateUser.withInfo(registerUserInfo)
        );
        roxanne.should(
                seeThat("The response code", new ResponseCode(), Matchers.equalTo(200))
        );

        roxanne.should(
                seeThat("The user email is: ", act -> registerUserInfo.getName(), Matchers.equalTo("roxanne")),
                seeThat("The first name is: ", act -> registerUserInfo.getJob(), Matchers.equalTo("Software Develop"))
        );

    }

    @Test
    public void factTest() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        roxanne.has(NetflixPlans.toViewSeries());
    }
}
