package aivlemsa.infra;

import aivlemsa.domain.*;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/writes")
@Transactional
public class WriteController {

    @Autowired
    WriteRepository WriteRepository;
}
//>>> Clean Arch / Inbound Adaptor
