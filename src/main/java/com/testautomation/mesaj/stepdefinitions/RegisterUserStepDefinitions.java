package com.testautomation.mesaj.stepdefinitions;

import com.testautomation.mesaj.models.users.RegUserInfo;
import com.testautomation.mesaj.questions.ResponseCode;
import com.testautomation.mesaj.tasks.RegisterUser;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abiities.CallAnApi;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SerenityRunner.class)
public class RegisterUserStepDefinitions {

    private final String BASEURL = "http://127.0.0.1:5000/api";
    Actor roxanne;

    @Given("Roxanne is a client and needs manage her products")
    public void roxanneIsAClientAndNeedsManageHerProducts() {
        roxanne = Actor.named("Roxanne the trainer")
                .whoCan(CallAnApi.at(BASEURL));

    }

    @When("She sends the required information to register")
    public void sheSendsTheRequiredInformationToRegister() {

        RegUserInfo regUserInfo = new RegUserInfo();

        regUserInfo.setName("morpheus");
        regUserInfo.setJob("leader");
        regUserInfo.setEmail("tracey.ramos@reqres.in");
        regUserInfo.setPassword("serenity");

        roxanne.attemptsTo(
                RegisterUser.withInfo(regUserInfo)

        );

    }


    @Then("She must obtains a virtual account in order to log in as required")
    public void sheMustObtainsAVirtualAccountInOrderToLogInAsRequired() {

        roxanne.should(
                seeThat("The status code is", new ResponseCode(), equalTo(200))
        );
    }
}
