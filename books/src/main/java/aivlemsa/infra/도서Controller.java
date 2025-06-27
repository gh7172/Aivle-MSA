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
// @RequestMapping(value="/도서")
@Transactional
public class 도서Controller {

    @Autowired
    도서Repository 도서Repository;
}
//>>> Clean Arch / Inbound Adaptor
