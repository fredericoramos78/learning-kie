package com.loconoco.tutorials.learningkie;

import com.loconoco.tutorials.learningkie.model.CurrentHour;
import com.loconoco.tutorials.learningkie.model.Person;
import com.loconoco.tutorials.learningkie.service.DroolsExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;


@SpringBootApplication
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
                                     HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.loconoco.tutorials.learningkie"})
@PropertySource(value = {
        "application.properties",
        "application-${spring.profiles.active}.properties" })
public class Main implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);


    @Autowired
    DroolsExecutor droolsExecutor;




    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            LOG.info("Preparing to run tutorial.");
            String filename = "./sample.dat";
            LOG.debug("Loading KIE data from file: " + filename);
            Collection<Person> people = this.readFactsFromFile(filename);
            CurrentHour now = new CurrentHour();
            // running it
            droolsExecutor.run(people, now);
        } catch (Exception e) {
            LOG.error("Error running simulator:", e);
        }
    }


    private Collection<Person> readFactsFromFile(String filename) throws IOException {
//        return null;
        Collection<Person> people = new LinkedList<>();
        ((LinkedList<Person>) people).add(new Person(10, "EN"));
        ((LinkedList<Person>) people).add(new Person(25, "EN"));
        return people;
    }

    private void debugArgsList(ApplicationArguments args) {
        StringBuilder builder = new StringBuilder();
        builder.append("Command line arguments: [");
        for (String arg: args.getSourceArgs()) {
            builder.append(arg).append(", ");
        }
        builder.append("].");
        LOG.debug(builder.toString());
    }

    private void printHelp() {
        System.out.println("Just run! Data should be in sample.dat file and the rules are all in src/main/resources.");
    }
}