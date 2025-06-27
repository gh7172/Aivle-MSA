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
// @RequestMapping(value="/집필")
@Transactional
public class 집필Controller {

    @Autowired
    집필Repository 집필Repository;
}
//>>> Clean Arch / Inbound Adaptor
