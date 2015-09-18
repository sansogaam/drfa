package com.drfa.cli;

import com.drfa.messaging.MessagePublisher;
import com.drfa.util.DrfaProperties;
import com.drfa.validator.ReconciliationTypeValidator;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import java.io.File;
import java.util.Date;

import static com.drfa.engine.EngineConstants.BASE_THREAD_NAME;
import static com.drfa.engine.EngineConstants.TARGET_THREAD_NAME;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class CommandConsole {

    static Logger LOG = Logger.getLogger(CommandConsole.class);

    public static void main(String args[]) throws JMSException {
        CommandConsole commandConsole = new CommandConsole();
        //commandConsole.manualRunProgram();
        //commandConsole.manualRunDBProgram();
        commandConsole.askQuestions();
    }

    public void askQuestions() throws JMSException {
        System.out.println(ansi().eraseScreen().fg(RED).a("Welcome to reconciliation tool"));
        String typeOfReconciliation = new DisplayQuestion(new ReconciliationTypeValidator()).displayQuestion("Enter the reconciliation type (FILE, DATABASE)");
        LOG.info("Type of reconciliation: " + typeOfReconciliation);
        QuestionFactory questionFactory = new QuestionFactory();
        Questions questions = questionFactory.getQuestion(typeOfReconciliation);
        Answer answer = questions.askQuestions();
        answer.setReconciliationType(typeOfReconciliation);
        LOG.info("Answers received.." + answer);
        String answerString = convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        publisher(answerString, DrfaProperties.REC_ANSWER);
    }

    public void publisher(String message, String queueName) {
        new MessagePublisher().publish(message, queueName);
    }

    public String convertAnswerToString(Answer answer) {
        XStream xst = new XStream();
        String xmlString = xst.toXML(answer);
        return xmlString;
    }

    private void manualRunProgram() throws JMSException {
        Answer answer = new Answer();
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setProcessId(1);
        answer.setBaseFile(new File("src/test/resources/test.csv").getAbsolutePath());
        answer.setFileDelimiter("|");
        answer.setTargetFile(new File("src/test/resources/test1.csv").getAbsolutePath());
        answer.setMetaDataFile(new File("src/test/resources/reconciliation-input.xml").getAbsolutePath());
        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());
        publishMessage(answer);
    }

    public void publishMessage(Answer answer) throws JMSException {
        String answerString = convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        publisher(answerString, DrfaProperties.REC_ANSWER);
    }

    private void manualRunDBProgram() throws JMSException {
        Answer answer = new Answer();

        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("DATABASE");
        answer.setProcessId(1);
        answer.setFileDelimiter("|");

        answer.setBaseDatabaseCredentialFile(new File("src/test/resources/mysql-base.cfg").getAbsolutePath());
        answer.setBaseDatabaseFile("target/test-output/");
        String baseOutputFile = answer.getBaseDatabaseFile() + File.separator + BASE_THREAD_NAME + "-" + new Date().getTime() + ".csv";
        answer.setBaseFile(baseOutputFile);


        answer.setBaseDatabaseType("MYSQL");
        answer.setSqlQueryBase("SELECT ID, first_name, last_name, email_address,DATE_FORMAT(date_of_joining,'%d/%m/%Y') as date_of_joining FROM EMPLOYEE");
        answer.setBaseDatabaseMetaDataFile(new File("src/test/resources/rec-db-base.fmt").getAbsolutePath());

        answer.setTargetDatabaseCredentialFile(new File("src/test/resources/mysql-target.cfg").getAbsolutePath());

        answer.setTargetDatabaseFile("target/test-output/");
        String targetOutputFile = answer.getTargetDatabaseFile() + File.separator + TARGET_THREAD_NAME + "-" + new Date().getTime() + ".csv";
        answer.setTargetFile(targetOutputFile);

        answer.setTargetDatabaseType("MYSQL");
        answer.setSqlQueryTarget("SELECT ID,name,address, email_detail, DATE_FORMAT(joining_date,'%d/%m/%Y') as joining_date FROM PERSON");
        answer.setTargetDatabaseMetaDataFile(new File("src/test/resources/rec-db-target.fmt").getAbsolutePath());

        answer.setPluginPath(new File("src/main/resources/plugins").getAbsolutePath());

        answer.setMetaDataFile(new File("src/test/resources/reconciliation-input.xml").getAbsolutePath());

        CommandConsole commandConsole = new CommandConsole();
        String answerString = commandConsole.convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        commandConsole.publisher(answerString, DrfaProperties.REC_ANSWER);
    }

}
