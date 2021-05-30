package com.testautomation.mesaj;

import com.testautomation.mesaj.facts.NetflixPlans;
import com.testautomation.mesaj.models.register.RegResponse;
import com.testautomation.mesaj.models.users.Datum;
import com.testautomation.mesaj.models.users.RegUserInfo;
import com.testautomation.mesaj.questions.GetQuestion;
import com.testautomation.mesaj.questions.PostResponse;
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
public class SerenityJunitTest {

    private final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void GetUsersTest() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        roxanne.attemptsTo(
                GetUsers.fromPage(1)
        );

        roxanne.should(
                seeThat("The status code is: ", ResponseCode.was(), equalTo(200))
        );


        Datum user = new GetQuestion().answeredBy(roxanne)
                .getData()
                .stream()
                .filter(x -> x.getId() == 1)
                .findFirst().orElse(null);


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
    public void registerUserTestWithModels() {

        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        RegUserInfo regUserInfo = new RegUserInfo();

        regUserInfo.setName("morpheus");
        regUserInfo.setJob("leader");
        regUserInfo.setEmail("tracey.ramos@reqres.in");
        regUserInfo.setPassword("serenity");

        roxanne.attemptsTo(
                RegisterUser.withInfo(regUserInfo)

        );

        roxanne.should(
                seeThat("The status code is", new ResponseCode(), equalTo(200))
        );

         //obtenemos la respuesta, esta respuesta esta mapeada en la clase register>RegResponse
        PostResponse data = new PostResponse();
        RegResponse responseBody = data.answeredBy(roxanne);
        System.out.println(responseBody.getId());
        System.out.println(responseBody.getToken());


    }

    @Test
    public void updateUserTest(){

        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));
        RegUserInfo regUserInfo = new RegUserInfo();

        regUserInfo.setName("roxanne");
        regUserInfo.setJob("Software Develop");
        regUserInfo.setEmail("lindsay.ferguson@reqres.in");
        regUserInfo.setPassword("serenity");

        roxanne.attemptsTo(
                UpdateUser.withInfo(regUserInfo)
        );
        roxanne.should(
                seeThat("The response code", new ResponseCode(), Matchers.equalTo(200))
        );

        roxanne.should(
                seeThat("The user email is: ", act -> regUserInfo.getName(), Matchers.equalTo("roxanne")),
                seeThat("The first name is: ", act -> regUserInfo.getJob(), Matchers.equalTo("Software Develop"))
        );

    }

    @Test
    public void factTest() {
        Actor roxanne = Actor.named("roxanne the trainer")
                .whoCan(CallAnApi.at(restApiUrl));

        roxanne.has(NetflixPlans.toViewSeries());
    }
}
