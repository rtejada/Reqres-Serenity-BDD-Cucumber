package com.testautomation.mesaj.stepdefinitions;

import com.testautomation.mesaj.questions.ResponseCode;
import com.testautomation.mesaj.tasks.RegisterUser;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abiities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterUserStepDefinitions {

    private final String BASEURL = "http://localhost:5000/api";
    Actor roxanne;

    @Given("Roxanne is a client and needs manage her products")
    public void roxanneIsAClientAndNeedsManageHerProducts() {
        roxanne = Actor.named("Julian the trainer")
                .whoCan(CallAnApi.at(BASEURL));
        /**/
    }

    @When("She sends the required information to register")
    public void sheSendsTheRequiredInformationToRegister() {
        String registerUserInfo = "{\n" +
                "\t\"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"email\": \"tracey.ramos@reqres.in\",\n" +
                "    \"password\": \"serenity\"\n" +
                "}";

        roxanne.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)

        );
    }

    @Then("She must obtains a virtual account in order to log in as required")
    public void sheMustObtainsAVirtualAccountInOrderToLogInAsRequired() {
        roxanne.should(
                seeThat("el codigo de respuesta", new ResponseCode(), equalTo(200))
        );
    }
}
