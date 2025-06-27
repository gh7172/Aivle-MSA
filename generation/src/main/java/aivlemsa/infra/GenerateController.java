package aivlemsa.infra;

import aivlemsa.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/집필요청")
@Transactional
public class GenerateController {

    @Autowired
    GenerateRepository GenerateRepository;

    @RequestMapping(
        value = "/집필요청/requestbookpublication",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Generate requestBookPublication(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestBookPublicationCommand requestBookPublicationCommand
    ) throws Exception {
        System.out.println(
            "##### /집필요청/requestBookPublication  called #####"
        );
        Generate Generate = new Generate();
        Generate.requestBookPublication(requestBookPublicationCommand);
        GenerateRepository.save(Generate);
        return Generate;
    }
}
//>>> Clean Arch / Inbound Adaptor
