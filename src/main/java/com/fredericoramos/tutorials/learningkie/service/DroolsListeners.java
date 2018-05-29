package com.loconoco.tutorials.learningkie.service;

import org.kie.api.event.rule.*;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public final class DroolsListeners {


    public List<RuleMatchEvent> rulesMatched = new LinkedList<>();
    public List<FactEvent> objectsInWM = new LinkedList<>();


    public final AgendaEventListener AGENDA_LISTENER = new AgendaEventListener() {

        @Override
        public void matchCreated(MatchCreatedEvent matchCreatedEvent) {
            String ruleName = matchCreatedEvent.getMatch().getRule().getName();
            rulesMatched.add(new RuleMatchEvent(rulesMatched.size(), ruleName, "added to agenda"));
        }

        @Override
        public void matchCancelled(MatchCancelledEvent matchCancelledEvent) {
            String ruleName = matchCancelledEvent.getMatch().getRule().getName();
            rulesMatched.add(new RuleMatchEvent(rulesMatched.size(), ruleName, "removed to agenda"));
        }

        @Override
        public void beforeMatchFired(BeforeMatchFiredEvent beforeMatchFiredEvent) {
            // not for now
        }

        @Override
        public void afterMatchFired(AfterMatchFiredEvent afterMatchFiredEvent) {
            // not for now
        }

        @Override
        public void agendaGroupPopped(AgendaGroupPoppedEvent agendaGroupPoppedEvent) {
            // not for now
        }

        @Override
        public void agendaGroupPushed(AgendaGroupPushedEvent agendaGroupPushedEvent) {
            // not for now
        }

        @Override
        public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {
            // not for now
        }

        @Override
        public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent ruleFlowGroupActivatedEvent) {
            // not for now
        }

        @Override
        public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
            // not for now
        }

        @Override
        public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent ruleFlowGroupDeactivatedEvent) {
            // not for now
        }
    };

    public final RuleRuntimeEventListener RUNTIME_LISTENER = new RuleRuntimeEventListener() {

        @Override
        public void objectInserted(ObjectInsertedEvent objectInsertedEvent) {
            long instanceId = objectInsertedEvent.getObject().hashCode();
            String className = objectInsertedEvent.getObject().getClass().getName();
            String content = objectInsertedEvent.getObject().toString();
            objectsInWM.add(new FactEvent(objectsInWM.size(), instanceId+"", className, "inserted into WM", content));
        }

        @Override
        public void objectUpdated(ObjectUpdatedEvent objectUpdatedEvent) {
            String className = objectUpdatedEvent.getObject().getClass().getName();
            long instanceId = objectUpdatedEvent.getObject().hashCode();
            String content = objectUpdatedEvent.getObject().toString();
            objectsInWM.add(new FactEvent(objectsInWM.size(), instanceId+"", className, "updated inside WM", content));
        }

        @Override
        public void objectDeleted(ObjectDeletedEvent objectDeletedEvent) {
            String className = objectDeletedEvent.getOldObject().getClass().getName();
            long instanceId = objectDeletedEvent.getOldObject().hashCode();
            String content = objectDeletedEvent.getOldObject().toString();
            objectsInWM.add(new FactEvent(objectsInWM.size(), instanceId+"", className, "remove from WM", content));
        }
    };

}

class RuleMatchEvent {
    private int index;
    private String ruleName;
    private String action;

    public RuleMatchEvent(int index, String rule, String action) {
        this.index = index;
        this.ruleName = rule;
        this.action = action;
    }

    @Override
    public String toString() {
        return "(#" + this.index + ") rule '" + this.ruleName + "' was " + this.action;
    }
}

class FactEvent {
    private int index;
    private String instanceId;
    private String className;
    private String action;
    private String content;

    public FactEvent(int index, String instanceId, String rule, String action, String content) {
        this.index = index;
        this.instanceId = instanceId;
        this.className = rule;
        this.action = action;
        this.content = content;
    }

    @Override
    public String toString() {
        return "(#" + this.index + ") instance " + this.instanceId + " from class '" + this.className + "' was " +
                this.action + System.getProperty("line.separator") +
                this.content;
    }
}