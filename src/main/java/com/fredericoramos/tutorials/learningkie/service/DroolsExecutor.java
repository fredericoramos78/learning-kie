package com.fredericoramos.tutorials.learningkie.service;

import com.fredericoramos.tutorials.learningkie.model.CurrentHour;
import com.fredericoramos.tutorials.learningkie.model.Person;
import org.kie.api.conf.KieBaseOption;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;


@Service
public class DroolsExecutor {


    private static final Logger LOG = LoggerFactory.getLogger(DroolsExecutor.class);

    private static final Collection<String> RULES = new LinkedList<>();

    static {
        RULES.add("/rules/package.drl");
        RULES.add("/rules/count-people.drl");
        RULES.add("/rules/determine-speech.drl");
        RULES.add("/rules/greet-formal-morning.drl");
        RULES.add("/rules/greet-formal-afternoon.drl");
        RULES.add("/rules/greet-formal-evening.drl");
        RULES.add("/rules/greet-informal-few.drl");
        RULES.add("/rules/greet-informal-many.drl");
    }


    @Autowired
    private DroolsListeners simulatorListeners;

    // drools session and listeners
    private KieSession session;

    private GreetingsResult greetingsResult;



    public void run(Collection<Person> people, CurrentHour now) {

        try {
            // boot up KIE
            this.loadRules();
            // assert facts
            for (Person p : people) { this.session.insert(p); }
            this.session.insert(now);
            // prepare and run
            this.prepareSession();
            this.fireRules();
        } catch (Exception e) {
            LOG.error("Error running drools:", e);
        } finally {
            // dump results to log
            this.dumpExecutionData();
        }
    }

    private void loadRules() {
        KieHelper kieHelper = new KieHelper();
        kieHelper.setClassLoader(ClassLoader.getSystemClassLoader());
        kieHelper.ks.newReleaseId("com.loconoco.tutorials", "learning-kie", "0.0.1-SNAPSHOT");

        for (String eachRule : RULES) {
            kieHelper.addFromClassPath(eachRule);
        }
        this.session = kieHelper.build(new KieBaseOption[0]).newKieSession();
        this.session.addEventListener(simulatorListeners.AGENDA_LISTENER);
        this.session.addEventListener(simulatorListeners.RUNTIME_LISTENER);
    }

    private void prepareSession() {
        this.greetingsResult = new GreetingsResult();
        this.session.setGlobal("greetingsResult", this.greetingsResult);
    }

    private void fireRules() {
        session.fireAllRules();
    }


    private void dumpExecutionData() {
        LOG.info("The greeting message should be ==> " + this.greetingsResult.toString());
        LOG.debug("----------------- STARTING DUMP OF EXECUTION STACKS -------------------");
        this.simulatorListeners.rulesMatched.forEach(event -> LOG.debug(event.toString()) );
        this.simulatorListeners.objectsInWM.forEach(event -> LOG.debug(event.toString()) );
    }
}

