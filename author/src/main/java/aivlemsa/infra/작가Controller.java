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
// @RequestMapping(value="/작가")
@Transactional
public class 작가Controller {

    @Autowired
    작가Repository 작가Repository;
}
//>>> Clean Arch / Inbound Adaptor
