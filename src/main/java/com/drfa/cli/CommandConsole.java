package com.drfa.cli;

import com.drfa.jms.ActiveMqPublisher;
import com.drfa.util.DrfaProperties;
import com.drfa.validator.ReconciliationTypeValidator;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import java.io.File;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class CommandConsole {

    static Logger LOG = Logger.getLogger(CommandConsole.class);

    public void askQuestions() throws JMSException {
        System.out.println( ansi().eraseScreen().fg(RED).a("Welcome to reconciliation tool"));
        String typeOfReconciliation = new DisplayQuestion(new ReconciliationTypeValidator()).displayQuestion("Enter the reconciliation type (FILE, DATABASE)");

        LOG.info("Type of reconciliation: " + typeOfReconciliation);

        QuestionFactory questionFactory = new QuestionFactory();
        Questions questions = questionFactory.getQuestion(typeOfReconciliation);
        Answer answer = questions.askQuestions();
        answer.setReconciliationType(typeOfReconciliation);
        LOG.info("Answers received.." + answer);
        String answerString = convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        publisher(answerString, "queue://REC_ANSWER");
    }
    public void publisher(String message, String queueName) throws JMSException {
        ActiveMqPublisher mqPublisher = new ActiveMqPublisher();
        mqPublisher.sendMsg(message, queueName, DrfaProperties.BROKER_URL);
    }
    
    public String convertAnswerToString(Answer answer){
        XStream xst = new XStream();
        String xmlString  = xst.toXML(answer);
        return xmlString;
    }
    
    public static void main(String args[]) throws JMSException {
        CommandConsole commandConsole = new CommandConsole();
        commandConsole.manualRunProgram();
        //commandConsole.manualRunDBProgram();
        //commandConsole.askQuestions();
    }

    
    private void manualRunProgram() throws JMSException {
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setProcessId(1);
        answer.setBaseFile(new File("src/test/resources/test.csv").getAbsolutePath());
        answer.setFileDelimiter("|");
        answer.setTargetFile(new File("src/test/resources/test1.csv").getAbsolutePath());
        answer.setMetaDataFile(new File("src/test/resources/rec-test.fmt").getAbsolutePath());
        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());
        answer.setTypeOfReport("HTML");
        answer.setReportCategory("BOTH");
        answer.setReportOutputPath(new File("src/test/resources").getAbsolutePath());
        CommandConsole commandConsole = new CommandConsole();
        String answerString = commandConsole.convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        commandConsole.publisher(answerString,"queue://REC_ANSWER");
    }

    private void manualRunDBProgram() throws JMSException {
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("DATABASE");
        answer.setProcessId(1);
        
        answer.setBaseDatabaseCredentialFile(new File("src/test/resources/mysql-base.cfg").getAbsolutePath());
        answer.setBaseDatabaseFile("C:/Temp");
        answer.setBaseDatabaseType("MYSQL");

        answer.setTargetDatabaseCredentialFile(new File("src/test/resources/mysql-target.cfg").getAbsolutePath());
        answer.setTargetDatabaseCredentialFile("C:/Temp");
        answer.setTargetDatabaseType("MYSQL");

        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());

        answer.setMetaDataFile(new File("src/test/resources/rec-db-test.fmt").getAbsolutePath());

        CommandConsole commandConsole = new CommandConsole();
        String answerString = commandConsole.convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        commandConsole.publisher(answerString,"queue://REC_ANSWER");
    }

}
