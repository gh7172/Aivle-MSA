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
// @RequestMapping(value="/유저")
@Transactional
public class 유저Controller {

    @Autowired
    유저Repository 유저Repository;
}
//>>> Clean Arch / Inbound Adaptor
