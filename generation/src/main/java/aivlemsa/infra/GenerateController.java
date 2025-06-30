package aivlemsa.infra;

import aivlemsa.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import aivlemsa.config.kafka.KafkaProcessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationEventPublisher;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/generate")
@Transactional
public class GenerateController {

    @Autowired
    GenerateRepository GenerateRepository;

    @Autowired
    GenerateCommandHandler generateCommandHandler;

    @RequestMapping(
        value = "/generate/requestbookpublication",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Generate requestBookPublication(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestBookPublicationCommand requestBookPublicationCommand
    ) throws Exception {
        System.out.println(
            "##### /generate/requestBookPublication  called #####"
        );

        Generate generate = new Generate(
            requestBookPublicationCommand.getBookId(),
            requestBookPublicationCommand.getSummary(),
            requestBookPublicationCommand.getTitle()
        );
        GenerateRepository.save(generate);
        generateCommandHandler.handle(requestBookPublicationCommand);
        return generate;
    }
}
//>>> Clean Arch / Inbound Adaptor
