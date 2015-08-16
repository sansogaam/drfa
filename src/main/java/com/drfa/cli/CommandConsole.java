package com.drfa.cli;

import com.drfa.jms.JMSConnection;
import com.drfa.jms.Publisher;
import com.drfa.validator.ReconciliationTypeValidator;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;

import javax.jms.*;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by Sanjiv on 2/18/2015.
 */
public class CommandConsole implements Publisher{

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
    @Override
    public void publisher(String message, String queueName) throws JMSException {
        Session session = JMSConnection.createSession();
        Destination dest = new QueueImpl(queueName);
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(session.createTextMessage(message));
    }
    
    public String convertAnswerToString(Answer answer){
        XStream xst = new XStream();
        String xmlString  = xst.toXML(answer);
        return xmlString;
    }
    
    public static void main(String args[]) throws JMSException {
        CommandConsole commandConsole = new CommandConsole();
        //commandConsole.manualRunProgram();
        commandConsole.askQuestions();
    }

    private void manualRunProgram() throws JMSException {
        Answer answer = new Answer();
        answer.setKeyIndex(0);
        answer.setBaseKeyIndex("0");
        answer.setTargetKeyIndex("0");
        answer.setReconciliationType("FILE");
        answer.setBaseFile("D:/dev/test.csv");
        answer.setFileDelimiter("|");
        answer.setTargetFile("D:/dev/test1.csv");
        answer.setMetaDataFile("D:/dev/drfa/src/test/resources/rec-test.fmt");
        answer.setPluginPath("D:/dev");
        answer.setTypeOfReport("HTML");
        answer.setReportCategory("BOTH");
        answer.setReportOutputPath("D:/dev");
        CommandConsole commandConsole = new CommandConsole();
        String answerString = commandConsole.convertAnswerToString(answer);
        LOG.info(String.format("Answer string to be published %s", answerString));
        commandConsole.publisher(answerString,"queue://REC_ANSWER");
    }

}
