package aivlemsa.infra;

import aivlemsa.domain.*;
import java.util.Optional;
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
public class 집필요청Controller {

    @Autowired
    집필요청Repository 집필요청Repository;

    @RequestMapping(
        value = "/집필요청/requestbookpublication",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public 집필요청 requestBookPublication(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestBookPublicationCommand requestBookPublicationCommand
    ) throws Exception {
        System.out.println(
            "##### /집필요청/requestBookPublication  called #####"
        );
        집필요청 집필요청 = new 집필요청();
        집필요청.requestBookPublication(requestBookPublicationCommand);
        집필요청Repository.save(집필요청);
        return 집필요청;
    }
}
//>>> Clean Arch / Inbound Adaptor
